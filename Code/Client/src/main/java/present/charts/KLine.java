package present.charts;

import bl.stock.GetKLineDataServiceImpl;
import blservice.stock.GetKLineDataService;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.*;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import present.component.chart.MyChartPanel;
import present.component.chart.MyPanel;
import present.component.chart.MyTimeline;
import present.utils.ColorUtil;
import util.NumberUtil;
import util.TimeUtil;
import vo.StockKLineVO;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by song on 16-8-25.
 * <p>
 * K线图(panel作为容器)
 * 包括日K、周K、月K
 */
public class KLine {

    /**
     * 5分钟K线
     */
    public static final int KLINE5MINUTE = 0;

    /**
     * 15分钟K线
     */
    public static final int KLINE15MINUTE = 1;

    /**
     * 30分钟K线
     */
    public static final int KLINE30MINUTE = 2;

    /**
     * 60分钟K线
     */
    public static final int KLINE60MINUTE = 3;

    /**
     * 日K线
     */
    public static final int KLINE_DAY = 4;

    /**
     * 周K线
     */
    public static final int KLINE_WEEK = 5;

    /**
     * 月K线
     */
    public static final int KLINE_MONTH = 6;

    private GetKLineDataService kLineDataService = new GetKLineDataServiceImpl();

    private JTabbedPane tabbedPane;

    private JPanel kLine5Minute, kLine15Minute, kLine30Minute, kLine60Minute, kLineDay, kLineWeek, kLineMonth;

    private String stockCode;

    public JTabbedPane getKLine(String stockCode) throws Exception {
        this.stockCode = stockCode;
        tabbedPane = new JTabbedPane();

        getKLineMinute(5);
        getKLineMinute(15);
        getKLineMinute(30);
        getKLineMinute(60);
        getKLineDay();
        getKLineWeek();
        getKLineMonth();

        return tabbedPane;
    }

    /**
     * 获取5/15/30/60分钟均线
     */
    private void getKLineMinute(int interval) {
        SwingWorker<List<StockKLineVO>, Void> worker = new SwingWorker<List<StockKLineVO>, Void>() {
            @Override
            protected List<StockKLineVO> doInBackground() throws Exception {
                return kLineDataService.getKLineMinute(stockCode, interval);
            }

            @Override
            protected void done() {
                try {
                    List<StockKLineVO> stockKLineVOList = get();

                    switch (interval) {
                        case 5:
                            kLine5Minute = createChart(new KLineVO(stockKLineVOList, KLINE5MINUTE), KLINE5MINUTE);
                            tabbedPane.addTab("5分", kLine5Minute);
                            break;
                        case 15:
                            kLine15Minute = createChart(new KLineVO(stockKLineVOList, KLINE15MINUTE), KLINE15MINUTE);
                            tabbedPane.addTab("15分", kLine15Minute);
                            break;
                        case 30:
                            kLine30Minute = createChart(new KLineVO(stockKLineVOList, KLINE30MINUTE), KLINE30MINUTE);
                            tabbedPane.addTab("30分", kLine30Minute);
                            break;
                        case 60:
                            kLine60Minute = createChart(new KLineVO(stockKLineVOList, KLINE60MINUTE), KLINE60MINUTE);
                            tabbedPane.addTab("60分", kLine60Minute);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    /**
     * 获取日K线
     */
    private void getKLineDay() throws Exception {
        SwingWorker<List<StockKLineVO>, Void> worker = new SwingWorker<List<StockKLineVO>, Void>() {
            @Override
            protected List<StockKLineVO> doInBackground() throws Exception {
                return kLineDataService.getKLineDay(stockCode);
            }

            @Override
            protected void done() {
                try {
                    List<StockKLineVO> stockKLineVOList = get();

                    kLineDay = createChart(new KLineVO(stockKLineVOList, KLINE_DAY), KLINE_DAY);

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
        SwingWorker<List<StockKLineVO>, Void> worker = new SwingWorker<List<StockKLineVO>, Void>() {
            @Override
            protected List<StockKLineVO> doInBackground() throws Exception {

                return kLineDataService.getKLineWeek(stockCode);
            }

            @Override
            protected void done() {
                try {
                    List<StockKLineVO> stockKLineVOList = get();

                    kLineWeek = createChart(new KLineVO(stockKLineVOList, KLINE_WEEK), KLINE_WEEK);

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
        SwingWorker<List<StockKLineVO>, Void> worker = new SwingWorker<List<StockKLineVO>, Void>() {
            @Override
            protected List<StockKLineVO> doInBackground() throws Exception {
                return kLineDataService.getKLineMonth(stockCode);
            }

            @Override
            protected void done() {
                try {
                    List<StockKLineVO> stockKLineVOList = get();

                    kLineMonth = createChart(new KLineVO(stockKLineVOList, KLINE_MONTH), KLINE_MONTH);

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
     */
    private JPanel createChart(KLineVO kLineVO, int kLineType) {
        //保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
        final OHLCSeriesCollection seriesCollection = kLineVO.getOhlcSeriesCollection();
        //保留成交量数据的集合
        TimeSeriesCollection timeSeriesCollection = kLineVO.getAmountSeriesCollection();
        //设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
        final CandlestickRenderer candlestickRender = new CandlestickRenderer() {
            @Override
            public Paint getItemOutlinePaint(int row, int column) {
                return ColorUtil.getColorByComparing(seriesCollection.getCloseValue(row, column),
                        seriesCollection.getOpenValue(row, column));
            }
        };
        candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
        candlestickRender.setAutoWidthGap(0.001);//设置各个K线图之间的间隔
        candlestickRender.setCandleWidth(5);
        candlestickRender.setUpPaint(Color.BLACK);//设置股票上涨的K线图颜色
        candlestickRender.setDownPaint(ColorUtil.DEC_COLOR);//设置股票下跌的K线图颜色
        candlestickRender.setSeriesVisibleInLegend(0, false);//设置不显示legend（数据颜色提示)
        candlestickRender.setSeriesVisibleInLegend(1, false);//设置不显示legend（数据颜色提示)

        // 时间轴
        DateAxis dateAxis = createDateAxis(kLineVO, kLineType);

        //设置k线图y轴参数
        NumberAxis y1Axis = new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的X轴设置
        y1Axis.setAutoRange(false);//不使用自动设定范围
        y1Axis.setRange(kLineVO.getRange());//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
        y1Axis.setUpperMargin(5);//设置向上边框距离

        //设置均线图画图器
        XYLineAndShapeRenderer lineAndShapeRenderer = new XYLineAndShapeRenderer();
        lineAndShapeRenderer.setBaseItemLabelsVisible(true);
        lineAndShapeRenderer.setSeriesShapesVisible(0, false);//设置不显示数据点模型
        lineAndShapeRenderer.setSeriesShapesVisible(1, false);
        lineAndShapeRenderer.setSeriesShapesVisible(2, false);
        lineAndShapeRenderer.setSeriesPaint(0, Color.WHITE);//设置均线颜色
        lineAndShapeRenderer.setSeriesPaint(1, Color.YELLOW);
        lineAndShapeRenderer.setSeriesPaint(2, Color.MAGENTA);

        //设置柱状图参数
        XYPlot plot1 = new XYPlot(null, dateAxis, y1Axis, null);//设置画图区域对象
        plot1.setDataset(0, seriesCollection);
        plot1.setRenderer(0, candlestickRender);
        plot1.setDataset(1, kLineVO.getAvgPriceCollection());
        plot1.setRenderer(1, lineAndShapeRenderer);
        plot1.setBackgroundPaint(Color.BLACK);
        plot1.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        plot1.setDomainGridlinesVisible(false);//不显示网格

        XYBarRenderer xyBarRender = new XYBarRenderer() {
            public Paint getItemPaint(int i, int j) {//匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
                if (seriesCollection.getCloseValue(i, j) > seriesCollection.getOpenValue(i, j)) {//收盘价高于开盘价，股票上涨，选用股票上涨的颜色
                    return candlestickRender.getUpPaint();
                } else {
                    return candlestickRender.getDownPaint();
                }
            }

            @Override
            public Paint getItemOutlinePaint(int row, int column) {
                return ColorUtil.getColorByComparing(seriesCollection.getCloseValue(row, column),
                        seriesCollection.getOpenValue(row, column));
            }
        };
        xyBarRender.setMargin(0.3);//设置柱形图之间的间隔
        xyBarRender.setDrawBarOutline(true);//设置显示边框线
        xyBarRender.setBarPainter(new StandardXYBarPainter());//取消渐变效果
        xyBarRender.setSeriesVisibleInLegend(0, false);//设置不显示legend（数据颜色提示)
        xyBarRender.setSeriesVisibleInLegend(1, false);
        xyBarRender.setShadowVisible(false);//设置没有阴影

        //设置柱状图y轴参数
        NumberAxis y2Axis = new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的y轴设置
        y2Axis.setAutoRange(true);

        // 绘制成交量的均线
        XYLineAndShapeRenderer lineAndShapeRenderer2 = null;
        try {
            lineAndShapeRenderer2 = (XYLineAndShapeRenderer) lineAndShapeRenderer.clone();
            lineAndShapeRenderer2.setSeriesVisibleInLegend(0, false);
            lineAndShapeRenderer2.setSeriesVisibleInLegend(1, false);
            lineAndShapeRenderer2.setSeriesVisibleInLegend(2, false);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        //建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
        XYPlot plot2 = new XYPlot(null, null, y2Axis, null);
        plot2.setDataset(0, timeSeriesCollection);
        plot2.setRenderer(0, xyBarRender);
        plot2.setDataset(1, kLineVO.getAvgVolumeCollection());
        plot2.setRenderer(1, lineAndShapeRenderer2);
        plot2.setBackgroundPaint(Color.BLACK);
        plot2.setDomainGridlinesVisible(false);//不显示网格

        CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(dateAxis);//建立一个恰当的联合图形区域对象，以x轴为共享轴
        combineddomainxyplot.add(plot1, 2);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
        combineddomainxyplot.add(plot2, 1);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
        combineddomainxyplot.setGap(10);//设置两个图形区域对象之间的间隔空间

        return new MyPanel(new MyChartPanel(new JFreeChart(combineddomainxyplot)), kLineVO);
    }

    /**
     * 创建时间轴
     *
     * @param kLineVO   K线所需数据
     * @param kLineType K线类型
     */
    private DateAxis createDateAxis(KLineVO kLineVO, int kLineType) {
        //设置x轴，也就是时间轴
        DateAxis dateAxis = new DateAxis() {
            protected List<DateTick> refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
                List ticks = super.refreshTicksHorizontal(g2, dataArea, edge);

                Map<String, DateTick> newTicks = new HashMap<>();
                for (Object tick1 : ticks) {
                    //在这里可以添加删除时间轴刻度，添加时间轴刻度时要对时间格式进行转换:
                    //DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
                    DateTick tick = (DateTick) tick1;
                    if (kLineType == KLINE60MINUTE) {
                        System.out.println(tick.getDate());
                    }
                    newTicks.put(tick.getText(), new DateTick(tick.getDate(), tick.getText(), TextAnchor.TOP_CENTER, TextAnchor.TOP_RIGHT, 0));
                }

                return Collections.list(Collections.enumeration(newTicks.values()));
            }
        };

        if (kLineType >= KLINE_DAY) {
            dateAxis.setAutoRange(false);
            dateAxis.setRange(kLineVO.getStartDate(), kLineVO.getEndDate());
            dateAxis.setAutoTickUnitSelection(false);
            dateAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
            dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
            dateAxis.setTickUnit(getTickUnit(kLineType));
            dateAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
            dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
        } else {
            dateAxis.setAutoRange(false);//设置不采用自动设置时间范围
            if (kLineType == KLINE5MINUTE) {
                dateAxis.setRange(new Date(116, 8, 2, 13, 0), new Date(116, 8, 2, 15, 0));
            } else {
                dateAxis.setRange(kLineVO.getStartTime(), kLineVO.getEndTime());
            }
            dateAxis.setTimeline(MyTimeline.getTimeLine(kLineType));
//            timeline.addException();
//            dateAxis.setAutoTickUnitSelection(true);//设置不采用自动选择刻度值
            dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
//            dateAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
            dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));//设置显示时间的格式
        }

        return dateAxis;
    }

    /**
     * 获取时间间隔
     *
     * @param kLineType K线类型
     */
    private DateTickUnit getTickUnit(int kLineType) {
        DateTickUnit dateTickUnit = null;
        switch (kLineType) {
            case KLINE_MONTH:
                dateTickUnit = new DateTickUnit(DateTickUnitType.MONTH, 8);
                break;
            case KLINE_WEEK:
                dateTickUnit = new DateTickUnit(DateTickUnitType.DAY, 80);
                break;
            case KLINE_DAY:
                dateTickUnit = new DateTickUnit(DateTickUnitType.DAY, 20);
                break;
        }

        return dateTickUnit;
    }

    /**
     * k线数据对象
     */
    private class KLineVO extends ChartVO {

        private List<StockKLineVO> stockKLineVOList;

        private OHLCSeriesCollection ohlcSeriesCollection;

        private TimeSeriesCollection amountSeriesCollection;

        private TimeSeriesCollection avgPriceCollection;

        private TimeSeriesCollection avgVolumeCollection;

        private double high = Double.MIN_VALUE, low = Double.MAX_VALUE;

        private int kLineType;

        KLineVO(List<StockKLineVO> stockKLineVOList, int kLineType) {
            this.stockKLineVOList = stockKLineVOList;
            this.kLineType = kLineType;

            OHLCSeries ohlcSeries = new OHLCSeries("");
            TimeSeries amountSeries = new TimeSeries("");
            TimeSeries ma5PriceSeries = new TimeSeries("ma5");
            TimeSeries ma10PriceSeries = new TimeSeries("ma10");
            TimeSeries ma20PriceSeries = new TimeSeries("ma20");
            TimeSeries ma5VolumeSeries = new TimeSeries("ma5Volume");
            TimeSeries ma10VolumeSeries = new TimeSeries("ma10Volume");
            TimeSeries ma20VolumeSeries = new TimeSeries("ma20Volume");

            RegularTimePeriod timePeriod;
            for (StockKLineVO kLineVO : stockKLineVOList) {
                timePeriod = getTimePeriod(kLineVO.getDate(), kLineType);
                ohlcSeries.add(timePeriod, kLineVO.getOpen(), kLineVO.getHigh(), kLineVO.getLow(), kLineVO.getClose());

                amountSeries.add(timePeriod, kLineVO.getVolume());
                ma5PriceSeries.add(timePeriod, kLineVO.getMa5());
                ma10PriceSeries.add(timePeriod, kLineVO.getMa10());
                ma20PriceSeries.add(timePeriod, kLineVO.getMa20());
                ma5VolumeSeries.add(timePeriod, kLineVO.getV_ma5());
                ma10VolumeSeries.add(timePeriod, kLineVO.getV_ma10());
                ma20VolumeSeries.add(timePeriod, kLineVO.getV_ma20());
            }

            ohlcSeriesCollection = new OHLCSeriesCollection();
            ohlcSeriesCollection.addSeries(ohlcSeries);

            amountSeriesCollection = new TimeSeriesCollection();
            amountSeriesCollection.addSeries(amountSeries);

            avgPriceCollection = new TimeSeriesCollection();
            avgPriceCollection.addSeries(ma5PriceSeries);
            avgPriceCollection.addSeries(ma10PriceSeries);
            avgPriceCollection.addSeries(ma20PriceSeries);

            avgVolumeCollection = new TimeSeriesCollection();
            avgVolumeCollection.addSeries(ma5VolumeSeries);
            avgVolumeCollection.addSeries(ma10VolumeSeries);
            avgVolumeCollection.addSeries(ma20VolumeSeries);

            calculate();
        }

        private RegularTimePeriod getTimePeriod(String time, int kLineType) {
            if (kLineType >= 0 && kLineType <= KLINE60MINUTE) {
                return getMinute(time);
            } else {
                return getDay(time);
            }
        }

        private Minute getMinute(String time) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
            Minute minute = null;

            try {
                minute = new Minute(dateFormat.parse(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return minute;
        }

        private Day getDay(String time) {
            return new Day(TimeUtil.getDate(time));
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
        }

        OHLCSeriesCollection getOhlcSeriesCollection() {
            return ohlcSeriesCollection;
        }

        TimeSeriesCollection getAmountSeriesCollection() {
            return amountSeriesCollection;
        }

        TimeSeriesCollection getAvgPriceCollection() {
            return avgPriceCollection;
        }

        TimeSeriesCollection getAvgVolumeCollection() {
            return avgVolumeCollection;
        }

        Range getRange() {
            if (high > low) {
                double temp = (high - low) * 0.1;

                return new Range(low - temp, high + temp);
            } else {
                return new Range(0, 0);
            }
        }

        /**
         * 日/周/月调用
         */
        Date getStartDate() {
            return TimeUtil.getDayBefore(stockKLineVOList.get(0).getDate());
        }

        /**
         * 日/周/月调用
         */
        Date getEndDate() {
            return TimeUtil.getDayAfter(stockKLineVOList.get(stockKLineVOList.size() - 1).getDate());
        }

        /**
         * 分钟线调用
         */
        Date getStartTime() {
            Date date = null;
            try {
                date = TimeUtil.getDetailTime(stockKLineVOList.get(0).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return date;
        }

        /**
         * 分钟线调用
         */
        Date getEndTime() {
            Date date = null;
            try {
                date = TimeUtil.getDetailTime(stockKLineVOList.get(stockKLineVOList.size() - 1).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return date;
        }

        public String getText(int item) {
            StockKLineVO kLineVO = stockKLineVOList.get(item);

            return kLineVO.getDate() + "   开 " + kLineVO.getOpen() + "  高 " + kLineVO.getHigh()
                    + "  收 " + kLineVO.getClose() + "  低 " + kLineVO.getLow()
                    + "  量 " + NumberUtil.transferUnit(kLineVO.getVolume()) + "手"
                    + "  幅 " + kLineVO.getP_change() + "%";
        }
    }

}

