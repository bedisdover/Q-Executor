package cn.edu.nju.software.vo;

/**
 * Created by Jiayiwu on 8/9/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StockNowTimeVO {
    //时间
    String timeLine;
    //价格
    double price;
    //均价
    double avePrice;

    public StockNowTimeVO() {
    }

    public StockNowTimeVO(String timeLine, double price, double avePrice) {
        this.timeLine = timeLine;
        this.price = price;
        this.avePrice = avePrice;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAvePrice() {
        return avePrice;
    }

    public void setAvePrice(double avePrice) {
        this.avePrice = avePrice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StockNowTimeVO{");
        sb.append("timeLine='").append(timeLine).append('\'');
        sb.append(", price=").append(price);
        sb.append(", avePrice=").append(avePrice);
        sb.append('}');
        return sb.toString();
    }
}
