package present.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import vo.StockTimeSeriesVO;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by song on 16-8-29.
 * <p>
 * 分时图
 */
public class TimeSeriesChart {
    public static JPanel getChart(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        StandardChartTheme standardChartTheme = new StandardChartTheme("name");
        //可以改变轴向的字体
        standardChartTheme.setLargeFont(new Font("微软雅黑", Font.BOLD, 14));
        //可以改变图例的字体
        standardChartTheme.setRegularFont(new Font("微软雅黑", Font.BOLD, 10));
        //可以改变图标的标题字体
        standardChartTheme.setExtraLargeFont(new Font("微软雅黑", Font.BOLD, 20));
        ChartFactory.setChartTheme(standardChartTheme);// 设置主题

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
                "", "", "Price Per Unit", getData(stockTimeSeriesVOList), true, true, true);

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
        }

        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
        dateaxis.setPositiveArrowVisible(true);

        return new ChartPanel(jfreechart);
    }

    private static XYDataset getData(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        TimeSeries timeSeries1 = new TimeSeries("实时价格");

        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(timeSeries1);
        timeseriescollection.addSeries(timeSeries2);
        return timeseriescollection;
        return null;
    }
}

