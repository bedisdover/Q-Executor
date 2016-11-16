package blservice.vwap;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;

/**
 * Created by ZhangYF on 2016/11/17.
 */
public interface StockDataService {

    /**
     * 获得某天各时间段的股票交易量
     * @param stockid
     * @param date
     * @return
     */
    public List<Double> getVolList(String stockid,Date date);

    /**
     * 获得某天各时间段的股票交易价格
     * @param stockid
     * @param date
     * @return
     */
    public List<Double> getPriceList(String stockid,Date date);
}
