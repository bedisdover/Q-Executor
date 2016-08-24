import cn.edu.nju.software.po.StockForMLPO;
import cn.edu.nju.software.service.MLForVWAPService;
import cn.edu.nju.software.service.MLForVWAPServiceImpl;
import cn.edu.nju.software.service.StockMLService;
import cn.edu.nju.software.service.StockMLServiceImpl;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by admin on 2016/8/20.
 */
public class MLForVWAPServiceTest {

    @Test
    public void testStaticService(){

//        StockMLService service=new StockMLServiceImpl();
//        ArrayList<StockForMLPO> list =service.getStockDataML("600000",200,48);
//        System.out.println("size: " + list.size());
//        StockForMLPO thisPO;
//        for(int i=0;i<list.size();i++){
//            thisPO=list.get(i);
//            System.out.println("num:"+(i+1)+" open:"+thisPO.getOpen()+"  close:"+thisPO.getClose()+"  low:"+thisPO.getLow()
//            +"  high:"+thisPO.getHigh()+"  Vol:"+thisPO.getVol()+"  Avg:"+thisPO.getAvg());
//        }

        MLForVWAPService ml_service=new MLForVWAPServiceImpl();
        ArrayList<Integer> list= ml_service.getStaticVol("600000");
        for(int i=0;i<list.size();i++){
            System.out.println("index:"+(i+1)+" "+list.get(i));
        }
    }


}
