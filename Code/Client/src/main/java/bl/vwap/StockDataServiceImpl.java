package bl.vwap;

import blservice.vwap.StockDataService;
import util.JdbcUtil;
import util.StockUtil;
import util.StringUtil;
import util.TimeUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 王栋 on 2016/11/17 0017.
 */
public class StockDataServiceImpl implements StockDataService{
    @Override
    public List<Long> getVolList(String stockid, Date date) {
        stockid =  StockUtil.getCode(stockid);
        int num = Integer.parseInt(stockid.substring(2))%20;
        List<Long> volumes = new ArrayList<>();
        String sql = "SELECT volume FROM "+ StringUtil.HISTORY_5MIN_DATA+num+" WHERE CODE = \""+stockid+"\" AND date>=? and date <=? ORDER BY DATE ";
        Timestamp start = Timestamp.valueOf(TimeUtil.getDate(date)+" 00:00:00");
        Timestamp end = Timestamp.valueOf(TimeUtil.getDate(date)+" 16:00:00");

        try {
            Connection connection = JdbcUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1,start);
            statement.setTimestamp(2,end);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long vol = (long)resultSet.getDouble(1);
                volumes.add(vol);
            }

        }catch (SQLException e){
            e.printStackTrace();
            return volumes;
        }
        return volumes;
    }

    @Override
    public List<Double> getPriceList(String stockid, Date date) {
        stockid =  StockUtil.getCode(stockid);
        int num = Integer.parseInt(stockid.substring(2))%20;
        List<Double> prices = new ArrayList<>();
        String sql = "SELECT volume,dealMoney FROM "+ StringUtil.HISTORY_5MIN_DATA+num+" WHERE CODE = \""+stockid+"\" AND date>=? and date <=? ORDER BY DATE";
        Timestamp start = Timestamp.valueOf(TimeUtil.getDate(date)+" 00:00:00");
        Timestamp end = Timestamp.valueOf(TimeUtil.getDate(date)+" 16:00:00");

        try {
            Connection connection = JdbcUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1,start);
            statement.setTimestamp(2,end);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                double volume = resultSet.getDouble(1);
                double dealMoney = resultSet.getDouble(2);
                double avg = 0.0;
                if (volume!=0){
                    avg = round(dealMoney/volume,2, BigDecimal.ROUND_HALF_UP);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            return prices;
        }
        //按照第一个到最后一个顺序
        return prices;
    }



    private static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }
}
