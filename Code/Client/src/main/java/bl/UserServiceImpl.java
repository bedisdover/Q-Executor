package bl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import config.MsgInfo;
import blservice.UserService;

public class UserServiceImpl implements UserService{

	public MsgInfo register(String userName, String nickName, String password,
			String mail) throws Exception {
		String url="http://121.42.143.164/register?userName="+userName+"&nickName="+nickName+"&password="+password+"&mail="+mail;	      
		return getInfo(url);
	}

	public MsgInfo login(String userName, String password) throws Exception {
		String url="http://121.42.143.164/login?userName="+userName+"&password="+password;	      
		return getInfo(url);
	}

	public MsgInfo isLogin(String userName) throws Exception {
		String url="http://121.42.143.164/isLogin?userName="+userName;	      
		return getInfo(url);
	}

	public MsgInfo findPassword(String userName) throws Exception {
		String url="http://121.42.143.164/findPassword?userName="+userName;
		return getInfo(url);
	}
	
	public MsgInfo getInfo(String url) throws Exception {
		MsgInfo info = null;
		URL ur=new URL(url);
		BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
		String line=reader.readLine();
		JSONObject jsonArray=new JSONObject(line);
		info=new MsgInfo(jsonArray.getBoolean("state"),jsonArray.getString("info"),jsonArray.get("object"));
		return info;
	}



}
