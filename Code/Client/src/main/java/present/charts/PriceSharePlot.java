package present.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-8-27.
 * <p>
 * 分价图
 */
public class PriceSharePlot {

    public JPanel getPlotChart(String codeNum) {
        JPanel panel = new ChartPanel(createChart(codeNum));

        panel.setPreferredSize(new Dimension(300, 1));

        return panel;
    }

    private JFreeChart createChart(String codeNum) {
        JFreeChart chart = ChartFactory.createBarChart("苹果年产量统计图", "省份", "产量(万吨)",
                getDataSet(codeNum), PlotOrientation.HORIZONTAL, false, false, false);

        CategoryPlot plot = chart.getCategoryPlot();
        //设置网格背景颜色
        plot.setBackgroundPaint(Color.white);
        //设置网格竖线颜色
        plot.setDomainGridlinePaint(Color.pink);
        //设置网格横线颜色
        plot.setRangeGridlinePaint(Color.pink);

        //显示每个柱的数值，并修改该数值的字体属性
        BarRenderer renderer = new BarRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setShadowVisible(false);//设置没有阴影

        //默认的数字显示在柱子中，通过如下两句可调整数字的显示
        //注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.BASELINE_RIGHT));
        renderer.setItemLabelAnchorOffset(20D);

        plot.setRenderer(renderer);

        return chart;
    }

    private DefaultCategoryDataset getDataSet(String codeNum) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        dataSet.addValue(390, "中国", "河南");
        dataSet.addValue(220, "中国", "河北");
        dataSet.addValue(510, "中国", "辽宁");
        dataSet.addValue(580, "中国", "山东");
        dataSet.addValue(320, "中国", "山西");
        dataSet.addValue(410, "中国", "陕西");
        dataSet.addValue(410, "中国", "陕西1");
        dataSet.addValue(410, "中国", "陕西2");
        dataSet.addValue(410, "中国", "陕西3");
        dataSet.addValue(410, "中国", "陕西4");

        return dataSet;
    }
}
