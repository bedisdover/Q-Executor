package cn.edu.nju.software.vo;

/**
 * Created by Jiayiwu on 8/12/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StockInfoByTime {
    private String time ; //时间
    private double price ;//成交价格
    private double volume;//成交量

    public StockInfoByTime() {
    }

    public StockInfoByTime(String time, double price, double volume) {
        this.time = time;
        this.price = price;
        this.volume = volume;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
