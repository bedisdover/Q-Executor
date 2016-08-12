package cn.edu.nju.software.model;

/**
 * Created by Jiayiwu on 8/12/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StockInfoByPer {
    //时间
    String time;
    //价格
    double price;
    //价格变动
    double change_price;
    //成交量
    double turnover;
    //成交额
    double totalNum;
    //性质 True为买入 False为卖出
    boolean isBuy;

    public StockInfoByPer() {
    }

    public StockInfoByPer(String time, double price, double change_price, double turnover, double totalNum, boolean isBuy) {
        this.time = time;
        this.price = price;
        this.change_price = change_price;
        this.turnover = turnover;
        this.totalNum = totalNum;
        this.isBuy = isBuy;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange_price() {
        return change_price;
    }

    public void setChange_price(double change_price) {
        this.change_price = change_price;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(double totalNum) {
        this.totalNum = totalNum;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }
}
