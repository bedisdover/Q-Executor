package blservice;

import vo.StockTimeSeriesVO;

import java.io.IOException;
import java.net.MalformedURLException;
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
     */
    List<StockTimeSeriesVO> getData(String codeNum) throws IOException;
}
