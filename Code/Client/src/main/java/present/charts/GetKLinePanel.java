package present.charts;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

import javax.swing.*;

/**
 * Created by song on 16-8-25.
 *
 * 获取K线图(panel作为容器)
 *  包括日K、周K、月K
 */
public class GetKLinePanel {
    /**
     * 获取日K线
     * @param stockCode 股票代码
     * @return 包含日K线的panel
     */
    public JPanel getKLineDay(String stockCode) {

        return new JPanel();
    }

    /**
     * 获取周K线
     * @param stockCode 股票代码
     * @return 包含周K线的panel
     */
    public JPanel getKLineWeek(String stockCode) {
        return new JPanel();
    }

    /**
     * 获取月K线
     * @param stockCode 股票代码
     * @return 包含月K线的panel
     */
    public JPanel getKLineMonth(String stockCode) {
        return new JPanel();
    }

    private JPanel createChart() {
        //定义k线图数据集
        OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();
        //高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
        OHLCSeries ohlc = new OHLCSeries("");
        //定义上涨和下跌的两个数据集
        OHLCSeries inc_dec = new OHLCSeries("");

        //保留成交量数据的集合
        TimeSeriesCollection volSeriesCollection = new TimeSeriesCollection();
        //对应时间成交量数据
        TimeSeries series3 = new TimeSeries("");
        //对应时间成交量数据
        TimeSeries series4 = new TimeSeries("");


        return new JPanel();
    }


}
