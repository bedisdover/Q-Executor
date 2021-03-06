package bl.user;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import bl.Connect;
import org.json.JSONObject;

import config.MsgInfo;
import blservice.user.UserService;

public class UserServiceImpl implements UserService{

	public MsgInfo register(String userName, String nickName, String password,
			String mail) throws Exception {
		String url="http://" + Connect.IP + "/register?userName="+userName+"&nickName="+nickName+"&password="+password+"&mail="+mail;
		return getInfo(url);
	}

	public MsgInfo login(String userName, String password) throws Exception {
		String url="http://" + Connect.IP + "/login?userName="+userName+"&password="+password;
		return getInfo(url);
	}

	public MsgInfo isLogin(String userName) throws Exception {
		String url="http://" + Connect.IP + "/isLogin?userName="+userName;
		return getInfo(url);
	}

	public MsgInfo findPassword(String userName) throws Exception {
		String url="http://" + Connect.IP + "/findPassword?userName="+userName;
		return getInfo(url);
	}
	
	public MsgInfo getInfo(String url) throws Exception {
		MsgInfo info = null;
		URL ur=new URL(url);
		BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream(), "utf-8"));
		String line=reader.readLine();
		JSONObject jsonArray=new JSONObject(line);
		info=new MsgInfo(jsonArray.getBoolean("state"),jsonArray.getString("info"),jsonArray.get("object"));
		return info;
	}



}
