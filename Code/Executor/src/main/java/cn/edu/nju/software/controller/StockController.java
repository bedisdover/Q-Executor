package cn.edu.nju.software.controller;


import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.service.StockService;
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
}
