package util;


import java.sql.*;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class TableUtil {


    public static boolean isExist(Connection conn,String tableName) throws SQLException {
        boolean result = false;
        String sql = "SHOW TABLES LIKE ?;";
        //Connection connection = ConnectionFactory.getInstance().makeConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,tableName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            result =  true;
        }

        JdbcUtil.close(statement);
        JdbcUtil.close(resultSet);
        return result;
    }

    public static void createTable(Connection connection,String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        JdbcUtil.close(statement);
    }
}
