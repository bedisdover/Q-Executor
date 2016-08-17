package cn.edu.nju.software.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by 王栋 on 2016/8/4 0004.
 */
public class JdbcUtil {

    private static final JdbcUtil jdbcUtil = new JdbcUtil();

    private static String driver;
    private static String dburl;
    private static String user;
    private static String password;

    static{
        Properties properties = new Properties();
        try {


            InputStream in = JdbcUtil.class.getClassLoader()
                    .getResourceAsStream("db.properties");
            properties.load(in);
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("=======配置文件读取错误=======");
        }

        driver = properties.getProperty("driver");
        dburl = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
    }
    //单例模式
    public static JdbcUtil getInstance(){
        return jdbcUtil;
    }

    public Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(driver);

            conn = DriverManager.getConnection(dburl,user,password);
            conn.setAutoCommit(true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
    public static void close(Statement statement) throws SQLException {
        if(statement!=null){
            statement.close();
        }
    }

    public static void close(ResultSet resultSet) throws SQLException {
        if(resultSet!=null){
            resultSet.close();
        }
    }

    public static void close(Connection connection) throws SQLException {
        if(connection!=null){
            connection.close();
        }
    }

}
