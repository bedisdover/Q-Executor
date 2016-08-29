package bl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import vo.StockInfoByPrice;

import blservice.SelfSelectService;
import config.MsgInfo;

public class SelfSelectServiceImpl implements SelfSelectService{

	public List<String> getUserSelectedStock() throws Exception {
		String url="http://121.42.143.164/getUserSelectedStock";	
		URL ur=new URL(url);
		BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
		String line=reader.readLine();
		JSONObject json=new JSONObject(line);
		List<String> stock =new ArrayList<String>();
		Object obj=json.get("object");
		JSONArray jsonArray=new JSONArray(obj);
		int size=jsonArray.length();
		for(int i=0;i<size;i++){
			JSONObject jsonObj=jsonArray.getJSONObject(i);
			stock.add(jsonObj.getString("gid"));
		}
		return stock;
	}

	public MsgInfo addUserSelectedStock(String codeNum) throws Exception {
        String url="http://121.42.143.164/addUserSelectedStock?codeNum="+codeNum;	
        MsgInfo info = null;
		URL ur=new URL(url);
		BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
		String line=reader.readLine();
		JSONObject jsonArray=new JSONObject(line);
		info=new MsgInfo(jsonArray.getBoolean("state"),jsonArray.getString("info"),jsonArray.get("object"));
		return info;
	}

	public MsgInfo deleteUserSelectedStock(String codeNum) throws Exception {
		String url="http://121.42.143.164/deleteUserSelectedStock?codeNum="+codeNum;	
        MsgInfo info = null;
		URL ur=new URL(url);
		BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
		String line=reader.readLine();
		JSONObject jsonArray=new JSONObject(line);
		info=new MsgInfo(jsonArray.getBoolean("state"),jsonArray.getString("info"),jsonArray.get("object"));
		return info;
	}
}
