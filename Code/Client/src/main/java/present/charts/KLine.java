package present.charts;

import bl.GetKLineDataServiceImpl;
import blservice.GetKLineDataService;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import util.TimeUtil;
import vo.StockKLineVO;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by song on 16-8-25.
 * <p>
 * K线图(panel作为容器)
 * 包括日K、周K、月K
 */
public class KLine {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private GetKLineDataService kLineDataService = new GetKLineDataServiceImpl();

    private JTabbedPane tabbedPane;

    private JPanel kLineDay, kLineWeek, kLineMonth;

    private String stockCode;

    public JTabbedPane getKLine(String stockCode) throws Exception {
        this.stockCode = stockCode;
        tabbedPane = new JTabbedPane();

        getKLineDay();
        getKLineWeek();
        getKLineMonth();

        return tabbedPane;
    }

    /**
     * 获取日K线
     */
    private void getKLineDay() throws Exception {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                return kLineDataService.getKLineDay(stockCode);
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void done() {
                try {
                    List stockKLineVOList = (List) get();

                    kLineDay = new ChartPanel(createChart(new KLineVO(stockKLineVOList), 7));

                    tabbedPane.addTab("日K", kLineDay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    /**
     * 获取周K线
     */
    private void getKLineWeek() throws Exception {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {

                return kLineDataService.getKLineWeek(stockCode);
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void done() {
                try {
                    List stockKLineVOList = (List) get();

                    kLineWeek = new ChartPanel(createChart(new KLineVO(stockKLineVOList), 50));

                    tabbedPane.addTab("周K", kLineWeek);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    /**
     * 获取月K线
     */
    private void getKLineMonth() throws Exception {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                return kLineDataService.getKLineMonth(stockCode);
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void done() {
                try {
                    List stockKLineVOList = (List) get();

                    kLineMonth = new ChartPanel(createChart(new KLineVO(stockKLineVOList), 200));

                    tabbedPane.addTab("月K", kLineMonth);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    /**
     * 绘制图形
     *
     * @return jfreeChart
     */
    private JFreeChart createChart(KLineVO kLineVO, int interval) {
        //保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
        final OHLCSeriesCollection seriesCollection = kLineVO.getOhlcSeriesCollection();
        //保留成交量数据的集合
        TimeSeriesCollection timeSeriesCollection = kLineVO.getAmountSeriesCollection();
        //设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
        final CandlestickRenderer candlestickRender = new CandlestickRenderer();
        candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
        candlestickRender.setAutoWidthGap(0.001);//设置各个K线图之间的间隔
        candlestickRender.setUpPaint(Color.BLACK);//设置股票上涨的K线图颜色
        candlestickRender.setDownPaint(Color.GREEN);//设置股票下跌的K线图颜色
        candlestickRender.setSeriesOutlinePaint(0, Color.RED);
        candlestickRender.setSeriesOutlinePaint(1, Color.GREEN);

        DateAxis x1Axis = new DateAxis();//设置x轴，也就是时间轴
        x1Axis.setAutoRange(false);//设置不采用自动设置时间范围
        try {
            x1Axis.setRange(dateFormat.parse("2007-08-20"), dateFormat.parse("2007-09-29"));//设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
        } catch (Exception e) {
            e.printStackTrace();
        }
        x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期，使图形看上去连续
        x1Axis.setAutoTickUnitSelection(false);//设置不采用自动选择刻度值
        x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
        x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
        x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, interval));//设置时间刻度的间隔，一般以周为单位
        x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//设置显示时间的格式

        //设置k线图y轴参数
        NumberAxis y1Axis = new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的X轴设置
        y1Axis.setAutoRange(false);//不使用自动设定范围
        y1Axis.setRange(kLineVO.getLow() * 0.9, kLineVO.getHigh() * 1.1);//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
        y1Axis.setTickUnit(new NumberTickUnit((kLineVO.getHigh() * 1.1 - kLineVO.getLow() * 0.9) / 10));//设置刻度显示的密度

        // 绘制均线
        StandardXYItemRenderer rendereravg = new StandardXYItemRenderer(
                StandardXYItemRenderer.SHAPES_AND_LINES);
        rendereravg.setShapesFilled(true);
        rendereravg.setSeriesPaint(0, new Color(253, 188, 64));
        rendereravg.setSeriesPaint(1, new Color(102, 14, 122));
        rendereravg.setSeriesPaint(2, Color.white);

        XYPlot plot1 = new XYPlot(null, x1Axis, y1Axis, null);//设置画图区域对象
        plot1.setDataset(0, seriesCollection);
        plot1.setRenderer(0, candlestickRender);
        plot1.setDataset(1, kLineVO.getAvgSeriesCollection());
        plot1.setRenderer(1, rendereravg);
        plot1.setBackgroundPaint(Color.BLACK);
        plot1.setDomainGridlinesVisible(false);//不显示网格
        plot1.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

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

        //建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
        XYPlot plot2 = new XYPlot(timeSeriesCollection, null, y2Axis, xyBarRender);
        plot2.setBackgroundPaint(Color.BLACK);
        plot2.setRangeGridlinePaint(Color.BLACK);// 设置横轴参考线颜色
        plot2.setDomainGridlinePaint(Color.BLACK);// 设置纵轴参考线颜色

        CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//建立一个恰当的联合图形区域对象，以x轴为共享轴
        combineddomainxyplot.add(plot1, 2);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
        combineddomainxyplot.add(plot2, 1);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
        combineddomainxyplot.setGap(10);//设置两个图形区域对象之间的间隔空间

        return new JFreeChart("中国联通", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, true);
    }
}

/**
 * k线数据对象
 */
class KLineVO {

    private OHLCSeriesCollection ohlcSeriesCollection;

    private TimeSeriesCollection amountSeriesCollection;

    private TimeSeriesCollection avgSeriesCollection;

    private double high = Double.MIN_VALUE, low = Double.MAX_VALUE,
            high_amount = Double.MIN_VALUE, low_amount = Double.MAX_VALUE;

    KLineVO(List<StockKLineVO> stockKLineVOList) {
        OHLCSeries ohlcSeries = new OHLCSeries("");
        TimeSeries timeSeries = new TimeSeries("");

        Day day;
        StockKLineVO kLineVO;
        for (int i = 0; i < 50; i++) {
            kLineVO = stockKLineVOList.get(i);
            day = new Day(TimeUtil.getDate(kLineVO.getDate()));
            ohlcSeries.add(day,
                    kLineVO.getOpen(), kLineVO.getHigh(), kLineVO.getLow(), kLineVO.getClose());

            timeSeries.add(day, kLineVO.getVolume());
        }

        ohlcSeriesCollection = new OHLCSeriesCollection();
        ohlcSeriesCollection.addSeries(ohlcSeries);

        amountSeriesCollection = new TimeSeriesCollection();
        amountSeriesCollection.addSeries(timeSeries);

        avgSeriesCollection = new TimeSeriesCollection();

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
        int seriesCount2 = amountSeriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
        for (int i = 0; i < seriesCount2; i++) {
            int itemCount = amountSeriesCollection.getItemCount(i);//每一个序列有多少个数据项
            for (int j = 0; j < itemCount; j++) {
                if (high_amount < amountSeriesCollection.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
                    high_amount = amountSeriesCollection.getYValue(i, j);
                }
                if (low_amount > amountSeriesCollection.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
                    low_amount = amountSeriesCollection.getYValue(i, j);
                }
            }
        }
    }

    OHLCSeriesCollection getOhlcSeriesCollection() {
        return ohlcSeriesCollection;
    }

    TimeSeriesCollection getAmountSeriesCollection() {
        return amountSeriesCollection;
    }

    TimeSeriesCollection getAvgSeriesCollection() {
        return avgSeriesCollection;
    }

    double getHigh() {
        return high;
    }

    double getLow() {
        return low;
    }

    double getHigh_amount() {
        return high_amount;
    }

    double getLow_amount() {
        return low_amount;
    }
}
