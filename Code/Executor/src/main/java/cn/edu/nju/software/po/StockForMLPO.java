package cn.edu.nju.software.po;

/**
 * Created by LiuXing on 2016/8/8.
 * @goal:定义用户机器学习部分的高频股票PO类
 */
public class StockForMLPO {


    private double avg;    //高频时间段内的均价
    private double open;   //初始价（元）,价格统一保留两位小数
    private double close; //终止价（元）
    private double high;//最高价（元）
    private double low;//最低价（元）
    private double  vol;//成交量（笔）

    public StockForMLPO(double avg, double open, double close, double high, double low, double vol) {
        this.avg = avg;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.vol = vol;
    }

    public StockForMLPO() {
    }

    public double getAvg(){return avg;}

    public void setAvg(double avg){this.avg=avg;}

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StockForMLPO{");
        sb.append("avg=").append(avg);
        sb.append(", open=").append(open);
        sb.append(", close=").append(close);
        sb.append(", high=").append(high);
        sb.append(", low=").append(low);
        sb.append(", vol=").append(vol);
        sb.append('}');
        return sb.toString();
    }
}
