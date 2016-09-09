package bl.vwap;

import blservice.vwap.VWAPService;
import org.junit.Test;
import vo.VolumeVO;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ZhangYF on 2016/9/9.
 */
public class VWAPTest {
    @Test
    public void predictVn() throws Exception {
        VWAPService vwap = VWAP.getInstance();
        VWAP_Param param = new VWAP_Param(1000,"sh600000",1,3,1,20);
        List<VolumeVO> volList= vwap.predictVn(param);
        for(VolumeVO vol:volList){
            System.out.println(vol.getTime()+":"+vol.getVolume());
        }
    }

}