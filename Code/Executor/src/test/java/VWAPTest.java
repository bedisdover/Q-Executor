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
        int currentTime = 10;
        int tiemNum = 48;

        VWAP_Param param = new VWAP_Param(userVol ,stockid,delta,currentTime,tiemNum);

        List<Integer> preVn = vwap.predictVn(param);
//        List<Integer> actualVn = getActualVol();
//        List<Integer> errorValue = new ArrayList<>();
//
//        for(int i = 0 ;i<preVn.size();i++){
//            int err = preVn.get(i)-actualVn.get(i);
//            errorValue.add(err);
//        }

        System.out.println(preVn);
//        System.out.println(actualVn);
//        System.out.println(errorValue);

    }

    private List<Integer> getActualVol(){
        return null;
    }

}