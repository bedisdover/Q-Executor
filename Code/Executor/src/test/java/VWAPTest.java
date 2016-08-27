package src.test.java;

import cn.edu.nju.software.service.VWAP;
import cn.edu.nju.software.service.VWAPService;
import cn.edu.nju.software.service.VWAP_Param;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ZhangYF on 2016/8/25.
 */
public class VWAPTest {
    @Test
    public void predictVnTest() throws Exception {
        VWAPService vwap = new VWAP();
        long userVol = 100000;
        String stockid = "sh600000";
        double delta = 1.0;
        int currentTime = 20;
        int tiemNum = 48;

        long startTime = System.currentTimeMillis();
        VWAP_Param param = new VWAP_Param(userVol ,stockid,delta,currentTime,tiemNum);

        List<Integer> preVn = vwap.predictVn(param);
//        List<Integer> actualVn = getActualVol();
//        List<Integer> errorValue = new ArrayList<>();
//
//        for(int i = 0 ;i<preVn.size();i++){
//            int err = preVn.get(i)-actualVn.get(i);
//            errorValue.add(err);
//        }

        long sum = 0;
        for (int i:preVn){
            sum+=i;
        }
        System.out.println(sum);
        System.out.println(preVn);
        long endTime = System.currentTimeMillis();
        long time = (endTime - startTime)/1000;
        System.out.println("测试用时："+time+"s");
//        System.out.println(actualVn);
//        System.out.println(errorValue);

    }

    private List<Integer> getActualVol(){
        return null;
    }

}