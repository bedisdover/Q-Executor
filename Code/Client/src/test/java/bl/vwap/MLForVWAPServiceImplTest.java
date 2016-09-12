package bl.vwap;

import blservice.vwap.MLForVWAPService;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by song on 16-9-9.
 */
public class MLForVWAPServiceImplTest {

    @Test
    public void getDynamicPrice() throws Exception {
        MLForVWAPService mlForVWAPService = new MLForVWAPServiceImpl();
        mlForVWAPService.getDynamicPrice("sh600000");
    }

    @Test
    public void getStaticVol() throws Exception {
        MLForVWAPService mlForVWAPService = new MLForVWAPServiceImpl();
        mlForVWAPService.getStaticVol("sh600000");
    }

}