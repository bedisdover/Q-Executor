package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.MLForVWAPService;
import cn.edu.nju.software.service.MLForVWAPServiceImpl;
import cn.edu.nju.software.service.StockMLService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by song on 16-8-3.
 * <p>
 * 测试VWAP
 */
@Controller
public class TestController {

    MLForVWAPService mlForVWAPService;

    @Resource
    StockMLService stockMLService;

    @RequestMapping("/testVolume")
    @ResponseBody
    public String testVolume() {
        mlForVWAPService= MLForVWAPServiceImpl.getInstance();
        String all_stock[] = stockMLService.getStocksNeedCal();

        String result = "";

        for (String anAll_stock : all_stock) {
            ArrayList<Integer> list = mlForVWAPService.getStaticVol(anAll_stock);

            for (int i = 0; i < list.size(); i++) {
                result += "index: " + (i + 1) + " " + list.get(i) + "\n";
            }
        }
        return result;
    }

    @RequestMapping("/testPrice")
    @ResponseBody
    public String testPrice() {
        String all_stock[] = stockMLService.getStocksNeedCal();

        String result = "";

        for (String stock: all_stock) {
            ArrayList<Double> list = mlForVWAPService.getDynamicPrice(stock).getPriceList();

            for (int i = 0; i < list.size(); i++) {
                result += "index: " + (i + 1) + " " + list.get(i) + "\n";
            }
        }

        return result;
    }
}
