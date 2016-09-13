package blservice.stock;

import vo.StockTimeSeriesVO;

import java.util.List;

/**
 * Created by song on 16-8-26.
 *
 * 获取分时图数据
 */
public interface GetTimeSeriesDataService {
    /**
     * 获取分时数据
     * @param codeNum 股票代码
     * @return 分时数据
     * @throws Exception 
     */
<<<<<<< HEAD:Code/Client/src/main/java/blservice/GetTimeSeriesDataService.java


    List<StockTimeSeriesVO> getData(String codeNum) throws Exception;


=======
    List<StockTimeSeriesVO> getData(String codeNum) throws Exception;
>>>>>>> origin/develop:Code/Client/src/main/java/blservice/stock/GetTimeSeriesDataService.java
}
