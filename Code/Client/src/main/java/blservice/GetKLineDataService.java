package blservice;

import vo.StockKLineVO;

import java.util.List;

/**
 * Created by song on 16-8-25.
 * <p>
 * 从服务器获取K线图所需数据
 */
public interface GetKLineDataService {
    /**
     * 获取日K线数据
     *
     * @param stockCode 股票代码
     * @return 股票K线数据
     */
    List<StockKLineVO> getKLineDay(String stockCode);

    /**
     * 获取周K线数据
     *
     * @param stockCode 股票代码
     * @return 股票K线数据
     */
    List<StockKLineVO> getKLineWeek(String stockCode);

    /**
     * 获取月K线数据
     *
     * @param stockCode 股票代码
     * @return 股票K线数据
     */
    List<StockKLineVO> getKLineMonth(String stockCode);
}
