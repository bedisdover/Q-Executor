package cn.edu.nju.software.service;

import cn.edu.nju.software.po.InforForMLPO;
import cn.edu.nju.software.po.StockForMLPO;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by LiuXing on 2016/8/8.
 *@goal:从数据库获取用于机器学习的高频股票数据的接口
 */
public interface StockMLService {

    /*
       @param:股票代码,数据条目数,第几个时间片
       @goal:获取代码为stockID的股票最近n个的高频数据（暂定numOfStock=200，强调是“n个”而不是“n天”，n天可能因为股票停牌而导致数据太少）
       @return:股票高频数据的list
     */
    public ArrayList<StockForMLPO> getStockDataML(String stockID, int numOfStock, int currentTime);


    /*
    @param:同上
    @goal:获取代码为stockID的股票以InforForMLPO类为基础的高频数据
    @return:股票高频数据的list
    @limit：currentTime>3，因为InforForMLPO类的限制
     */
    public ArrayList<InforForMLPO> getDynamicInforML(String stockID,int numOfStock,int currentTime);


    /*
    @param:股票代码
    @goal：获取股票代码为stockID的股票今天所有已经发生的高频数据（严格按照时间顺序，不能缺少）。
           若尚未开盘或者股票停牌、休市，list的size=0；若今日股市已闭市，list的size应该等于48
    @return:今日高频数据的list
     */
    public ArrayList<StockForMLPO> getTodayInforML(String stockID);
}
