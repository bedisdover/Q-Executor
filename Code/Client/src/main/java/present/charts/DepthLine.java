package present.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
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
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static JPanel getChart(List<DeepStockVO> deepStockVOList) {
        TimeSeriesVO timeSeriesVO = getData(deepStockVOList);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRange(false);//设置不采用自动设置数据范围
        yAxis.setUpperMargin(10);//设置向上边框距离
        yAxis.setLabelFont(new Font("微软雅黑", Font.BOLD, 12));
        yAxis.setRange(timeSeriesVO.getLow() * 0.9, timeSeriesVO.getHigh() * 1.1);//设置y轴数据范围

        DateAxis dateAxis = new DateAxis();
        // FIXME 去掉无数据的时间点
//        dateAxis.setAutoRange(false);
//        dateAxis.setRange(timeSeriesVO.getStartTime(), timeSeriesVO.getEndTime());
//        SegmentedTimeline timeline = SegmentedTimeline.newFifteenMinuteTimeline();
//        timeline.addException(timeSeriesVO.getInterruptTime().getTime(), timeSeriesVO.getResumeTime().getTime());
//        dateAxis.setTimeline(timeline);
        dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
        dateAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnit.MINUTE, 30));//设置时间刻度的间隔
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

//        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
//        dateaxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
//        dateaxis.setPositiveArrowVisible(true);

        return new ChartPanel(jfreechart);
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

        double getHigh() {
            return high;
        }

        double getLow() {
            return low;
        }

        Date getStartTime() {
            return getTime("2016-08-31 9:30:00");
        }

        Date getEndTime() {
            return getTime("2016-08-31 15:00:00");
        }

        Date getInterruptTime() {
            return getTime("2016-08-31 11:30:00");
        }

        Date getResumeTime() {
            return getTime("2016-08-31 13:00:00");
        }

        private Date getTime(String time) {
            Date date = null;

            try {
                date = dateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return date;
        }
    }
}
