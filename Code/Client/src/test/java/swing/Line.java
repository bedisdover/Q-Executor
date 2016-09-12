package swing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class Line {
    public static void main(String[] args) {
        CategoryDataset dataSet = GetDataSet();
        JFreeChart mChart = ChartFactory.createLineChart("折线图", "年份", "数量", dataSet,
                PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot mPlot = (CategoryPlot) mChart.getPlot();
        mPlot.setBackgroundPaint(Color.LIGHT_GRAY);
        mPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
        mPlot.setOutlinePaint(Color.RED);//边界线

        ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);

    }

    private static CategoryDataset GetDataSet() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        dataSet.addValue(1, "First", "2013");
        dataSet.addValue(3, "First", "2014");
        dataSet.addValue(2, "First", "2015");
        dataSet.addValue(6, "First", "2016");
        dataSet.addValue(5, "First", "2017");
        dataSet.addValue(12, "First", "2018");
        dataSet.addValue(14, "Second", "2013");
        dataSet.addValue(13, "Second", "2014");
        dataSet.addValue(12, "Second", "2015");
        dataSet.addValue(9, "Second", "2016");
        dataSet.addValue(5, "Second", "2017");
        dataSet.addValue(7, "Second", "2018");

        return dataSet;
    }
}
