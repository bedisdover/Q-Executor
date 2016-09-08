package bl.user;

import bl.Connect;
import blservice.user.SelfSelectService;
import config.MsgInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import vo.NowTimeSelectedStockInfoVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SelfSelectServiceImpl implements SelfSelectService{

	public List<NowTimeSelectedStockInfoVO> getUserSelectedStock(String userName,String password) throws Exception {
		String url="http://" + Connect.IP + "/getUserSelectedStockClient?userName="+userName+"&password="+password;
		URL ur=new URL(url);
		BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(), "utf-8"));
		String line=reader.readLine();
		JSONObject json=new JSONObject(line);
		return getJsonArray(json);
		
	}

	public MsgInfo addUserSelectedStock(String codeNum,String userName,String password) throws Exception {
        String url="http://" + Connect.IP + "/addUserSelectedStockClient?codeNum="+codeNum+"&userName="+userName+"&password="+password;
        MsgInfo info = null;
		URL ur=new URL(url);
		BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
		String line=reader.readLine();
		JSONObject jsonArray=new JSONObject(line);
		info=new MsgInfo(jsonArray.getBoolean("state"),jsonArray.getString("info"),getJsonArray(jsonArray));
		return info;
	}

	public MsgInfo deleteUserSelectedStock(String codeNum,String userName,String password) throws Exception {
		String url="http://" + Connect.IP + "/deleteUserSelectedStockClient?codeNum="+codeNum+"&userName="+userName+"&password="+password;
        MsgInfo info = null;
		URL ur=new URL(url);
		BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
		String line=reader.readLine();
		JSONObject jsonArray=new JSONObject(line);
		info=new MsgInfo(jsonArray.getBoolean("state"),jsonArray.getString("info"),getJsonArray(jsonArray));
		return info;
	}
	public List<NowTimeSelectedStockInfoVO> getJsonArray(JSONObject json) throws Exception{
		List<NowTimeSelectedStockInfoVO> stock =new ArrayList<NowTimeSelectedStockInfoVO>();
		JSONArray jsonArray=json.getJSONArray("object");

		if(null == jsonArray){
			return  stock;
		}

		int size=jsonArray.length();
		for(int i=0;i<size;i++){
			NowTimeSelectedStockInfoVO vo=new NowTimeSelectedStockInfoVO();
			if(jsonArray.get(i).equals(null)){
				break;
			}
			JSONObject jsonObj=jsonArray.getJSONObject(i);
			vo.setGid(jsonObj.getString("gid"));
			vo.setIncrePer(jsonObj.getDouble("increPer"));
			vo.setIncrease(jsonObj.getDouble("increase"));
			vo.setName(jsonObj.getString("name"));
			vo.setYestodEndPri(jsonObj.getDouble("yestodEndPri"));
			vo.setTodayStartPri(jsonObj.getDouble("todayStartPri"));
			vo.setNowPri(jsonObj.getDouble("nowPri"));
			vo.setTodayMax(jsonObj.getDouble("todayMax"));
			vo.setTodayMin(jsonObj.getDouble("todayMin"));
			vo.setDate(jsonObj.getString("date"));
			vo.setTime(jsonObj.getString("time"));
			vo.setTraNumber(jsonObj.getDouble("traNumber"));
			vo.setTraAmount(jsonObj.getDouble("traAmount"));
			stock.add(vo);		
		}
		return stock;
	}
	
}
