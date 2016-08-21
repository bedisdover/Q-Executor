package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.BaseDao;
import cn.edu.nju.software.po.StockForMLPO;
import cn.edu.nju.software.utils.JdbcUtil;
import cn.edu.nju.software.utils.StockUtil;
import cn.edu.nju.software.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by 王栋 on 2016/8/17 0017.
 *
 * question:由于hibernate提供的查询sql接口同样的查询sql报错
 * 因此迫不得已 使用了jdbc
 */
@Service
public class StockMLServiceImpl implements StockMLService{
    private static String[] timeFormat = {"09:35:00","09:40:00","09:45:00","09:50:00","09:55:00","10:00:00",
    "10:05:00","10:10:00","10:15:00","10:20:00","10:25:00","10:30:00","10:35:00","10:40:00","10:45:00","10:50:00","10:55:00","11:00:00",
    "11:05:00","11:10:00","11:15:00","11:20:00","11:25:00","11:30:00","13:05:00","13:10:00","13:15:00","13:20:00","13:25:00","13:30:00",
    "13:35:00","13:40:00","13:45:00","13:50:00","13:55:00","14:00:00","14:05:00","14:10:00","14:15:00","14:20:00","14:25:00","14:30:00",
    "14:35:00","14:40:00","14:45:00","14:50:00","14:55:00","15:00:00"};
    @Resource
    BaseDao baseDao;
    @Override
    public ArrayList<StockForMLPO> getStockDataML(String stockID, int numOfStock, int currentTime) {
        stockID =  StockUtil.getCode(stockID);
        int num = Integer.parseInt(stockID.substring(2))%20;
        ArrayList<StockForMLPO> stockForMLPOs = new ArrayList<StockForMLPO>();
        String sql = "SELECT open,high,low,close,volume,dealMoney FROM "+ StringUtil.HISTORY_5MIN_DATA+num+" WHERE CODE = \""+stockID+"\" AND DATE_FORMAT(DATE,\"%H:%i:%s\") = \""+timeFormat[currentTime-1]+"\" ORDER BY DATE DESC limit 0,"+numOfStock;
//        List<Object[]> objectsList = baseDao.execSqlQuery(sql);
//        for(Object[] objects:objectsList){
//            double volume = Double.parseDouble(objects[4].toString());
//            double dealMoney = Double.parseDouble(objects[5].toString());
//            double avg = 0.0;
//            if (volume!=0){
//                avg = round(dealMoney/volume,2,BigDecimal.ROUND_HALF_UP);
//            }
        try {
            Connection connection = JdbcUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                double volume = resultSet.getDouble(5);
                double dealMoney = resultSet.getDouble(6);
                double avg = 0.0;
                if (volume!=0){
                avg = round(dealMoney/volume,2,BigDecimal.ROUND_HALF_UP);
                }
                stockForMLPOs.add(new StockForMLPO(avg,resultSet.getDouble(1),
                        resultSet.getDouble(4),resultSet.getDouble(2),
                        resultSet.getDouble(3), volume));
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
}
