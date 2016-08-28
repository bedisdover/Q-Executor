package vo;

import java.util.ArrayList;

/**
 * Created by Liu on 2016/8/16.
 * 用来给VWAP的动态预测价格的数据类
 */


public class MLForVWAPPriceVO {

    private ArrayList<Double> priceList;// 动态预测下一天的所有价格（48个），已发生的返回真实价格，未发生的返回预测价格
    private int currentTime;           //当前处于的时间片

    public MLForVWAPPriceVO(ArrayList<Double> priceList,int currentTime){
               this.priceList=priceList;
              this.currentTime=currentTime;
    }

    public void setPriceList(ArrayList<Double> priceList) {
        this.priceList = priceList;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public ArrayList<Double> getPriceList() {
        return priceList;
    }

    public int getCurrentTime() {
        return currentTime;
    }
}
