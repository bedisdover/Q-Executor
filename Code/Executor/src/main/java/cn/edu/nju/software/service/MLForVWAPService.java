package cn.edu.nju.software.service;

import cn.edu.nju.software.vo.MLForVWAPPriceVO;

import java.util.ArrayList;

/**
 * Created by LiuXing on 2016/8/8.
 * @goal:VWAP算法获取机器学习部分预测的结果
 */
public interface MLForVWAPService {

    //返回最新数据下动态预测的均价
    public MLForVWAPPriceVO getDynamicPrice(String stockID);

    //返回最新数据下静态预测的48个成交量
    public ArrayList<Integer>   getStaticVol(String stockID);

}
