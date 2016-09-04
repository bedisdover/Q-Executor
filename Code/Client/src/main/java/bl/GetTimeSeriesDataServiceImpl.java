package bl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.NumberUtil;
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
			double price=0;
			double volume=0;
			for(int i=(size-1);i>=0;i--){
				StockTimeSeriesVO stockTimeSeriesVO=new StockTimeSeriesVO();
				JSONObject jsonObj=jsonArray.getJSONObject(i);
				if (jsonObj.getDouble("price") != 0) {
					price += jsonObj.getDouble("price") * jsonObj.getDouble("volume");
					volume += jsonObj.getDouble("volume");
					double ave = 0;
					if (volume != 0) {
						DecimalFormat df = new DecimalFormat("0.00");
						ave = price / volume;
						ave = Double.parseDouble(df.format(ave));
					}
					stockTimeSeriesVO.setAvePrice(ave);
					stockTimeSeriesVO.setTimeLine((String) jsonObj.get("time"));
					stockTimeSeriesVO.setPrice((Double) jsonObj.get("price"));
					stockTimeSeriesVO.setVolume(NumberUtil.round(jsonObj.getDouble("volume")));
					stockList.add(stockTimeSeriesVO);
				}
			}
		return stockList;
	}
}
