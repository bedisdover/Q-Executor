package data;


import po.StockInfo;
import util.ConnectionFactory;
import util.DateUtil;
import util.StringUtil;
import util.TableUtil;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class InitAllStocks {

    public static boolean init(Connection connection){


        try {
            if(!TableUtil.isExist(connection, StringUtil.STOCKS_INFO)){

                connection.setAutoCommit(false);
                String sql = "CREATE TABLE "+StringUtil.STOCKS_INFO+ "(code varchar(8) PRIMARY KEY ,name varchar(20),industry varchar(15),area VARCHAR(10),timeToMarket bigint);";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                initInfo(connection);
                return false;
            }else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //显示信息
            System.err.println("判断表是否存在失败，定位data.InitAllStocks.init()");
        }
        return false;
    }

    private static void initInfo(Connection connection) throws SQLException {

        InputStream inputStream = InitAllStocks.class.getClassLoader().getResourceAsStream("stocks.txt");
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String sql = "insert into "+StringUtil.STOCKS_INFO+" (code,name,industry,area,timeToMarket) values(?,?,?,?,?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        String line = null;
        try {
            while ((line=bufferedReader.readLine())!=null){
                StockInfo stockInfo = getStockInfo(line);
                statement.setString(1,stockInfo.getCode());
                statement.setString(2,stockInfo.getName());
                statement.setString(3,stockInfo.getIndustry());
                statement.setString(4,stockInfo.getArea());
                statement.setLong(5,stockInfo.getTimeToMarket());
                statement.executeUpdate();
            }
            connection.commit();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("读取A股的所有股票的基本信息失败了，看一下是代码问题还是对应的txt文件丢失了！！");
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("解析所有A股股票基本信息失败了！请看stocks.txt查看其格式是否为:" +
                    "code,name,industry,area,millis_time");
        }
    }

    private static StockInfo getStockInfo(String info){
        String[] infos = info.split(",");
        if (infos.length==5){
            String begin = "sz";
            if(infos[0].charAt(0)=='6'){
                begin = "sh";
            }
            Date date = DateUtil.getDate(infos[4]);
            StockInfo stockInfo = new StockInfo(begin+infos[0],infos[1],infos[2],infos[3],date.getTime());
            return stockInfo;
        }else {
            throw new RuntimeException("stocks.txt某些行格式有问题");
        }
    }

    public static int getStockCounts(Connection connection){
        int counts = 0;
        try {

            String sql = "select count(*) from " + StringUtil.STOCKS_INFO;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                counts = resultSet.getInt(1);
            }

            ConnectionFactory.getInstance().close(statement,resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("读取一共有多少支股票竟然有问题天啦噜");
            return counts;
        }
        System.out.print("一共有" + counts + "支股票");
        return counts;
    }

    //一开始想着用迭代器模式 后来一看如果是多线程我就是真的很可笑了
    public static List<String> getStockCodes(Connection connection){
        ArrayList<String> list = new ArrayList<String>();
        try {
        String sql = "select code from " +StringUtil.STOCKS_INFO;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            String code = resultSet.getString(1);
            //在这里加上了code的前缀 sh sz
            if(code.length()==6){
                if(code.charAt(0)=='6'){
                    code = "sh"+code;
                }else {
                    code = "sz"+code;
                }
            }
            list.add(code);
        }
        ConnectionFactory.getInstance().close(statement,resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("读取出所有A股的股票代码的过程中竟然tmd出错了");
            return list;
        }
        return list;

    }

}
