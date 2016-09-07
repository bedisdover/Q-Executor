package present.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import util.TimeUtil;
import vo.DeepStockVO;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by song on 16-8-31.
 * <p>
 * 深度曲线
 */
public class DepthLine {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static JPanel getChart(List<DeepStockVO> deepStockVOList) {
        TimeSeriesVO timeSeriesVO = getData(deepStockVOList);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRange(false);//设置不采用自动设置数据范围
        yAxis.setUpperMargin(10);//设置向上边框距离
        yAxis.setRange(timeSeriesVO.getRange());//设置y轴数据范围

        DateAxis dateAxis = new DateAxis();
        dateAxis.setAutoRange(false);
        dateAxis.setRange(TimeUtil.getStartTime(), TimeUtil.getEndTime());
        SegmentedTimeline timeline = SegmentedTimeline.newFifteenMinuteTimeline();
        timeline.addException(TimeUtil.getInterruptTime().getTime(), TimeUtil.getResumeTime().getTime());
        dateAxis.setTimeline(timeline);
        dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
        dateAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MINUTE, 30));//设置时间刻度的间隔
        dateAxis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));//设置显示时间的格式

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
                "", "", "", timeSeriesVO.getTimeSeriesCollection(), false, true, true);

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        xyplot.setBackgroundPaint(Color.BLACK);
        xyplot.setDomainGridlinesVisible(false);
        xyplot.setRangeGridlinesVisible(false);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        xyplot.setRangeAxis(yAxis);
        xyplot.setDomainAxis(dateAxis);

        XYItemRenderer xyitemrenderer = xyplot.getRenderer();
        if (xyitemrenderer instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyitemrenderer;
            xylineandshaperenderer.setBaseShapesVisible(false);
            xylineandshaperenderer.setBaseShapesFilled(false);
            xylineandshaperenderer.setBaseStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            xylineandshaperenderer.setSeriesShapesVisible(0, false);
            xylineandshaperenderer.setSeriesShapesVisible(1, false);
        }

        ChartPanel chartPanel = new ChartPanel(jfreechart);
        chartPanel.setMouseZoomable(false);

        return chartPanel;
    }

    private static TimeSeriesVO getData(List<DeepStockVO> deepStockVOList) {
        return new TimeSeriesVO(deepStockVOList);
    }

    /**
     * 获取时间(精确到秒)
     *
     * @param time 格式: "HH:mm:ss"
     */
    private static Second getSecond(String time) {
        Second second;

        try {
            second = new Second(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Second(new Date());
        }

        return second;
    }

    private static class TimeSeriesVO {
        private double high = Double.MIN_VALUE, low = Double.MAX_VALUE;

        private TimeSeriesCollection timeSeriesCollection;

        TimeSeriesVO(List<DeepStockVO> deepStockVOList) {
            TimeSeries timeSeries = new TimeSeries("");

            DeepStockVO deepStockVO;
            for (int i = deepStockVOList.size() - 1; i >= 0; i--) {
                deepStockVO = deepStockVOList.get(i);

                timeSeries.add(getSecond(deepStockVO.getTimeline()), deepStockVO.getDeepPrice());
            }

            timeSeriesCollection = new TimeSeriesCollection();
            timeSeriesCollection.addSeries(timeSeries);

            calculate(deepStockVOList);
        }

        /**
         * 获取K线数据的最高值和最低值
         */
        private void calculate(List<DeepStockVO> deepStockVOList) {
            double temp;
            for (DeepStockVO stockVO : deepStockVOList) {
                temp = stockVO.getDeepPrice();
                if (temp > high) {
                    high = temp;
                }

                if (temp < low) {
                    low = temp;
                }
            }
        }

        TimeSeriesCollection getTimeSeriesCollection() {
            return timeSeriesCollection;
        }

        Range getRange() {
            double temp = (high - low) * 0.1;

            if (temp >= 0) {
                return new Range(low - temp, high + temp);
            } else {
                return new Range(0, 0);
            }
        }
    }
}
