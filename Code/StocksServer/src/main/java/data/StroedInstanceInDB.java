package data;

import po.StockInstance;
import util.ConnectionFactory;
import util.TableUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 王栋 on 2016/8/1 0001.
 */
public class StroedInstanceInDB {

    public static void stored(String code, StockInstance stockInstance, Connection connection){
        if(stockInstance==null){
            return;
        }
        //将读取到的实时数据存储到数据库
        String sql = null;
        try {
            connection.setAutoCommit(false);
            if(!TableUtil.isExist(connection,code)){
                //建表
                sql = "create table " + code + " (open float,close float,current float," +
                        "todayMax float,todayMin float,dealNumber bigint,dealMoney FLOAT ," +
                        "buy1Number int,buy1 float,buy2Number int,buy2 float," +
                        "buy3Number int,buy3 float,buy4Number int,buy4 float," +
                        "buy5Number int,buy5 float,sell1Number int,sell1 float," +
                        "sell2Number int,sell2 float,sell3Number int,sell3 float," +
                        "sell4Number int,sell4 float,sell5Number int,sell5 float," +
                        "time bigint ,code varchar(8),primary key(time,code));";
                TableUtil.createTable(connection,sql);
            }
            sql = "insert into "+code+" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            insertDetails(statement,stockInstance);
            statement.executeUpdate();


        } catch (SQLException e) {
//            e.printStackTrace();
            System.err.println("错误定位:StroedInstanceInDB.stored 一般是数据重复不能添加");
        }
    }

    private static void insertDetails(PreparedStatement statement,StockInstance stockInstance) throws SQLException {
        statement.setFloat(1,stockInstance.getOpen());
        statement.setFloat(2,stockInstance.getClose());
        statement.setFloat(3,stockInstance.getCurrent());
        statement.setFloat(4,stockInstance.getTodayMax());
        statement.setFloat(5,stockInstance.getTodayMin());
        statement.setLong(6,stockInstance.getDealNumber());
        statement.setFloat(7,stockInstance.getDealMoney());
        statement.setInt(8,stockInstance.getBuy1Number());
        statement.setFloat(9,stockInstance.getBuy1());
        statement.setInt(10,stockInstance.getBuy2Number());
        statement.setFloat(11,stockInstance.getBuy2());
        statement.setInt(12,stockInstance.getBuy3Number());
        statement.setFloat(13,stockInstance.getBuy3());
        statement.setInt(14,stockInstance.getBuy4Number());
        statement.setFloat(15,stockInstance.getBuy4());
        statement.setInt(16,stockInstance.getBuy5Number());
        statement.setFloat(17,stockInstance.getBuy5());

        statement.setInt(18,stockInstance.getSell1Number());
        statement.setFloat(19,stockInstance.getSell1());
        statement.setInt(20,stockInstance.getSell2Number());
        statement.setFloat(21,stockInstance.getSell2());
        statement.setInt(22,stockInstance.getSell3Number());
        statement.setFloat(23,stockInstance.getSell3());
        statement.setInt(24,stockInstance.getSell4Number());
        statement.setFloat(25,stockInstance.getSell4());
        statement.setInt(26,stockInstance.getSell5Number());
        statement.setFloat(27,stockInstance.getSell5());

        statement.setLong(28,stockInstance.getTime());
        statement.setString(29,stockInstance.getCode());
    }
}
