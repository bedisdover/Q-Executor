package vo;

/**
 * Created by Jiayiwu on 8/12/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StockInfoByCom {
    //时间
    private String time;
    //价格
    private double price;
    //交易量
    private double volume;
    //价格变动
    private double change_price;
    //交易总量
    private double total_number;
    //前一笔交易价格
    private double f_price;
    //性质
    private int type;
    // 性质映射关系
    private String mapping[] = new String[]{"买盘", "卖盘", "中性盘"};

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

    public String getType() {
        return mapping[type];
    }

    public int getTypeNum() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getChange_price() {
        return price - f_price;
    }

    public double getTotal_number() {
        return total_number;
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
