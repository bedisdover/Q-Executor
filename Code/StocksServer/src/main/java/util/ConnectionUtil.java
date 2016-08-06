package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王栋 on 2016/8/4 0004.
 */
public class ConnectionUtil {

    private static List<Connection> connections;

    static {
        connections = new ArrayList<Connection>();
    }


    public static void makeConnections(int num){
        for(int i = 0 ; i < num ; i++){
            connections.add(JdbcUtil.getInstance().getConnection());
        }
    }



    public static Connection getConnection(int i){
       try {
           if(connections.get(i)!=null){
               if(connections.get(i).isClosed()){
                   connections.set(i,JdbcUtil.getInstance().getConnection());
                   return connections.get(i);
               }
               return connections.get(i);
           }else {
               connections.add(i,JdbcUtil.getInstance().getConnection());
               return connections.get(i);
           }
       }catch (Exception e){
           e.printStackTrace();
           connections.add(i,JdbcUtil.getInstance().getConnection());
           return connections.get(i);
       }
    }

    public static void closeAll() throws SQLException {
        for(Connection connection : connections){
            JdbcUtil.close(connection);
        }
    }

}
