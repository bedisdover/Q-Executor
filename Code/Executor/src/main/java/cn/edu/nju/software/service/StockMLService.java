package cn.edu.nju.software.service;

import cn.edu.nju.software.po.StockForMLPO;

import java.util.Iterator;

/**
 * Created by LiuXing on 2016/8/8.
 *@goal:从数据库获取用于机器学习的高频股票数据的接口
 */
public interface StockMLService {

    /*
       @param:股票代码
       @goal:获取代码为stockID的股票最近n个的高频数据（暂定n=200，强调是“n个”而不是“n天”，n天可能因为股票停牌而导致数据太少）
       @return:股票高频数据的迭代器
     */

    public Iterator<StockForMLPO> getStockDataML(String stockID);
}
