package vo;

/**
 * Created by Jiayiwu on 8/12/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StockInfoByPer {
    //时间
    private String time;
    //价格
    private double price;
    //价格变动
    private double change_price;
    //成交量
    private double volume;
    //成交额
    private double totalNum;
    //性质 0为买盘 1为卖盘 2为中性盘
    private int type;

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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(double totalNum) {
        this.totalNum = totalNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StockInfoByPer{");
        sb.append("time='").append(time).append('\'');
        sb.append(", price=").append(price);
        sb.append(", change_price=").append(change_price);
        sb.append(", volume=").append(volume);
        sb.append(", totalNum=").append(totalNum);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
