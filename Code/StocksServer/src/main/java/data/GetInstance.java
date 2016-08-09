package data;

import po.StockInstance;
import util.DateUtil;
import util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by 王栋 on 2016/8/4 0004.
 */
public class GetInstance {

    private static final String urlStr = "http://hq.sinajs.cn/list=";
    private static final int INSTANCE_TABLE_COUNTS = 25;


    public void getInstanceBySina(String code,Connection connection)  {

        URL ur = null;


        try {
            //已经是setAutoCommit为false
            ur = new URL(urlStr+code);
            HttpURLConnection uc = (HttpURLConnection) ur.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(), "GBK"));
            String msg = null;

            while((msg = reader.readLine())!=null){

                String stockCode = msg.split("=")[0];
                stockCode = stockCode.substring(stockCode.length()-8,stockCode.length());


                int start = msg.indexOf("\"");
                int end = msg.lastIndexOf("\"");
                String info = msg.substring(start+1,end);
                StockInstance stockInstance = getStockInstance(info,stockCode);
                StroedInstanceInDB.stored(getTableName(stockCode),stockInstance,connection);
                //查看每次从sina API获取的内容--------
//                System.out.println(msg);
                //----------------------------------

            }
            //--------是不是这两个没关掉导致内存占用比较大？？
            reader.close();
            uc.disconnect();
            //_____________________________________
            connection.commit();
//            System.out.println("调用了一次commit，当前线程为"+Thread.currentThread().getName());
//            System.err.println("当前时间为"+ new java.util.Date());

        }catch (IOException e) {
            e.printStackTrace();
            System.err.println("网络问题或者获取的数据格式出现了问题");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("存到数据库里面出现问题回滚提交");
            try {
                connection.commit();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

    }

    public String getTableName(String code){
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

    public StockInstance getStockInstance(String info,String stockCode){
//        System.out.println(info);
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
