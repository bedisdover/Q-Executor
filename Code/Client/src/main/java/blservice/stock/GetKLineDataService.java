package blservice.stock;

import vo.StockKLineVO;

import java.util.List;

/**
 * Created by song on 16-8-25.
 * <p>
 * 从服务器获取K线图所需数据
 */
public interface GetKLineDataService {

    /**
     * 获取5/15/30/60/分钟均线
     * url: /KLineMinute?codeNum=...&interval=5/15/30/60
     * @param stockCode 股票代码
     */
    List<StockKLineVO> getKLineMinute(String stockCode, int interval) throws Exception;

    /**
     * 获取日K线数据
     *
     * @param stockCode 股票代码
     * @return 股票K线数据
     * @throws Exception 
     */
    List<StockKLineVO> getKLineDay(String stockCode) throws Exception;

    /**
     * 获取周K线数据
     *
     * @param stockCode 股票代码
     * @return 股票K线数据
     * @throws Exception 
     */
    List<StockKLineVO> getKLineWeek(String stockCode) throws Exception;

    /**
     * 获取月K线数据
     *
     * @param stockCode 股票代码
     * @return 股票K线数据
     * @throws Exception 
     */
    List<StockKLineVO> getKLineMonth(String stockCode) throws Exception;
}
