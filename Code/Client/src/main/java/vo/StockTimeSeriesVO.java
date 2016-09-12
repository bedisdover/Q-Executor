package vo;

/**
 * Created by Jiayiwu on 8/9/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 *
 * 分时图所需数据对象
 */
public class StockTimeSeriesVO {
    //时间
    private String timeLine;
    //价格
    private double price;
    //成交量
    private double volume;
    //均价
    private double avePrice;

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

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StockTimeSeriesVO{");
        sb.append("timeLine='").append(timeLine).append('\'');
        sb.append(", price=").append(price);
        sb.append(", avePrice=").append(avePrice);
        sb.append('}');
        return sb.toString();
    }
}
