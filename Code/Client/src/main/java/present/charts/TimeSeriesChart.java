package present.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import vo.StockTimeSeriesVO;

import javax.swing.*;
import java.awt.*;
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
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static JPanel getChart(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
                "", "", "", getData(stockTimeSeriesVOList), true, true, true);

        jfreechart.setBackgroundPaint(Color.BLACK);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);

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

    private static XYDataset getData(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        TimeSeries timeSeries1 = new TimeSeries("实时价格");
        TimeSeries timeSeries2 = new TimeSeries("平均价格");

        StockTimeSeriesVO stockVO;
        for (int i = stockTimeSeriesVOList.size() - 1; i >= 0; i--) {
            stockVO = stockTimeSeriesVOList.get(i);

            timeSeries1.add(getTime(stockVO.getTimeLine()), stockVO.getPrice());
            timeSeries2.add(getTime(stockVO.getTimeLine()), stockVO.getAvePrice());
        }

        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(timeSeries1);
        timeseriescollection.addSeries(timeSeries2);

        System.out.println(timeSeries1.getItems());
        System.out.println(timeSeries2.getItems());

        return timeseriescollection;
    }

    /**
     * 获取时间(精确到秒)
     *
     * @param time 格式: "HH:mm:ss"
     */
    private static Second getTime(String time) {
        Second second;

        try {
            second = new Second(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Second(new Date());
        }

        return second;
    }
}

