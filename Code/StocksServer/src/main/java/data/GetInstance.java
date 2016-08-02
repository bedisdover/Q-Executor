package data;

import po.StockInstance;
import util.ConnectionFactory;
import util.DateUtil;
import util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 王栋 on 2016/8/1 0001.
 */
public class GetInstance {

    private static final String urlStr = "http://hq.sinajs.cn/list=";
    private static final int INSTANCE_TABLE_COUNTS = 25;
    public static synchronized String getString(Iterator<String> codes){
        StringBuilder stringBuilder = new StringBuilder();
        while (codes.hasNext()){
            stringBuilder.append(codes.next()+",");
        }
        return stringBuilder.toString();
    }
    public static String getInstanceBySina(String code)  {
        URL ur = null;
        Connection connection = ConnectionFactory.getInstance().makeConnection();

        try {
            //已经是setAutoCommit为false
            ur = new URL(urlStr+code);
            HttpURLConnection uc = (HttpURLConnection) ur.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(), "GBK"));
            String msg = null;
            StringBuilder result = new StringBuilder();
            while((msg = reader.readLine())!=null){

                String stockCode = msg.split("=")[0];
                stockCode = stockCode.substring(stockCode.length()-8,stockCode.length());

                result.append(msg+"\n");
                int start = msg.indexOf("\"");
                int end = msg.lastIndexOf("\"");
                String info = msg.substring(start+1,end);
                StockInstance stockInstance = getStockInstance(info,stockCode);
                StroedInstanceInDB.stored(getTableName(stockCode),stockInstance,connection);
                //查看每次从sina API获取的内容--------
                System.out.println(msg);
                //----------------------------------
            }
            connection.commit();
            return result.toString();
        }catch (IOException e) {
            e.printStackTrace();
            System.err.println("网络问题或者获取的数据格式出现了问题");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("存到数据库里面出现问题回滚提交");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }

    public static String getTableName(String code){
        if(code.length()==8){
            code = code.substring(2);
        }
        String result  =null;
        try {
            result = StringUtil.STOCKS_INSTANCE+Integer.parseInt(code)%INSTANCE_TABLE_COUNTS;
        }catch (Exception e){
            result = StringUtil.STOCKS_INSTANCE+"0";
        }

        return result;
    }

    public static StockInstance getStockInstance(String info,String stockCode){
        System.out.println(info);
        if(info==null||info.equals("")){
            return null;
        }
        String[] infos = info.split(",");


        if(infos.length>=32){
            StockInstance stockInstance = new StockInstance(
                 Float.parseFloat(infos[1]),Float.parseFloat(infos[2]),
                 Float.parseFloat(infos[3]),Float.parseFloat(infos[4]),
                 Float.parseFloat(infos[5]),Long.parseLong(infos[8]),
                 Float.parseFloat(infos[9]),Integer.parseInt(infos[10]),
                 Float.parseFloat(infos[11]),Integer.parseInt(infos[12]),
                 Float.parseFloat(infos[13]),Integer.parseInt(infos[14]),
                 Float.parseFloat(infos[15]),Integer.parseInt(infos[16]),
                    Float.parseFloat(infos[17]),Integer.parseInt(infos[18]),
                    Float.parseFloat(infos[19]),Integer.parseInt(infos[20]),
                    Float.parseFloat(infos[21]),Integer.parseInt(infos[22]),
                    Float.parseFloat(infos[23]),Integer.parseInt(infos[24]),
                    Float.parseFloat(infos[25]),Integer.parseInt(infos[26]),
                    Float.parseFloat(infos[27]),Integer.parseInt(infos[28]),
                    Float.parseFloat(infos[29]), DateUtil.getDateByDetail(infos[30]+" "+infos[31]).getTime(),
                    stockCode

            );
            return stockInstance;
        }

        return null;
    }
}
