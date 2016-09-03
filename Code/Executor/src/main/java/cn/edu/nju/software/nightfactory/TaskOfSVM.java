package cn.edu.nju.software.nightfactory;

import cn.edu.nju.software.service.MLForVWAPService;
import cn.edu.nju.software.service.MLForVWAPServiceImpl;
import cn.edu.nju.software.service.StockMLService;
import cn.edu.nju.software.service.StockMLServiceImpl;

import java.util.TimerTask;

/**
 * Created by admin on 2016/9/2.
 */
public class TaskOfSVM extends TimerTask {

    private MLForVWAPService mlService;
    private StockMLService stockService;
    private static TaskOfSVM single;

    public static TaskOfSVM getInstance(){
        if (single==null) {
            single=new TaskOfSVM();
        }
        return single;
    }

    private TaskOfSVM(){
        mlService= new MLForVWAPServiceImpl();
        stockService=new StockMLServiceImpl();
    }


    @Override
    public void run() {
        try {

            String[] all_stock=stockService .getStocksNeedCal();
            for(int i=0;i<all_stock.length;i++){
                mlService.getStaticVol_svm(all_stock[i]);
//                this.getStaticPrice_svm(all_stock[i]);
//                this.getDynamicPrice_svm(all_stock[i]);
            }
        } catch (Exception e) {
            System.out.println("night factory error!");
        }

    }
}
