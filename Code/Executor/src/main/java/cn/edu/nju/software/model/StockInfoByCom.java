package cn.edu.nju.software.model;

/**
 * Created by Jiayiwu on 8/12/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StockInfoByCom {
    //时间
    String time;
    //价格
    double price;
    //交易量
    double trunover;
    //前一笔交易价格
    double f_price;
    //性质 True为买入 False为卖出
    boolean isBuy;

    public StockInfoByCom() {
    }

    public StockInfoByCom(String time, double price, double trunover, double f_price, boolean isBuy) {
        this.time = time;
        this.price = price;
        this.trunover = trunover;
        this.f_price = f_price;
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

    public double getTrunover() {
        return trunover;
    }

    public void setTrunover(double trunover) {
        this.trunover = trunover;
    }

    public double getF_price() {
        return f_price;
    }

    public void setF_price(double f_price) {
        this.f_price = f_price;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }
}
