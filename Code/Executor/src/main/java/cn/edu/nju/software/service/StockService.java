package cn.edu.nju.software.service;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.vo.StockNowTimeVO;

import java.util.List;

/**
 * Created by Jiayiwu on 8/9/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface StockService {
    public MsgInfo getStockNowTime(String Code);
}
