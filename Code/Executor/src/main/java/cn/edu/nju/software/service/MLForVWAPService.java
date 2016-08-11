package cn.edu.nju.software.service;

/**
 * Created by LiuXing on 2016/8/8.
 * @goal:VWAP算法获取机器学习部分预测的结果
 */
public interface MLForVWAPService {
    public double getForecastPrice(String stockID);
    public int    getForecastVol(String stockID);
}
