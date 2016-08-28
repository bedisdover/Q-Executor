package present.charts;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * Created by song on 16-8-25.
 * <p>
 * K线图(panel作为容器)
 * 包括日K、周K、月K
 */
public class KLine {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public JTabbedPane getKLine(String stockCode) {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("日K", getKLineDay(stockCode));
        tabbedPane.addTab("周K", getKLineWeek(stockCode));
        tabbedPane.addTab("月K", getKLineMonth(stockCode));

        return tabbedPane;
    }

    /**
     * 获取日K线
     *
     * @param stockCode 股票代码
     * @return 包含日K线的panel
     */
    private JPanel getKLineDay(String stockCode) {
        JPanel panel = new ChartPanel(createChart(stockCode));

        panel.setPreferredSize(new Dimension(300, 100));

        return panel;
    }

    /**
     * 获取周K线
     *
     * @param stockCode 股票代码
     * @return 包含周K线的panel
     */
    private JPanel getKLineWeek(String stockCode) {
        return new JPanel();
    }

    /**
     * 获取月K线
     *
     * @param stockCode 股票代码
     * @return 包含月K线的panel
     */
    private JPanel getKLineMonth(String stockCode) {
        JPanel panel = new ChartPanel(createChart(stockCode));

        panel.setPreferredSize(new Dimension(300, 100));

        return panel;
    }

    /**
     * 获取成交量数据序列
     *
     * @param stockCode 股票代码
     * @return 数据序列
     */

    /**
     * 绘制图形
     *
     * @return jfreeChart
     */
    private JFreeChart createChart(String stockCode) {
        KLineVO kLineVO = new KLineVO(stockCode);
        //保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
        final OHLCSeriesCollection seriesCollection = kLineVO.getOhlcSeriesCollection();
        //保留成交量数据的集合
        TimeSeriesCollection timeSeriesCollection = kLineVO.getTimeSeriesCollection();

        final CandlestickRenderer candlestickRender = new CandlestickRenderer();//设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
        candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
        candlestickRender.setAutoWidthGap(0.001);//设置各个K线图之间的间隔
        candlestickRender.setUpPaint(Color.BLACK);//设置股票上涨的K线图颜色
        candlestickRender.setDownPaint(Color.GREEN);//设置股票下跌的K线图颜色
        candlestickRender.setUseOutlinePaint(true);
        candlestickRender.setSeriesOutlinePaint(0, Color.RED);
        candlestickRender.setSeriesOutlinePaint(1, Color.GREEN);

        DateAxis x1Axis = new DateAxis();//设置x轴，也就是时间轴
        x1Axis.setAutoRange(false);//设置不采用自动设置时间范围
        try {
            x1Axis.setRange(dateFormat.parse("2007-08-20"), dateFormat.parse("2007-09-29"));//设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
        } catch (Exception e) {
            e.printStackTrace();
        }
        x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
        x1Axis.setAutoTickUnitSelection(false);//设置不采用自动选择刻度值
        x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
        x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
        x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 7));//设置时间刻度的间隔，一般以周为单位
        x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//设置显示时间的格式

        //设置k线图y轴参数
        NumberAxis y1Axis = new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的y轴设置
        y1Axis.setAutoRange(false);//不使用自动设定范围
        y1Axis.setRange(kLineVO.getLow() * 0.9, kLineVO.getHigh() * 1.1);//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
        y1Axis.setTickUnit(new NumberTickUnit((kLineVO.getHigh() * 1.1 - kLineVO.getLow() * 0.9) / 10));//设置刻度显示的密度

        // TODO 设置均线图画图器
        XYLineAndShapeRenderer lineAndShapeRenderer = new XYLineAndShapeRenderer();
        lineAndShapeRenderer.setBaseItemLabelsVisible(true);
        lineAndShapeRenderer.setSeriesShapesVisible(0, false);//设置不显示数据点模型
        lineAndShapeRenderer.setSeriesShapesVisible(1, false);
        lineAndShapeRenderer.setSeriesShapesVisible(2, false);
        lineAndShapeRenderer.setSeriesPaint(0, Color.WHITE);//设置均线颜色
        lineAndShapeRenderer.setSeriesPaint(1, Color.YELLOW);
        lineAndShapeRenderer.setSeriesPaint(2, Color.MAGENTA);

        XYPlot plot1 = new XYPlot(seriesCollection, x1Axis, y1Axis, candlestickRender);//设置画图区域对象
        plot1.setBackgroundPaint(Color.BLACK);
        plot1.setDomainGridlinesVisible(false);//不显示网格

        XYBarRenderer xyBarRender = new XYBarRenderer() {
            public Paint getItemPaint(int i, int j) {//匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
                if (seriesCollection.getCloseValue(i, j) > seriesCollection.getOpenValue(i, j)) {//收盘价高于开盘价，股票上涨，选用股票上涨的颜色
                    return candlestickRender.getUpPaint();
                } else {
                    return candlestickRender.getDownPaint();
                }
            }
        };
        xyBarRender.setMargin(0.1);//设置柱形图之间的间隔
        xyBarRender.setDrawBarOutline(true);//设置显示边框线
        xyBarRender.setBarPainter(new StandardXYBarPainter());//取消渐变效果
        xyBarRender.setMargin(0.3);//设置柱形图之间的间隔
        xyBarRender.setSeriesPaint(0, Color.BLACK);//设置柱子内部颜色
        xyBarRender.setSeriesPaint(1, Color.CYAN);//设置柱子内部颜色
        xyBarRender.setSeriesOutlinePaint(0, Color.RED);//设置柱子边框颜色
        xyBarRender.setSeriesOutlinePaint(1, Color.CYAN);//设置柱子边框颜色
        xyBarRender.setShadowVisible(false);//设置没有阴影

        NumberAxis y2Axis = new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的y轴设置
        y2Axis.setAutoRange(false);
        y2Axis.setRange(kLineVO.getLow_amount() * 0.9, kLineVO.getHigh_amount() * 1.1);
        y2Axis.setTickUnit(new NumberTickUnit((kLineVO.getLow_amount() * 1.1 - kLineVO.getHigh_amount() * 0.9) / 4));
        XYPlot plot2 = new XYPlot(timeSeriesCollection, null, y2Axis, xyBarRender);//建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
        plot2.setBackgroundPaint(Color.BLACK);
        plot2.setRangeGridlinePaint(Color.BLACK);// 设置横轴参考线颜色
        plot2.setDomainGridlinePaint(Color.WHITE);// 设置纵轴参考线颜色

        CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//建立一个恰当的联合图形区域对象，以x轴为共享轴
        combineddomainxyplot.add(plot1, 2);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
        combineddomainxyplot.add(plot2, 1);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
        combineddomainxyplot.setGap(10);//设置两个图形区域对象之间的间隔空间

        JFreeChart chart = new JFreeChart("中国联通", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);

        return chart;
    }
}

class KLineVO {
    private OHLCSeries ohlcSeries;

    private OHLCSeriesCollection ohlcSeriesCollection;

    private TimeSeries timeSeries;
    private TimeSeriesCollection timeSeriesCollection;

    private double high, low, high_amount, low_amount;
    double highValue = Double.MIN_VALUE;//设置K线数据当中的最大值
    double minValue = Double.MAX_VALUE;//设置K线数据当中的最小值
    double high2Value = Double.MIN_VALUE;//设置成交量的最大值
    double min2Value = Double.MAX_VALUE;//设置成交量的最低值

    KLineVO(String stockCode) {
        ohlcSeries = new OHLCSeries("");
        ohlcSeries.add(new Day(28, 9, 2007), 9.2, 9.58, 9.16, 9.34);
        ohlcSeries.add(new Day(27, 9, 2007), 8.9, 9.06, 8.83, 8.96);
        ohlcSeries.add(new Day(26, 9, 2007), 9.0, 9.1, 8.82, 9.04);
        ohlcSeries.add(new Day(25, 9, 2007), 9.25, 9.33, 8.88, 9.00);
        ohlcSeries.add(new Day(24, 9, 2007), 9.05, 9.50, 8.91, 9.25);
        ohlcSeries.add(new Day(21, 9, 2007), 8.68, 9.05, 8.40, 9.00);
        ohlcSeries.add(new Day(20, 9, 2007), 8.68, 8.95, 8.50, 8.69);
        ohlcSeries.add(new Day(19, 9, 2007), 8.80, 8.94, 8.50, 8.66);
        ohlcSeries.add(new Day(18, 9, 2007), 8.88, 9.17, 8.69, 8.80);
        ohlcSeries.add(new Day(17, 9, 2007), 8.26, 8.98, 8.15, 8.89);
        ohlcSeries.add(new Day(14, 9, 2007), 8.44, 8.45, 8.13, 8.33);
        ohlcSeries.add(new Day(13, 9, 2007), 8.13, 8.46, 7.97, 8.42);
        ohlcSeries.add(new Day(12, 9, 2007), 8.2, 8.4, 7.81, 8.13);
        ohlcSeries.add(new Day(11, 9, 2007), 9.0, 9.0, 8.1, 8.24);
        ohlcSeries.add(new Day(10, 9, 2007), 8.6, 9.03, 8.40, 8.95);
        ohlcSeries.add(new Day(7, 9, 2007), 8.89, 9.04, 8.70, 8.73);
        ohlcSeries.add(new Day(6, 9, 2007), 8.4, 9.08, 8.33, 8.88);
        ohlcSeries.add(new Day(5, 9, 2007), 8.2, 8.74, 8.17, 8.36);
        ohlcSeries.add(new Day(4, 9, 2007), 7.7, 8.46, 7.67, 8.27);
        ohlcSeries.add(new Day(3, 9, 2007), 7.5, 7.8, 7.48, 7.69);
        ohlcSeries.add(new Day(31, 8, 2007), 7.4, 7.6, 7.28, 7.43);
        ohlcSeries.add(new Day(30, 8, 2007), 7.42, 7.56, 7.31, 7.40);
        ohlcSeries.add(new Day(29, 8, 2007), 7.42, 7.66, 7.22, 7.33);
        ohlcSeries.add(new Day(28, 8, 2007), 7.31, 7.70, 7.15, 7.56);
        ohlcSeries.add(new Day(27, 8, 2007), 7.05, 7.46, 7.02, 7.41);
        ohlcSeries.add(new Day(24, 8, 2007), 7.05, 7.09, 6.90, 6.99);
        ohlcSeries.add(new Day(23, 8, 2007), 7.12, 7.16, 7.00, 7.03);
        ohlcSeries.add(new Day(22, 8, 2007), 6.96, 7.15, 6.93, 7.11);
        ohlcSeries.add(new Day(21, 8, 2007), 7.10, 7.15, 7.02, 7.07);
        ohlcSeries.add(new Day(20, 8, 2007), 7.02, 7.19, 6.94, 7.14);

        ohlcSeriesCollection = new OHLCSeriesCollection();
        ohlcSeriesCollection.addSeries(ohlcSeries);

        timeSeries = new TimeSeries("");
        timeSeries.add(new Day(28, 9, 2007), 260659400 / 100);
        timeSeries.add(new Day(27, 9, 2007), 119701900 / 100);
        timeSeries.add(new Day(26, 9, 2007), 109719000 / 100);
        timeSeries.add(new Day(25, 9, 2007), 178492400 / 100);
        timeSeries.add(new Day(24, 9, 2007), 269978500 / 100);
        timeSeries.add(new Day(21, 9, 2007), 361042300 / 100);
        timeSeries.add(new Day(20, 9, 2007), 173912600 / 100);
        timeSeries.add(new Day(19, 9, 2007), 154622600 / 100);
        timeSeries.add(new Day(18, 9, 2007), 200661600 / 100);
        timeSeries.add(new Day(17, 9, 2007), 312799600 / 100);
        timeSeries.add(new Day(14, 9, 2007), 141652900 / 100);
        timeSeries.add(new Day(13, 9, 2007), 221260400 / 100);
        timeSeries.add(new Day(12, 9, 2007), 274795400 / 100);
        timeSeries.add(new Day(11, 9, 2007), 289287300 / 100);
        timeSeries.add(new Day(10, 9, 2007), 289063600 / 100);
        timeSeries.add(new Day(7, 9, 2007), 351575300 / 100);
        timeSeries.add(new Day(6, 9, 2007), 451357300 / 100);
        timeSeries.add(new Day(5, 9, 2007), 442421200 / 100);
        timeSeries.add(new Day(4, 9, 2007), 671942600 / 100);
        timeSeries.add(new Day(3, 9, 2007), 349647800 / 100);
        timeSeries.add(new Day(31, 8, 2007), 225339300 / 100);
        timeSeries.add(new Day(30, 8, 2007), 160048200 / 100);
        timeSeries.add(new Day(29, 8, 2007), 247341700 / 100);
        timeSeries.add(new Day(28, 8, 2007), 394975400 / 100);
        timeSeries.add(new Day(27, 8, 2007), 475797500 / 100);
        timeSeries.add(new Day(24, 8, 2007), 297679500 / 100);
        timeSeries.add(new Day(23, 8, 2007), 191760600 / 100);
        timeSeries.add(new Day(22, 8, 2007), 232570200 / 100);
        timeSeries.add(new Day(21, 8, 2007), 215693200 / 100);
        timeSeries.add(new Day(20, 8, 2007), 200287500 / 100);

        timeSeriesCollection = new TimeSeriesCollection();
        timeSeriesCollection.addSeries(timeSeries);

        calculate();
    }

    private void calculate() {
        //获取K线数据的最高值和最低值
        int count = ohlcSeriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
        for (int i = 0; i < count; i++) {
            int itemCount = ohlcSeriesCollection.getItemCount(i);//每一个序列有多少个数据项
            for (int j = 0; j < itemCount; j++) {
                if (high < ohlcSeriesCollection.getHighValue(i, j)) {//取第i个序列中的第j个数据项的最大值
                    high = ohlcSeriesCollection.getHighValue(i, j);
                }
                if (low > ohlcSeriesCollection.getLowValue(i, j)) {//取第i个序列中的第j个数据项的最小值
                    low = ohlcSeriesCollection.getLowValue(i, j);
                }
            }

        }
        //获取最高值和最低值
        int seriesCount2 = timeSeriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
        for (int i = 0; i < seriesCount2; i++) {
            int itemCount = timeSeriesCollection.getItemCount(i);//每一个序列有多少个数据项
            for (int j = 0; j < itemCount; j++) {
                if (high_amount < timeSeriesCollection.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
                    high_amount = timeSeriesCollection.getYValue(i, j);
                }
                if (low_amount > timeSeriesCollection.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
                    low_amount = timeSeriesCollection.getYValue(i, j);
                }
            }
        }
    }

    public OHLCSeriesCollection getOhlcSeriesCollection() {
        return ohlcSeriesCollection;
    }

    public TimeSeriesCollection getTimeSeriesCollection() {
        return timeSeriesCollection;
    }

    public OHLCSeries getOhlcSeries() {
        return ohlcSeries;
    }

    public void setOhlcSeries(OHLCSeries ohlcSeries) {
        this.ohlcSeries = ohlcSeries;
    }

    public TimeSeries getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh_amount() {
        return high_amount;
    }

    public void setHigh_amount(double high_amount) {
        this.high_amount = high_amount;
    }

    public double getLow_amount() {
        return low_amount;
    }

    public void setLow_amount(double low_amount) {
        this.low_amount = low_amount;
    }
}
