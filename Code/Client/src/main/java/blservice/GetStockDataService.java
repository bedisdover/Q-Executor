package blservice;

import java.net.MalformedURLException;
import java.util.List;

import vo.*;

/**
 * Created by song on 16-8-26.
 * <p>
 * 获取股票数据，包含实时数据、基本信息
 */
public interface GetStockDataService {

    /**
     * 获取实时数据, 可同时获得多只股票数据
     * 单只股票对应url：
     * http://hq.finance.ifeng.com/q.php?l=sh600000
     * 多只股票：
     * http://hq.finance.ifeng.com/q.php?l=sh600000,sh600001
     *
     * @param codeNum 股票代码
     * @return 实时数据，数据含义如下
     * 0:19.71                当前价格
     * 1:19.88                昨日收盘价
     * 2:-0.17                涨跌额
     * 3:-0.86                涨跌幅
     * 4:19.89                今日开盘价
     * 5:20.02                最高
     * 6:19.61                最低
     * 7:19.71
     * 8:19.73
     * 9:867473                成交量
     * 10:17184357.45        成交额
     * 11:19.71                买一报价
     * 12~15                买二至买五报价
     * 16:2900                买一申请2900股
     * 17~20                买二至买五申请
     * 21~25                卖一至卖五报价
     * 26~30                卖一至卖五申请
     * 31:4000
     * 32:2900
     * 33:4000
     * 34:1470881274        当前时间
     * 35:1470881276
     * 36:19.71
     * @throws MalformedURLException 
     */
    List<StockNowTimeVO> getNowTimeData(String... codeNum) throws Exception;

    /**
     * 获取股票基本信息
     * 对应url：http://121.42.143.164/BasicComInfo?codeNum=sh600000
     *
     * @param codeNum 股票代码
     * @return 基本信息
     * @throws Exception 
     */
    StockBasicInfoVO getBasicInfo(String codeNum) throws Exception;

    /**
     * 分价数据
     * url: /StockInfoByPrice
     * @throws Exception 
     */
    List<StockInfoByPrice> getStockInfoByPrice(String codeNum) throws Exception;

    /**
     * 大单数据
     * url: /ComStockInfo
     * @throws Exception 
     */
    List<StockInfoByCom> getComStockInfo(String codeNum) throws Exception;

    /**
     * 大单数据(带参数筛选)
     * url: /ComStockInfoParam"
     * @throws Exception 
     */
    List<StockInfoByCom> getComStockInfo(String codeNum, double param) throws Exception;

    /**
     * 逐笔数据
     * url: /PerStockInfo"
     * @throws Exception 
     */
    List<StockInfoByPer> getPerStockInfo(String codeNum) throws Exception;
    
}
