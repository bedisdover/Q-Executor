package bl;

import blservice.GetStockDataService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.StockUtil;
import vo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GetStockDataServiceImpl implements GetStockDataService{

	public List<StockNowTimeVO> getNowTimeData(String... codeNum) throws Exception {
		
		List<StockNowTimeVO> stockList=new ArrayList<StockNowTimeVO>();
			for(int i=0;i<codeNum.length;i++){
				StockNowTimeVO stockNowTimeVO=new StockNowTimeVO();
				String url="http://hq.finance.ifeng.com/q.php?l="+codeNum[i];
				URL ur=new URL(url);
				BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream(), "utf-8"));
				String line=reader.readLine();
				JSONObject jsonArray=new JSONObject(line.substring(11));
				JSONArray a=(JSONArray)jsonArray.get(codeNum[i]);
				stockNowTimeVO.setCode(codeNum[i]);
				stockNowTimeVO.setPrice(a.getDouble(0));
				stockNowTimeVO.setClose(a.getDouble(1));
				stockNowTimeVO.setIncNum(a.getDouble(2));
				stockNowTimeVO.setIncRate(a.getDouble(3));
				stockNowTimeVO.setOpen(a.getDouble(4));
				stockNowTimeVO.setHigh(a.getDouble(5));
				stockNowTimeVO.setLow(a.getDouble(6));
				stockNowTimeVO.setAmount(a.getDouble(9));
				stockNowTimeVO.setVolume(a.getDouble(10));
				stockNowTimeVO.setBuy1price(a.getDouble(11));
				stockNowTimeVO.setBuy2price(a.getDouble(12));
				stockNowTimeVO.setBuy3price(a.getDouble(13));
				stockNowTimeVO.setBuy4Price(a.getDouble(14));
				stockNowTimeVO.setBuy5price(a.getDouble(15));
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
				Date dt = new Date(a.getLong(34) * 1000);
				stockNowTimeVO.setTime(dt);
				stockList.add(stockNowTimeVO);
			}
		return stockList;
	}

	public StockBasicInfoVO getBasicInfo(String codeNum) throws Exception {
		String url="http://" + Connect.IP + "/BasicComInfo?codeNum=" + codeNum;
		StockBasicInfoVO stockBasicInfoVO=new StockBasicInfoVO();
			URL ur=new URL(url);
			BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream(), "utf-8"));
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
		

		
		return stockBasicInfoVO;
	}

	public List<StockInfoByPrice> getStockInfoByPrice(String codeNum) throws Exception {
		String url="http://" + Connect.IP + "/StockInfoByPrice?codeNum="+codeNum;
		List<StockInfoByPrice> stockList=new ArrayList<StockInfoByPrice>();
			URL ur=new URL(url);
			BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream(), "utf-8"));
			String line=reader.readLine();
			JSONArray jsonArray=new JSONArray(line);
			int size=jsonArray.length();
			for(int i=0;i<size;i++){
				StockInfoByPrice stockKLineVO=new StockInfoByPrice();
				JSONObject jsonObj=jsonArray.getJSONObject(i);
				if (jsonObj.getDouble("price") != 0.0) {
					stockKLineVO.setPercent(jsonObj.getDouble("percent"));
					stockKLineVO.setPrice(jsonObj.getDouble("price"));
					stockKLineVO.setTrunover(jsonObj.getDouble("trunover"));
					stockList.add(stockKLineVO);
				}
			}
		return stockList;
	}

	public List<StockInfoByCom> getComStockInfo(String codeNum) throws Exception {
		String url="http://" + Connect.IP + "/ComStockInfo?codeNum="+codeNum;
		return getComStock(url); 
	}

	public List<StockInfoByCom> getComStockInfo(String codeNum, double param) throws Exception {
		String url="http://" + Connect.IP + "/ComStockInfoParam?codeNum="+codeNum+"&param="+param;
		return getComStock(url);
	}

	public List<StockInfoByPer> getPerStockInfo(String codeNum) throws Exception {
		String url="http://" + Connect.IP + "/PerStockInfo?codeNum="+codeNum;
		List<StockInfoByPer> stockList=new ArrayList<StockInfoByPer>();
			URL ur=new URL(url);
			BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream(), "utf-8"));
			String line=reader.readLine();
			JSONArray jsonArray=new JSONArray(line);
			int size=jsonArray.length();
			for(int i=0;i<size;i++){
				StockInfoByPer stockKLineVO=new StockInfoByPer();
				JSONObject jsonObj=jsonArray.getJSONObject(i);
				if (jsonObj.getDouble("volume") != 0) {
                    stockKLineVO.setTime(jsonObj.getString("time"));
                    stockKLineVO.setPrice(jsonObj.getDouble("price"));
                    stockKLineVO.setVolume(jsonObj.getDouble("volume"));
                    stockKLineVO.setType(jsonObj.getInt("type"));
                    stockKLineVO.setChange_price(jsonObj.getDouble("change_price"));
                    stockKLineVO.setTotalNum(jsonObj.getDouble("totalNum"));
                    stockList.add(stockKLineVO);
				}
			}
		return stockList;
	}

	public List<DeepStockVO> getStockDepth(String codeNum) throws Exception {
		String url="http://" + Connect.IP + "/DeepStock?codeNum="+codeNum;
		List<DeepStockVO> stockList=new ArrayList<DeepStockVO>();
		URL ur=new URL(url);
		BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream(), "utf-8"));
		String line=reader.readLine();
		JSONArray jsonArray=new JSONArray(line);
		int size=jsonArray.length();
		for(int i=0;i<size;i++){
			DeepStockVO stockKLineVO=new DeepStockVO();
			JSONObject jsonObj=jsonArray.getJSONObject(i);
			stockKLineVO.setTimeline(jsonObj.getString("timeline"));
			stockKLineVO.setDeepPrice(jsonObj.getDouble("deepPrice"));
			stockList.add(stockKLineVO);
		}
	return stockList;
	}

	public List<HotStockVO> getHotStock() throws Exception {
//		String url="http://" + Connect.IP + "/HotStocks";
//		List<HotStockVO> stockList=new ArrayList<HotStockVO>();
//		URL ur=new URL(url);
//		BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
//		String line=reader.readLine();
//		JSONArray jsonArray=new JSONArray(line);
//		int size=jsonArray.length();
//		for(int i=0;i<size;i++){
//			HotStockVO stockKLineVO=new HotStockVO();
//			JSONObject jsonObj=jsonArray.getJSONObject(i);
//			stockKLineVO.setCode(jsonObj.getString("code"));
//			stockKLineVO.setName(jsonObj.getString("name"));
//			stockKLineVO.setPchange(jsonObj.getDouble("pchange"));
//			stockKLineVO.setReason(jsonObj.getString("reason"));
//			stockKLineVO.setDate(jsonObj.getString("date"));
//			stockList.add(stockKLineVO);
//		}
//	return stockList;
		List<HotStockVO> stockVOs = new ArrayList<HotStockVO>();

		String date = TimeUtil.getLastworkDate();
		String url = "http://data.eastmoney.com/DataCenter_V3/stock2016/TradeDetail/pagesize=200,page=1,sortRule=-1,sortType=,startDate="+date+",endDate="+date+",gpfw=0,js=vardata_tab_1.html";
		String content = getContentFromURL(url);
		if (content==null){
			return stockVOs;
		}
		try {
//            System.out.println(content);
//            JSONObject jsonObject = new JSONObject(content.substring(content));

			JSONArray array = new JSONArray(content.substring(content.indexOf("["),content.indexOf("]")+1));

			List<HotStockVO> stockVOList = getStockVOsByJson(array);
			Collections.sort(stockVOList);
			stockVOs = stockVOList.subList(0,15);

			url="http://hq.sinajs.cn/list=";
			for (HotStockVO vo:stockVOs){
				String info = getContentFromURL(url+ StockUtil.getCode(vo.getCode()));
				try {
					vo.setCurrentPrice(info.split(",")[3]);
				}catch (Exception e){
					vo.setCurrentPrice("--");
					continue;
				}
			}

			return stockVOs;

		} catch (JSONException e) {
			e.printStackTrace();
			return stockVOs;
		}
	}

	public List<StockInfoByCom> getComStock(String url) throws Exception{
		List<StockInfoByCom> stockList=new ArrayList<StockInfoByCom>();
			URL ur=new URL(url);
			BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream(), "utf-8"));
			String line=reader.readLine();
			JSONArray jsonArray=new JSONArray(line);
			int size=jsonArray.length();
			for(int i=0;i<size;i++){
				StockInfoByCom stockKLineVO=new StockInfoByCom();
				JSONObject jsonObj=jsonArray.getJSONObject(i);
				stockKLineVO.setTime(jsonObj.getString("time"));
				stockKLineVO.setF_price(jsonObj.getDouble("f_price"));
				stockKLineVO.setPrice(jsonObj.getDouble("price"));
				stockKLineVO.setVolume(jsonObj.getDouble("volume"));
				stockKLineVO.setType(jsonObj.getInt("type"));
				stockList.add(stockKLineVO);
			}
		return stockList;
	}


	private List<HotStockVO> getStockVOsByJson(JSONArray array) throws JSONException {
		List<HotStockVO> result = new ArrayList<HotStockVO>();
		for (int i = 0 ; i < array.length() ;i++){

			JSONObject object = array.getJSONObject(i);
			HotStockVO vo = new HotStockVO(object.getString("SCode"),
					object.getString("SName"),object.getDouble("Chgradio"),
					object.getString("Ctypedes"),object.getString("Tdate"));

			if (result.contains(vo)){
				for(HotStockVO obj :result){
					if (obj.equals(vo)){
						obj.addReason(vo.getReason());
					}
				}
			}else{
				result.add(vo);
			}
		}

		return result;
	}

	private String getContentFromURL(String url) {

		try {
			URL ur = new URL(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(),"GBK"));
			String line = null;
			line = reader.readLine();
			return line;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
//	public static void main(String [] args){
//		String url="http://" + Connect.IP + "/DeepStock?codeNum=sh600000";
//		StockBasicInfoVO stockBasicInfoVO=new StockBasicInfoVO();
//		try {
//			URL ur=new URL(url);
//			BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
//			String line=reader.readLine();
//			JSONArray jsonArray=new JSONArray(line);
//			int size=jsonArray.length();
//			for(int i=0;i<size;i++){
//				JSONObject jsonObj=jsonArray.getJSONObject(i);
//				System.out.println(jsonObj.get("time"));
//			}
//			System.out.println(line);
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		GetStockDataServiceImpl A=new GetStockDataServiceImpl();
//		try {
//			System.out.println(A.getStockDepth("sh600000").get(0).getDeepPrice());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
