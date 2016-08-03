package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class ConnectionFactory {

    private static final ConnectionFactory factory = new ConnectionFactory();
    public static List<Connection> connections = new ArrayList<Connection>();
    private  Connection conn;

    private static String driver;
    private static String dburl;
    private static String user;
    private static String password;

    static{
        Properties properties = new Properties();
        try {


            InputStream in = ConnectionFactory.class.getClassLoader()
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

    private ConnectionFactory(){

    }

    //单例模式
    public static ConnectionFactory getInstance(){
        return factory;
    }

    public Connection makeConnection(){

        try {
            Class.forName(driver);

            conn = DriverManager.getConnection(dburl,user,password);
            conn.setAutoCommit(false);
            connections.add(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
    public void makeConnections(int num){
        for(int i = 0 ; i < num ;i++){
            makeConnection();
        }
    }

    public void close(Connection conn, Statement statement) throws SQLException {
        if (conn!=null){
            conn.close();
        }
        if(statement!=null){
            statement.close();
        }
    }

    public void close(Connection conn) throws SQLException {
        if (conn!=null){
            conn.close();
        }
    }
    public void close(Connection conn, Statement statement, ResultSet resultSet) throws SQLException {

        if (conn!=null){
            conn.close();
        }
        if(statement!=null){
            statement.close();
        }
        if (resultSet!=null){
            resultSet.close();
        }
    }

    public void close(Statement statement,ResultSet resultSet) throws SQLException {
        if(statement!=null){
        statement.close();
        }
        if (resultSet!=null){
        resultSet.close();
        }
    }

    public static void close() throws SQLException{
        System.out.println("共有"+connections.size()+"个connection");
        for (Connection conn : connections){
            conn.commit();
            conn.close();
        }
    }
}
