package cn.edu.nju.software.po;

import cn.edu.nju.software.utils.TimeUtil;

import java.sql.Timestamp;

/**
 * Created by kinsley on 16-9-7.
 */
public class Stock5MInPO {

    private Timestamp time;
    private double open;
    private double high;
    private double low;
    private double close;

    public Stock5MInPO() {
    }

    public Stock5MInPO(Timestamp time, double open, double high, double low, double close) {
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "Stock5MInPO{" +
                "time=" + time +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                '}';
    }
}
