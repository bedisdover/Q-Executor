package data;

import util.ConnectionFactory;

import java.sql.Connection;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class test {

    public static void main(String[] args){

        Connection connection = ConnectionFactory.getInstance().makeConnection();
        InitAllStocks.init(connection);
        InitAllStocks.getStockCounts(connection);
    }
}
