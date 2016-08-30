package vo;

/**
 * Created by Jiayiwu on 8/11/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 *
 * 股票数据对象，用于绘制K线图
 */
public class StockKLineVO {
    private String date;
    private double open;
    private double high;
    private double close;
    private double low;
    private double volume;
    private double price_change;
    private double p_change;
    private double ma5;
    private double ma10;
    private double ma20;
    private double v_ma5;
    private double v_ma10;
    private double v_ma20;
    private double turnover;

    public StockKLineVO() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPrice_change() {
        return price_change;
    }

    public void setPrice_change(double price_change) {
        this.price_change = price_change;
    }

    public double getP_change() {
        return p_change;
    }

    public void setP_change(double p_change) {
        this.p_change = p_change;
    }

    public double getMa10() {
        return ma10;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }

    public double getMa20() {
        return ma20;
    }

    public void setMa20(double ma20) {
        this.ma20 = ma20;
    }

    public double getMa5() {
        return ma5;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    public double getV_ma5() {
        return v_ma5;
    }

    public void setV_ma5(double v_ma5) {
        this.v_ma5 = v_ma5;
    }

    public double getV_ma10() {
        return v_ma10;
    }

    public void setV_ma10(double v_ma10) {
        this.v_ma10 = v_ma10;
    }

    public double getV_ma20() {
        return v_ma20;
    }

    public void setV_ma20(double v_ma20) {
        this.v_ma20 = v_ma20;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StockKLineVO{");
        sb.append("date='").append(date).append('\'');
        sb.append(", open=").append(open);
        sb.append(", high=").append(high);
        sb.append(", close=").append(close);
        sb.append(", low=").append(low);
        sb.append(", volume=").append(volume);
        sb.append(", price_change=").append(price_change);
        sb.append(", p_change=").append(p_change);
        sb.append(", ma5=").append(ma5);
        sb.append(", ma10=").append(ma10);
        sb.append(", ma20=").append(ma20);
        sb.append(", v_ma5=").append(v_ma5);
        sb.append(", v_ma10=").append(v_ma10);
        sb.append(", v_ma20=").append(v_ma20);
        sb.append(", turnover=").append(turnover);
        sb.append('}');
        return sb.toString();
    }
}
