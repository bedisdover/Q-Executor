package cn.edu.nju.software.controller;


import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.service.StockService;
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
    public MsgInfo isLogin(String codeNum) {

        return stockService.getStockNowTime(codeNum);
    }
}
