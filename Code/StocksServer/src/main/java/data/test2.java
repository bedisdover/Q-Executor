package data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 王栋 on 2016/8/1 0001.
 */
public class test2 {
    public static void main(String[] args) throws Exception{
//        URL ur = new URL("http://hq.sinajs.cn/list=sh600000");
//        HttpURLConnection uc = (HttpURLConnection) ur.openConnection();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(), "GBK"));
//        String msg = null;
//        StringBuilder result = new StringBuilder();
//        while ((msg=reader.readLine())!=null){
//            System.out.println(msg);
//        }
        GetInstance.getInstanceBySina("sh600000");
    }
}
