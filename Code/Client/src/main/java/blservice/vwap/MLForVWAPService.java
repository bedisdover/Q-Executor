package blservice.vwap;

import org.json.JSONException;
import vo.MLForVWAPPriceVO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by song on 16-9-9.
 *
 * VWAP算法相关
 */
public interface MLForVWAPService {

    /**
     * 获取最新数据下动态预测的均价
     * @param stockID 股票代码
     */
    MLForVWAPPriceVO getDynamicPrice(String stockID) throws IOException, JSONException;

    /**
     * 获取最新数据下静态预测的48个成交量
     * @param stockID 股票代码
     */
    List<Integer> getStaticVol(String stockID) throws IOException, JSONException;
}
