package bl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import vo.StockBasicInfoVO;
import vo.StockKLineVO;
import vo.StockNowTimeVO;
import blservice.GetStockDataService;

public class GetStockDataServiceImpl implements GetStockDataService{

	public List<StockNowTimeVO> getNowTimeData(String... codeNum) {
		
		List<StockNowTimeVO> stockList=new ArrayList<StockNowTimeVO>();
		try {
			for(int i=0;i<codeNum.length;i++){
				StockNowTimeVO stockNowTimeVO=new StockNowTimeVO();
				String url="http://hq.finance.ifeng.com/q.php?l="+codeNum[i];
				URL ur=new URL(url);
				BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
				String line=reader.readLine();
				JSONObject jsonArray=new JSONObject(line.substring(11));
				JSONArray a=(JSONArray)jsonArray.get(codeNum[i]);
				stockNowTimeVO.setCode(codeNum[i]);
				stockNowTimeVO.setPrice(a.getDouble(0));
				stockNowTimeVO.setClose(a.getDouble(1));
				stockNowTimeVO.setIncNum(a.getDouble(2));
				stockNowTimeVO.setIncRate(a.getDouble(3));
				stockNowTimeVO.setOpen(a.getDouble(4));
				stockNowTimeVO.setAmount(a.getDouble(9));
				stockNowTimeVO.setVolume(a.getDouble(10));
				stockNowTimeVO.setBuy1price(a.getDouble(11));
				stockNowTimeVO.setBuy2price(a.getDouble(12));
				stockNowTimeVO.setBuy3Price(a.getDouble(13));
				stockNowTimeVO.setBuy4Price(a.getDouble(14));
				stockNowTimeVO.setBuy5Price(a.getDouble(15));
				stockNowTimeVO.setBuy1amount(a.getDouble(16));
				stockNowTimeVO.setBuy2amount(a.getDouble(17));
				stockNowTimeVO.setBuy3amount(a.getDouble(18));
				stockNowTimeVO.setBuy4amount(a.getDouble(19));
				stockNowTimeVO.setBuy5amount(a.getDouble(20));
				stockNowTimeVO.setSell1price(a.getDouble(21));
				stockNowTimeVO.setSell2price(a.getDouble(22));
				stockNowTimeVO.setSell3price(a.getDouble(23));
				stockNowTimeVO.setSell4price(a.getDouble(24));
				stockNowTimeVO.setSell5price(a.getDouble(25));
				stockNowTimeVO.setSell1amount(a.getDouble(26));
				stockNowTimeVO.setSell2amount(a.getDouble(27));
				stockNowTimeVO.setSell3amount(a.getDouble(28));
				stockNowTimeVO.setSell4amount(a.getDouble(29));
				stockNowTimeVO.setSell5amount(a.getDouble(30));
				Date dt = new Date(a.getLong(34));
				stockNowTimeVO.setTime(dt);
				stockList.add(stockNowTimeVO);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stockList;
	}
	
	public StockBasicInfoVO getBasicInfo(String codeNum) {
		String url="http://121.42.143.164/BasicComInfo?codeNum=sh600000";
		StockBasicInfoVO stockBasicInfoVO=new StockBasicInfoVO();
		try {
			URL ur=new URL(url);
			BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
			String line=reader.readLine();
			JSONObject jsonArray=new JSONObject(line);
			stockBasicInfoVO.setCode(codeNum);
			stockBasicInfoVO.setName((String) jsonArray.get("name"));
			stockBasicInfoVO.setIndustry((String) jsonArray.get("industry"));
			stockBasicInfoVO.setArea((String) jsonArray.get("area"));
			stockBasicInfoVO.setPe( (Double) jsonArray.get("pe"));
			stockBasicInfoVO.setOutstanding( (Double) jsonArray.get("outstanding"));
			stockBasicInfoVO.setTotals( (Double) jsonArray.get("totals"));
			stockBasicInfoVO.setTotalAssets( (Double) jsonArray.get("totalAssets"));
			stockBasicInfoVO.setLiquidAssets( (Double) jsonArray.get("liquidAssets"));
			stockBasicInfoVO.setFixedAssets( (Double) jsonArray.get("fixedAssets"));
			stockBasicInfoVO.setReserved( (Double) jsonArray.get("reserved"));
			stockBasicInfoVO.setReservedPerShare( (Double) jsonArray.get("reservedPerShare"));
			stockBasicInfoVO.setEps( (Double) jsonArray.get("eps"));
			stockBasicInfoVO.setBvps( (Double) jsonArray.get("bvps"));
			stockBasicInfoVO.setPb( (Double) jsonArray.get("pb"));
			Date dt = new Date((Long) jsonArray.get("timeToMarket"));
			stockBasicInfoVO.setTimeToMarket(dt);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stockBasicInfoVO;
	}

}
