package present.charts;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import present.component.chart.MyChartPanel;
import present.component.chart.MyNumberAxis;
import present.component.chart.MyPanel;
import present.panel.stock.west.CurrentDataPanel;
import present.utils.ColorUtil;
import util.NumberUtil;
import util.TimeUtil;
import vo.StockTimeSeriesVO;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by song on 16-8-29.
 * <p>
 * 分时图
 */
public class TimeSeriesChart {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public static JPanel getChart(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        TimeSeriesVO timeSeriesVO = getData(stockTimeSeriesVOList);

        // 横轴
        DateAxis dateAxis = new DateAxis();
        dateAxis.setAutoRange(false);
        dateAxis.setRange(TimeUtil.getStartTime(), TimeUtil.getEndTime());
        SegmentedTimeline timeline = SegmentedTimeline.newFifteenMinuteTimeline();
        timeline.addException(TimeUtil.getInterruptTime().getTime(), TimeUtil.getResumeTime().getTime());
        dateAxis.setTimeline(timeline);
        dateAxis.setTickMarkPosition(DateTickMarkPosition.START);//设置标记的位置
        dateAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MINUTE, 30));//设置时间刻度的间隔
        dateAxis.setDateFormatOverride(dateFormat);//设置显示时间的格式

        // 实时价格纵轴
        NumberAxis yAxis = new MyNumberAxis(timeSeriesVO.getClose());
        yAxis.setAutoRange(false);//设置不采用自动设置数据范围
        yAxis.setUpperMargin(10);//设置向上边框距离
        yAxis.setRange(timeSeriesVO.getPriceRange());//设置y轴数据范围
        // 涨跌幅纵轴
        NumberAxis avgAxis = new MyNumberAxis(0);
        avgAxis.setAutoRange(false);
        avgAxis.setRange(timeSeriesVO.getIncRateRange());
        NumberFormat numberFormat = new DecimalFormat("0.00%");
        avgAxis.setNumberFormatOverride(numberFormat);

        //设置价格绘图器
        XYLineAndShapeRenderer priceRenderer = new XYLineAndShapeRenderer();
        priceRenderer.setBaseItemLabelsVisible(true);
        priceRenderer.setSeriesShapesVisible(0, false);//设置不显示数据点模型
        priceRenderer.setSeriesShapesVisible(1, false);
        priceRenderer.setSeriesShapesVisible(2, false);
        priceRenderer.setSeriesPaint(0, Color.WHITE);//设置颜色
        priceRenderer.setSeriesPaint(1, Color.YELLOW);
        priceRenderer.setSeriesPaint(2, Color.RED);

        // 价格图
        XYPlot pricePlot = new XYPlot(timeSeriesVO.getPriceCollection(), dateAxis, yAxis, priceRenderer);
        pricePlot.setRangeAxis(0, yAxis);
        pricePlot.setRangeAxis(1, avgAxis);
        pricePlot.setBackgroundPaint(Color.BLACK);

        // 成交量纵轴
        NumberAxis y1Axis = new NumberAxis();
        y1Axis.setAutoRange(true);

        // 成交量绘图器
        XYBarRenderer amountRender = new XYBarRenderer();
        amountRender.setMargin(0.1);//设置柱形图之间的间隔
        amountRender.setDrawBarOutline(true);//设置显示边框线
        amountRender.setBarPainter(new StandardXYBarPainter());//取消渐变效果
        amountRender.setMargin(0.3);//设置柱形图之间的间隔
        amountRender.setSeriesPaint(0, Color.YELLOW);//设置柱子内部颜色
        amountRender.setSeriesVisibleInLegend(0, false);//设置不显示legend（数据颜色提示)
        amountRender.setShadowVisible(false);//设置没有阴影

        // 成交量图
        XYPlot amountPlot = new XYPlot(timeSeriesVO.getAmountCollection(), null, y1Axis, amountRender);
        amountPlot.setBackgroundPaint(Color.BLACK);

        CombinedDomainXYPlot combinedDomainXYPlot = new CombinedDomainXYPlot(dateAxis);
        combinedDomainXYPlot.add(pricePlot, 2);
        combinedDomainXYPlot.add(amountPlot, 1);
        combinedDomainXYPlot.setGap(10);

        return new MyPanel(new MyChartPanel(new JFreeChart(combinedDomainXYPlot)), timeSeriesVO);
    }

    private static TimeSeriesVO getData(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        return new TimeSeriesVO(stockTimeSeriesVOList);
    }

    /**
     * 获取时间(精确到分钟)
     *
     * @param time 格式: "HH:mm"
     */
    private static Minute getMinute(String time) {
        Minute minute;

        try {
            minute = new Minute(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Minute(new Date());
        }

        return minute;
    }

    private static class TimeSeriesVO extends ChartVO {
        private double high = Double.MIN_VALUE, low = Double.MAX_VALUE;

        private TimeSeries priceSeries, avgSeries, amountSeries;

        private TimeSeriesCollection priceCollection;

        private TimeSeriesCollection amountCollection;

        private double close;

        private List<StockTimeSeriesVO> stockTimeSeriesVOList;

        TimeSeriesVO(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
            this.stockTimeSeriesVOList = stockTimeSeriesVOList;

            close = CurrentDataPanel.getClose();
            initData();
        }

        private void initData() {
            priceSeries = new TimeSeries("实时价格");
            avgSeries = new TimeSeries("平均价格");
            TimeSeries closeSeries = new TimeSeries("收盘价");

            amountSeries = new TimeSeries("成交量");

            StockTimeSeriesVO stockVO;
            Minute minute;
            for (int i = stockTimeSeriesVOList.size() - 1; i >= 0; i--) {
                stockVO = stockTimeSeriesVOList.get(i);

                minute = getMinute(stockVO.getTimeLine());
                priceSeries.add(minute, stockVO.getPrice());
                avgSeries.add(minute, stockVO.getAvePrice());
                closeSeries.add(minute, close);

                amountSeries.add(minute, stockVO.getVolume());
            }

            priceCollection = new TimeSeriesCollection();
            priceCollection.addSeries(priceSeries);
            priceCollection.addSeries(avgSeries);
            priceCollection.addSeries(closeSeries);

            amountCollection = new TimeSeriesCollection();
            amountCollection.addSeries(amountSeries);

            calculate(stockTimeSeriesVOList);
        }

        /**
         * 获取K线数据的最高值和最低值
         */
        private void calculate(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
            double temp;
            for (StockTimeSeriesVO stockVO : stockTimeSeriesVOList) {
                temp = stockVO.getPrice();
                if (temp > high) {
                    high = temp;
                }

                if (temp < low) {
                    low = temp;
                }
            }
        }

        TimeSeriesCollection getPriceCollection() {
            return priceCollection;
        }

        TimeSeriesCollection getAmountCollection() {
            return amountCollection;
        }

        /**
         * 获取价格范围
         */
        Range getPriceRange() {
            double margin = (high - low) * 0.1;

            if (margin >= 0) {
                return new Range(low - margin, high + margin);
            } else {
                return new Range(0, 0);
            }
        }

        /**
         * 获取涨跌幅范围
         */
        Range getIncRateRange() {
            return new Range((getPriceRange().getLowerBound() - close) / close,
                    (getPriceRange().getUpperBound() - close) / close);
        }

        /**
         * 获取某一时刻的数据
         */
        public String getText(int itemIndex) {
            double price = (double) priceSeries.getDataItem(itemIndex).getValue();
            double incNum = price - close;
            double amount = (double) amountSeries.getDataItem(itemIndex).getValue();

            return getTime(itemIndex) + "  价格:" + price +
                    "  涨跌:" + NumberUtil.round(incNum) + "(" + NumberUtil.getPercent(incNum / close) + ")" +
                    "  成交量:" + NumberUtil.transferUnit(amount / 100) + "手";
        }

        double getClose() {
            return close;
        }

        /**
         * 获取精确时间
         *
         * @return 格式: yyyy-MM-dd HH:mm:ss
         */
        String getTime(int itemIndex) {
            return TimeUtil.getDateHHmmss(priceSeries.getDataItem(itemIndex).getPeriod().getFirstMillisecond());
        }
    }
}
