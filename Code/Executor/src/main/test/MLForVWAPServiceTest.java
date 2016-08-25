import cn.edu.nju.software.po.InforForMLPO;
import cn.edu.nju.software.po.StockForMLPO;
import cn.edu.nju.software.service.MLForVWAPService;
import cn.edu.nju.software.service.MLForVWAPServiceImpl;
import cn.edu.nju.software.service.StockMLService;
import cn.edu.nju.software.service.StockMLServiceImpl;
import cn.edu.nju.software.vo.MLForVWAPPriceVO;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by admin on 2016/8/20.
 */
public class MLForVWAPServiceTest {

    @Test
    public void testStaticService(){

        StockMLService service=new StockMLServiceImpl();

        //测试获取用于静态模型的接口
//        ArrayList<StockForMLPO> list =service.getStockDataML("600000",200,48);
//        System.out.println("size: " + list.size());
//        StockForMLPO thisPO;
//        for(int i=0;i<list.size();i++){
//            thisPO=list.get(i);
//            System.out.println("num:"+(i+1)+" open:"+thisPO.getOpen()+"  close:"+thisPO.getClose()+"  low:"+thisPO.getLow()
//            +"  high:"+thisPO.getHigh()+"  Vol:"+thisPO.getVol()+"  Avg:"+thisPO.getAvg());
//        }

        //测试获取用于动态模型的接口
//        ArrayList<InforForMLPO> dynamicPrice=service.getDynamicInforML("600000",200,48);
//        InforForMLPO thisPO;
//        for(int i=0;i<dynamicPrice.size();i++){
//            thisPO=dynamicPrice.get(i);
//            System.out.println("index:"+(i+1)+" firstDay:"+thisPO.getFirstDay()+" secondDay:"+thisPO.getSecondDay()+" thirdDay:"+thisPO.getThirdDay()+
//                              " firstTime:"+thisPO.getFirstTime()+" secondTime:"+thisPO.getSecondTime()+" thirdTime:"+thisPO.getThirdTime()
//            +" currentTime:"+thisPO.getCurrentTime());
//        }

        //测试获取今日数据的模型接口
//        ArrayList<StockForMLPO> poList=service.getTodayInforML("600000");
//        StockForMLPO thisPO;
//        for(int i=0;i<poList.size();i++){
//            thisPO=poList.get(i);
//            System.out.println("index:"+(i+1)+"open:"+thisPO.getOpen()+" close:"+thisPO.getClose()+" low:"+thisPO.getLow()+" high:"+thisPO.getHigh()+" vol:"+thisPO.getVol()+" avg:"+thisPO.getAvg());
//        }



        MLForVWAPService ml_service=new MLForVWAPServiceImpl();

        //测试向VWAP传递静态成交量
//        ArrayList<Integer> list= ml_service.getStaticVol("600000");
//        for(int i=0;i<list.size();i++){
//            System.out.println("index:"+(i+1)+" "+list.get(i));
//        }

       //测试向VWAP传递动态数据
        MLForVWAPPriceVO dynamicPrice=ml_service.getDynamicPrice("600000");
        ArrayList<Double> priceList=dynamicPrice.getPriceList();
        int currentTime=dynamicPrice.getCurrentTime();
        System.out.println("currnetTime:"+currentTime);
        for(int i=0;i<priceList.size();i++){
            System.out.println("index:"+(i+1)+"   "+priceList.get(i));
        }


    }


}