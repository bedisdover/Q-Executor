package present.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.*;
import org.jfree.ui.RectangleInsets;
import vo.StockTimeSeriesVO;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
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

    public static JPanel getChart(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        TimeSeriesVO timeSeriesVO = getData(stockTimeSeriesVOList);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRange(false);//设置不采用自动设置数据范围
        yAxis.setUpperMargin(10);//设置向上边框距离
        yAxis.setLabelFont(new Font("微软雅黑", Font.BOLD, 12));
        yAxis.setRange(timeSeriesVO.getLow(), timeSeriesVO.getHigh());//设置y轴数据范围

        DateAxis dateAxis = new DateAxis();
        dateAxis.setAutoRange(false);
        dateAxis.setRange(timeSeriesVO.getStartTime(), timeSeriesVO.getEndTime());
        SegmentedTimeline timeline = SegmentedTimeline.newFifteenMinuteTimeline();
        timeline.addException(timeSeriesVO.getInterruptTime().getTime(), timeSeriesVO.getResumeTime().getTime());
        dateAxis.setTimeline(timeline);
        dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
        dateAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnit.MINUTE, 30));//设置时间刻度的间隔
        dateAxis.setDateFormatOverride(dateFormat);//设置显示时间的格式

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
                "", "", "", timeSeriesVO.getTimeSeriesCollection(), true, true, true);

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

    private static TimeSeriesVO getData(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        return new TimeSeriesVO(stockTimeSeriesVOList);
    }

    /**
     * 获取时间(精确到秒)
     *
     * @param time 格式: "HH:mm:ss"
     */
    private static Minute getMinute(String time) {
        Minute minute;

        try {
            minute= new Minute(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Minute(new Date());
        }

        return minute;
    }

    private static class TimeSeriesVO {
        private double high = Double.MIN_VALUE, low = Double.MAX_VALUE;

        private TimeSeriesCollection timeSeriesCollection;

        TimeSeriesVO(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
            TimeSeries timeSeries1 = null;
            TimeSeries1 = new TimeSeries("price");

            TimeSeries timeSeries2 = new TimeSeries("average price");

            StockTimeSeriesVO stockVO;
            for (int i = stockTimeSeriesVOList.size() - 1; i >= 0; i--) {
                stockVO = stockTimeSeriesVOList.get(i);

                timeSeries1.add(getMinute(stockVO.getTimeLine()), stockVO.getPrice());
                timeSeries2.add(getMinute(stockVO.getTimeLine()), stockVO.getAvePrice());
            }

            timeSeriesCollection = new TimeSeriesCollection();
            timeSeriesCollection.addSeries(timeSeries1);
            timeSeriesCollection.addSeries(timeSeries2);

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
            return getTime("9:30");
        }

        Date getEndTime() {
            return getTime("15:00");
        }

        Date getInterruptTime() {
            return getTime("11:30");
        }

        Date getResumeTime() {
            return getTime("13:00");
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

