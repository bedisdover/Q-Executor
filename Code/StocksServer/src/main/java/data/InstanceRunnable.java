package data;

import util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by 王栋 on 2016/8/4 0004.
 */
public class InstanceRunnable implements Runnable {
    private Connection connection;
    private String codes;
    private int index;

    public InstanceRunnable(Iterator<String> list , Connection connection,int index){
        codes = "";
        while (list.hasNext()){
            this.codes+=(list.next()+",");

        }
        System.out.println(codes);

        this.connection = connection;
        this.index = index;
    }
    public void run() {

        while (true){
            GetInstance getInstance = new GetInstance();
            getInstance.getInstanceBySina(codes,connection);

            try {
                Thread.sleep(10000);
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }


            connection = ConnectionUtil.getConnection(index);
            getInstance = null;
        }

    }
}
