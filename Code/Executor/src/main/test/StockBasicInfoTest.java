import cn.edu.nju.software.dao.StockBasicInfoDao;
import cn.edu.nju.software.dao.StockNowTimeDao;
import cn.edu.nju.software.dao.UserDao;
import cn.edu.nju.software.model.StockBasicInfo;
import cn.edu.nju.software.utils.TimeUtil;
import cn.edu.nju.software.vo.StockNowTimeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Objects;

/**
 * Created by 王栋 on 2016/8/2 0002.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"mvc.xml","/applicationContext.xml"})
public class StockBasicInfoTest {
    @Resource
    StockBasicInfoDao stockDao;
    @Resource
    StockNowTimeDao stockNowTimeDao;
    @Test
    public void test(){
        //test获取所有的股票的基本信息的对象
        List<StockBasicInfo> stockBasicInfos = stockDao.getAllStocksBasicInfo();
        for(StockBasicInfo info : stockBasicInfos){
            System.out.println(info.getName());
        }

        System.err.println(stockBasicInfos.size());
    }

    @Test
    public void test2(){
        //根据特定股票的code获取某支股票
        StockBasicInfo stock1 = stockDao.getStockBasicInfo("sh600000");
        System.out.println(stock1);
        StockBasicInfo stock2 = stockDao.getStockBasicInfo("600004");
        System.out.println(stock2);
    }

    @Test
    public void test3(){
        List<StockBasicInfo> list = stockDao.getStocksBasicInfoByCode("60000");
        for (StockBasicInfo stockInfo : list) {
            System.out.println(stockInfo);
        }
    }

    @Test
    public void test4(){
        List<StockBasicInfo> temp = stockDao.getStocksBasicInfoByName("银行");
        for(StockBasicInfo info : temp){
            System.out.println(info);
        }
    }

    @Test
    public void test5(){
        List<StockBasicInfo> temp = stockDao.getStocksBasicInfoByArea("上海");
        for(StockBasicInfo info : temp){
            System.out.println(info);
        }
    }


    @Test
    public void test6(){
        List<StockNowTimeVO> result = stockNowTimeDao.getNowTime("sh600000", TimeUtil.getDate("2016-08-10"));
        for (StockNowTimeVO stockNowTimeVO : result){
            System.out.println(stockNowTimeVO);
        }
    }
}
