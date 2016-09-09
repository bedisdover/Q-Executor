package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.MLForVWAPService;
import cn.edu.nju.software.vo.MLForVWAPPriceVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by song on 16-9-9.
 *
 * VWAP相关
 */
@Controller
public class MLForVWAPController {
    @Resource
    MLForVWAPService mlForVWAPService;

    /**
     * 最新数据下动态预测的均价
     */
    @RequestMapping("/dynamicPrice")
    @ResponseBody
    public MLForVWAPPriceVO getDynamicPrice(String stockID) {
        return mlForVWAPService.getDynamicPrice(stockID);
    }

    /**
     * 最新数据下静态预测的48个成交量
     */
    @RequestMapping("/staticVol")
    @ResponseBody
    public List<Integer> getStaticVol(String stockID) {
        return mlForVWAPService.getStaticVol(stockID);
    }
}
