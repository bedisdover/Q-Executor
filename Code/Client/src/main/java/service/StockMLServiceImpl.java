package service;


import po.InforForMLPO;
import po.StockForMLPO;
import util.JdbcUtil;
import util.StockUtil;
import util.StringUtil;
import util.TimeUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by 王栋 on 2016/8/17 0017.
 *
 * question:由于hibernate提供的查询sql接口同样的查询sql报错
 * 因此迫不得已 使用了jdbc
 */

public class StockMLServiceImpl implements StockMLService{
    private static String[] timeFormat = {"09:35:00","09:40:00","09:45:00","09:50:00","09:55:00","10:00:00",
    "10:05:00","10:10:00","10:15:00","10:20:00","10:25:00","10:30:00","10:35:00","10:40:00","10:45:00","10:50:00","10:55:00","11:00:00",
    "11:05:00","11:10:00","11:15:00","11:20:00","11:25:00","11:30:00","13:05:00","13:10:00","13:15:00","13:20:00","13:25:00","13:30:00",
    "13:35:00","13:40:00","13:45:00","13:50:00","13:55:00","14:00:00","14:05:00","14:10:00","14:15:00","14:20:00","14:25:00","14:30:00",
    "14:35:00","14:40:00","14:45:00","14:50:00","14:55:00","15:00:00"};

    @Override
    public ArrayList<StockForMLPO> getStockDataML(String stockID, int numOfStock, int currentTime) {
        stockID =  StockUtil.getCode(stockID);
        int num = Integer.parseInt(stockID.substring(2))%20;
        ArrayList<StockForMLPO> stockForMLPOs = new ArrayList<StockForMLPO>();
        String sql = "SELECT open,high,low,close,volume,dealMoney FROM "+ StringUtil.HISTORY_5MIN_DATA+num+" WHERE CODE = \""+stockID+"\" AND DATE_FORMAT(DATE,\"%H:%i:%s\") = \""+timeFormat[currentTime-1]+"\" ORDER BY DATE DESC limit "+numOfStock;

        try {
            Connection connection = JdbcUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                stockForMLPOs.add(getStockForMLPO(resultSet));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return stockForMLPOs;
        }
        Collections.reverse(stockForMLPOs);
        return stockForMLPOs;

    }

    private static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }


    /*
    新增的两个接口
    @auther：LiuXing
     */

    /**
     * 获取该只股票的当日的五分钟的信息 如果没有就算了 返回一个size为0的list 如果有 有多少给多少
     * @param stockID 股票代码
     * @return 该只股票的当日截止到目前为止所有的五分钟线
     */
    public ArrayList<StockForMLPO> getTodayInforML(String stockID){
        ArrayList<StockForMLPO> array=new ArrayList<StockForMLPO>();
        stockID =  StockUtil.getCode(stockID);
        int num = Integer.parseInt(stockID.substring(2))%20;
        String today = TimeUtil.getDate(new Date());

        String sql = "SELECT open,high,low,close,volume,dealMoney FROM "+ StringUtil.HISTORY_5MIN_DATA+num+" WHERE CODE = \""+stockID+"\" AND DATE >= ?;";
        try {
            Connection connection = JdbcUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1,Timestamp.valueOf(today+" 00:00:00"));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                array.add(getStockForMLPO(resultSet));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return array;
        }
        return array;
    }

    /**
     * 保证 currentTime >= 4 ??
     * @param stockID
     * @param numOfStock
     * @param currentTime
     * @return
     */
    public ArrayList<InforForMLPO> getDynamicInforML(String stockID, int numOfStock, int currentTime){
        ArrayList<InforForMLPO> array=new ArrayList<InforForMLPO>();
        ArrayList<StockForMLPO> currentTimes = getStockDataML(stockID,numOfStock+3,currentTime);
        ArrayList<StockForMLPO> currentTimeLast1 = getStockDataML(stockID,numOfStock,currentTime-1);
        ArrayList<StockForMLPO> currentTimeLast2 = getStockDataML(stockID,numOfStock,currentTime-2);
        ArrayList<StockForMLPO> currentTimeLast3 = getStockDataML(stockID,numOfStock,currentTime-3);
        int currentTimesNums = currentTimes.size();
        int currentTimeLast1Nums = currentTimeLast1.size();
        int currentTimeLast2Nums = currentTimeLast2.size();
        int currentTimeLast3Nums = currentTimeLast3.size();
        try {

            for (int i = 1; i <= currentTimesNums - 3; i++) {
                InforForMLPO po = new InforForMLPO(currentTimes.get(currentTimesNums - i - 1), currentTimes.get(currentTimesNums - i - 2), currentTimes.get(currentTimesNums - i - 3),
                        currentTimeLast1.get(currentTimeLast1Nums - i), currentTimeLast2.get(currentTimeLast2Nums - i), currentTimeLast3.get(currentTimeLast3Nums - i), currentTimes.get(currentTimesNums - i));
                array.add(po);
            }
        }catch(Exception e){
            e.printStackTrace();
            Collections.reverse(array);
            return array;
        }
        Collections.reverse(array);
        return array;
    }




    /**********************************************实证部分***********************************************************/
    @Override
    public ArrayList<StockForMLPO> getStockDataMLTest(String stockID, int currentTime, Date end) {
        ArrayList<StockForMLPO> stockForMLPOs=new ArrayList<StockForMLPO>();
        stockID =  StockUtil.getCode(stockID);
        int num = Integer.parseInt(stockID.substring(2))%20;

        String sql = "SELECT open,high,low,close,volume,dealMoney FROM "+ StringUtil.HISTORY_5MIN_DATA+num+" WHERE CODE = \""+stockID+"\" AND DATE_FORMAT(DATE,\"%H:%i:%s\") = \""+timeFormat[currentTime-1]+"\" AND DATE<=? ORDER BY DATE DESC ";

        try {
            Connection connection = JdbcUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1,Timestamp.valueOf(TimeUtil.getDate(end)+" 00:00:00"));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                stockForMLPOs.add(getStockForMLPO(resultSet));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return stockForMLPOs;
        }
        Collections.reverse(stockForMLPOs);
        return stockForMLPOs;
    }

    @Override
    public ArrayList<InforForMLPO> getDynamicInforMLTest(String stockID, int index, Date end) {
        ArrayList<InforForMLPO> array=new ArrayList<InforForMLPO>();
        ArrayList<StockForMLPO> currentTimes = getStockDataMLTest(stockID,index,end);
        ArrayList<StockForMLPO> currentTimeLast1 = getStockDataMLTest(stockID,index-1,end);
        ArrayList<StockForMLPO> currentTimeLast2 = getStockDataMLTest(stockID,index-2,end);
        ArrayList<StockForMLPO> currentTimeLast3 = getStockDataMLTest(stockID,index-3,end);
        int currentTimesNums = currentTimes.size();
        int currentTimeLast1Nums = currentTimeLast1.size();
        int currentTimeLast2Nums = currentTimeLast2.size();
        int currentTimeLast3Nums = currentTimeLast3.size();
        try {

            for (int i = 1; i <= currentTimesNums - 3; i++) {
                InforForMLPO po = new InforForMLPO(currentTimes.get(currentTimesNums - i - 1), currentTimes.get(currentTimesNums - i - 2), currentTimes.get(currentTimesNums - i - 3),
                        currentTimeLast1.get(currentTimeLast1Nums - i), currentTimeLast2.get(currentTimeLast2Nums - i), currentTimeLast3.get(currentTimeLast3Nums - i), currentTimes.get(currentTimesNums - i));
                array.add(po);
            }
        }catch(Exception e){
            e.printStackTrace();
            Collections.reverse(array);
            return array;
        }
        Collections.reverse(array);
        return array;
    }

    @Override
    public ArrayList<StockForMLPO> getTodayInforMLTest(String stockID, Date end, int index) {
        ArrayList<StockForMLPO> array=new ArrayList<StockForMLPO>();
        stockID =  StockUtil.getCode(stockID);
        int num = Integer.parseInt(stockID.substring(2))%20;


        String sql = "SELECT open,high,low,close,volume,dealMoney FROM "+ StringUtil.HISTORY_5MIN_DATA+num+" WHERE CODE = \""+stockID+"\" AND DATE >= ? and DATE <= ?;";
        try {
            Connection connection = JdbcUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1,Timestamp.valueOf(TimeUtil.getDate(end)+" 00:00:00"));
            statement.setTimestamp(2,Timestamp.valueOf(TimeUtil.getDate(end)+" "+timeFormat[index-1]));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                array.add(getStockForMLPO(resultSet));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return array;
        }
        return array;
    }

    private StockForMLPO getStockForMLPO(ResultSet resultSet) throws SQLException {
        double volume = resultSet.getDouble(5);
        double dealMoney = resultSet.getDouble(6);
        double avg = 0.0;
        if (volume!=0){
            avg = round(dealMoney/volume,2,BigDecimal.ROUND_HALF_UP);
        }
        StockForMLPO stockForMLPO = new StockForMLPO(avg,resultSet.getDouble(1),
                resultSet.getDouble(4),resultSet.getDouble(2),
                resultSet.getDouble(3), volume);
        return  stockForMLPO;
    }



}
