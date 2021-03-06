package cn.edu.nju.software.vo;

import java.util.Date;

/**
 * Created by song on 16-9-7.
 *
 * 指标数据对象，包括RSI、BIAS、MACD
 */
public class IndexVO {
    /**
     * 时间，格式："yyyy-MM-dd"
     */
    private String date;

    /**
     * 指标数值
     */
    private double value;

    public IndexVO() {

    }

    public IndexVO(String date, double value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "date:" + date + "value:" + value;
    }
}
