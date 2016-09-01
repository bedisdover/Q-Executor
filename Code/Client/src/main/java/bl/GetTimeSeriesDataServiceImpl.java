package bl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import vo.StockTimeSeriesVO;
import blservice.GetTimeSeriesDataService;

public class GetTimeSeriesDataServiceImpl implements GetTimeSeriesDataService{

	public List<StockTimeSeriesVO> getData(String codeNum) throws Exception {
		String url="http://121.42.143.164/StockInfoByTime?codeNum="+codeNum;
		List<StockTimeSeriesVO> stockList=new ArrayList<StockTimeSeriesVO>();
			URL ur=new URL(url);
			BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
			String line=reader.readLine();
			JSONArray jsonArray=new JSONArray(line);
			int size=jsonArray.length();
			for(int i=0;i<size;i++){
				StockTimeSeriesVO stockTimeSeriesVO=new StockTimeSeriesVO();
				JSONObject jsonObj=jsonArray.getJSONObject(i);
				stockTimeSeriesVO.setTimeLine((String) jsonObj.get("time"));
				stockTimeSeriesVO.setPrice((Double) jsonObj.get("price"));
				stockList.add(stockTimeSeriesVO);
			}
		return stockList;
	}
	
	
}
