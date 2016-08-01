package data;

import java.util.Iterator;

/**
 * Created by 王栋 on 2016/8/1 0001.
 */
public class InstanceRunnable implements Runnable {
    private String codes;
    public InstanceRunnable(Iterator<String> codes){
        this.codes = GetInstance.getString(codes);
    }


    public void run() {
            while (true){

            GetInstance.getInstanceBySina(codes);

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


}
