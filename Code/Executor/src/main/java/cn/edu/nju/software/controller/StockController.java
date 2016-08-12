package cn.edu.nju.software.controller;


import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.model.StockBasicInfo;
import cn.edu.nju.software.model.StockInfoByCom;
import cn.edu.nju.software.model.StockInfoByPer;
import cn.edu.nju.software.service.StockService;
import cn.edu.nju.software.vo.StockInfoByPrice;
import cn.edu.nju.software.vo.StockInfoByTime;
import cn.edu.nju.software.vo.StockKLineVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Jiayiwu on 8/9/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Controller
public class StockController {
    @Resource
    StockService stockService;


    @RequestMapping("/nowTime")
    @ResponseBody
    public MsgInfo getNowTime(String codeNum) {

        return stockService.getStockNowTime(codeNum);
    }


    @RequestMapping("/KLineDay")
    @ResponseBody
    public List<StockKLineVO> getKLineByDay(String codeNum) {

        return stockService.getKLineByDay(codeNum);
    }
    @RequestMapping("/KLineWeek")
    @ResponseBody
    public List<StockKLineVO> getKLineByWeek(String codeNum) {

        return stockService.getKLineByWeek(codeNum);
    }
    @RequestMapping("/KLineMonth")
    @ResponseBody
    public List<StockKLineVO> getKLineByMonth(String codeNum) {

        return stockService.getKLineByMonth(codeNum);
    }


    //获取公司基本信息
    @RequestMapping("/BasicComInfo")
    @ResponseBody
    public StockBasicInfo getBasicComInfo(String codeNum) {

        return stockService.getStockBasicInfo(codeNum);
    }

    //分时数据
    @RequestMapping("/StockInfoByTime")
    @ResponseBody
    public List<StockInfoByTime> getStockInfoByTime(String codeNum) {

        return stockService.getStockInfoByTime(codeNum);
    }
    //分价数据
    @RequestMapping("/StockInfoByPrice")
    @ResponseBody
    public List<StockInfoByPrice> getStockInfoByPrice(String codeNum) {

        return stockService.getStockInfoByPrice(codeNum);
    }
    //大单数据
    @RequestMapping("/ComStockInfo")
    @ResponseBody
    public List<StockInfoByCom> getComStockInfo(String codeNum) {

        return stockService.getComStockInfo(codeNum);
    }
    //逐笔数据
    @RequestMapping("/PerStockInfo")
    @ResponseBody
    public List<StockInfoByPer> getPerStockInfo(String codeNum) {

        return stockService.getPerStockInfo(codeNum);
    }
}
