package present.charts;

import org.jfree.chart.ChartPanel;
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
import present.panel.stock.west.CurrentDataPanel;
import util.TimeUtil;
import vo.StockTimeSeriesVO;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by song on 16-8-29.
 * <p>
 * 分时图
 */
public class TimeSeriesChart {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    private TimeSeriesVO timeSeriesVO;

    public TimeSeriesChart(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        timeSeriesVO = getData(stockTimeSeriesVOList);
    }

    private JPanel getPriceChart() {
        // 实时价格纵轴
        NumberAxis priceAxis = new NumberAxis();
        priceAxis.setAutoRange(false);
        priceAxis.setRange(timeSeriesVO.getPriceRange());
        priceAxis.setVisible(false);

        // 涨跌幅纵轴
        NumberAxis incRateAxis = new NumberAxis();
        incRateAxis.setAutoRange(false);
        incRateAxis.setRange(timeSeriesVO.getIncRateRange());
        NumberFormat numberFormat = new DecimalFormat("0.00%");
        incRateAxis.setNumberFormatOverride(numberFormat);
        incRateAxis.setVisible(false);

        //设置价格绘图器
        XYLineAndShapeRenderer priceRenderer = new XYLineAndShapeRenderer();
        priceRenderer.setBaseItemLabelsVisible(true);
        priceRenderer.setSeriesShapesVisible(0, false);//设置不显示数据点模型
        priceRenderer.setSeriesShapesVisible(1, false);
        priceRenderer.setSeriesShapesVisible(2, false);
        priceRenderer.setSeriesVisibleInLegend(0, false);
        priceRenderer.setSeriesVisibleInLegend(1, false);
        priceRenderer.setSeriesVisibleInLegend(2, false);
        priceRenderer.setSeriesPaint(0, Color.WHITE);//设置颜色
        priceRenderer.setSeriesPaint(1, Color.YELLOW);
        priceRenderer.setSeriesPaint(2, Color.RED);

        // 价格图
        XYPlot pricePlot = new XYPlot(timeSeriesVO.getPriceCollection(), getDateAxis(), null, priceRenderer);
        pricePlot.setRangeAxis(0, priceAxis);
        pricePlot.setRangeAxis(1, incRateAxis);
        pricePlot.setBackgroundPaint(Color.BLACK);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        TickPanel priceTick = new TickPanel(timeSeriesVO.getPriceRange(), timeSeriesVO.getClose());
        priceTick.setPreferredSize(new Dimension(30, 1));
        panel.add(priceTick, BorderLayout.WEST);

        JFreeChart jFreeChart = new JFreeChart(pricePlot);
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        chartPanel.setMouseZoomable(false);
        panel.add(chartPanel, BorderLayout.CENTER);

        TickPanel incRateTick = new TickPanel(timeSeriesVO.getIncRateRange(), 0);
        incRateTick.setPreferredSize(new Dimension(30, 1));
        panel.add(incRateTick, BorderLayout.EAST);

        return panel;
    }

    private JPanel getVolumeChart() {
        // 成交量纵轴
        NumberAxis amountAxis = new NumberAxis();
        amountAxis.setAutoRange(true);

        // 成交量绘图器
        XYBarRenderer amountRender = new XYBarRenderer();
        amountRender.setDrawBarOutline(true);//设置显示边框线
        amountRender.setBarPainter(new StandardXYBarPainter());//取消渐变效果
        amountRender.setMargin(0.3);//设置柱形图之间的间隔
        amountRender.setSeriesPaint(0, Color.YELLOW);//设置柱子内部颜色
//        amountRender.setSeriesVisibleInLegend(0, false);//设置不显示legend（数据颜色提示)
        amountRender.setShadowVisible(false);//设置没有阴影

        // 成交量图
        XYPlot amountPlot = new XYPlot(timeSeriesVO.getAmountCollection(), getDateAxis(), amountAxis, amountRender);
        amountPlot.setBackgroundPaint(Color.BLACK);

        return new ChartPanel(new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, amountPlot, true));
    }

    private DateAxis getDateAxis() {
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

        return dateAxis;
    }

    public JPanel getChart() {
        JPanel panel = new JPanel();

        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);

        JPanel priceChart = getPriceChart();
        panel.add(priceChart);
        JPanel volumeChart = getVolumeChart();
        panel.add(volumeChart);

        GridBagConstraints gridBagConstraints= new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagLayout.setConstraints(priceChart, gridBagConstraints);

        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagLayout.setConstraints(volumeChart, gridBagConstraints);

        return panel;
    }

    private TimeSeriesVO getData(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        return new TimeSeriesVO(stockTimeSeriesVOList);
    }

    /**
     * 获取时间(精确到分钟)
     *
     * @param time 格式: "HH:mm"
     */
    private Minute getMinute(String time) {
        Minute minute;

        try {
            minute = new Minute(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Minute(new Date());
        }

        return minute;
    }

    private class TimeSeriesVO {
        private double high = Double.MIN_VALUE, low = Double.MAX_VALUE;

        private TimeSeriesCollection priceCollection;

        private TimeSeriesCollection amountCollection;

        TimeSeriesVO(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
            initData(stockTimeSeriesVOList);
        }

        private double getClose() {
            return CurrentDataPanel.getClose();
        }

        private void initData(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
            TimeSeries priceSeries = new TimeSeries("实时价格");
            TimeSeries avgSeries = new TimeSeries("平均价格");
            TimeSeries closeSeries = new TimeSeries("收盘价");
            TimeSeries amountSeries = new TimeSeries("成交量");

            StockTimeSeriesVO stockVO;
            Minute minute;
            for (int i = stockTimeSeriesVOList.size() - 1; i >= 0; i--) {
                stockVO = stockTimeSeriesVOList.get(i);

                minute = getMinute(stockVO.getTimeLine());
                priceSeries.add(minute, stockVO.getPrice());
                avgSeries.add(minute, stockVO.getAvePrice());
                closeSeries.add(minute, getClose());

                amountSeries.add(getMinute(stockVO.getTimeLine()), stockVO.getVolume());
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

            return new Range(low - margin, high + margin);
        }

        /**
         * 获取涨跌幅范围
         */
        Range getIncRateRange() {
            double close = getClose();

            return new Range((getPriceRange().getLowerBound() - close) / close,
                    (getPriceRange().getUpperBound() - close) / close);
        }
    }
}

