package data;

import main.Main;
import util.DateUtil;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Created by 王栋 on 2016/8/1 0001.
 */
public class InstanceRunnable implements Runnable {
    private String codes;
    private Connection connection;
    public InstanceRunnable(Iterator<String> codes,Connection connection){
        this.codes = GetInstance.getString(codes);
        this.connection = connection;
    }


    public void run() {
            while (true){
                    GetInstance.getInstanceBySina(codes,connection);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


}
