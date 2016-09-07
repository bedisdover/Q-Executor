package cn.edu.nju.software.vo;

import java.util.Date;

/**
 * Created by song on 16-9-7.
 *
 * KDJ指标数据对象
 */
public class KDJVO {
    private double value_K;

    private double value_D;

    private double value_J;

    /**
     * 日期，格式"yyyy-MM-dd"
     */
    private Date date;

    public KDJVO() {

    }

    public KDJVO(double value_K, double value_D, double value_J) {
        this.value_K = value_K;
        this.value_D = value_D;
        this.value_J = value_J;
    }

    public double getValue_K() {
        return value_K;
    }

    public void setValue_K(double value_K) {
        this.value_K = value_K;
    }

    public double getValue_D() {
        return value_D;
    }

    public void setValue_D(double value_D) {
        this.value_D = value_D;
    }

    public double getValue_J() {
        return value_J;
    }

    public void setValue_J(double value_J) {
        this.value_J = value_J;
    }

    @Override
    public String toString() {
        return "date:" + date + "value_K:" + value_K + "value_D:" + value_D + "value_J:" + value_J;
    }
}
