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
    double volume;
    //价格变动
    double change_price;
    //交易总量
    double total_number;
    //前一笔交易价格
    double f_price;
    //性质
    int type;

    public StockInfoByCom() {
    }

    public StockInfoByCom(String time, double price, double volume, double f_price, int type) {
        this.time = time;
        this.price = price;
        this.volume = volume;
        this.f_price = f_price;
        this.type= type;
        this.change_price = price-f_price;
        this.total_number=price*volume;
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

    public double getF_price() {
        return f_price;
    }

    public void setF_price(double f_price) {
        this.f_price = f_price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StockInfoByCom{");
        sb.append("time='").append(time).append('\'');
        sb.append(", price=").append(price);
        sb.append(", volume=").append(volume);
        sb.append(", change_price=").append(change_price);
        sb.append(", total_number=").append(total_number);
        sb.append(", f_price=").append(f_price);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
