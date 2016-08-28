package bl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import vo.StockKLineVO;
import blservice.GetKLineDataService;

/**
 * Created by Tian on 16-8-26.
 * <p>
 * 从服务器获取K线图所需数据
 */
public class GetKLineDataServiceImpl implements GetKLineDataService{

	public List<StockKLineVO> getKLineDay(String stockCode) {
		String url="http://121.42.143.164/KLineDay?codeNum="+stockCode;
		return getDate(url);
	}

	public List<StockKLineVO> getKLineWeek(String stockCode) {
		String url="http://121.42.143.164/KLineWeek?codeNum="+stockCode;
		return getDate(url);
	}

	public List<StockKLineVO> getKLineMonth(String stockCode) {
		String url="http://121.42.143.164/KLineMonth?codeNum="+stockCode;
		return getDate(url);
	}
	
	public List<StockKLineVO> getDate(String url){
		List<StockKLineVO> stockList=new ArrayList<StockKLineVO>();
		try {
			URL ur=new URL(url);
			BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
			String line=reader.readLine();
			JSONArray jsonArray=new JSONArray(line);
			int size=jsonArray.length();
			for(int i=0;i<size;i++){
				StockKLineVO stockKLineVO=new StockKLineVO();
				JSONObject jsonObj=jsonArray.getJSONObject(i);
				stockKLineVO.setDate((String)jsonObj.get("date"));
				stockKLineVO.setOpen((Double) jsonObj.get("open"));
				stockKLineVO.setHigh((Double) jsonObj.get("high"));
				stockKLineVO.setClose((Double) jsonObj.get("close"));
				stockKLineVO.setLow((Double) jsonObj.get("low"));
				stockKLineVO.setVolume((Double) jsonObj.get("volume"));
				stockKLineVO.setPrice_change((Double) jsonObj.get("price_change"));
				stockKLineVO.setP_change((Double) jsonObj.get("p_change"));
				stockKLineVO.setMa5((Double) jsonObj.get("ma5"));
				stockKLineVO.setMa10((Double) jsonObj.get("ma10"));
				stockKLineVO.setMa10((Double) jsonObj.get("ma20"));
				stockKLineVO.setV_ma5((Double) jsonObj.get("v_ma5"));
				stockKLineVO.setV_ma10((Double) jsonObj.get("v_ma10"));
				stockKLineVO.setV_ma20((Double) jsonObj.get("v_ma20"));
				stockKLineVO.setTurnover((Double) jsonObj.get("turnover"));
				stockList.add(stockKLineVO);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stockList;
	}


}