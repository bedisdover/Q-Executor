import cn.edu.nju.software.dao.StockInfoDao;
import cn.edu.nju.software.model.StockInfoByCom;
import cn.edu.nju.software.model.StockInfoByPer;
import cn.edu.nju.software.utils.TimeUtil;
import cn.edu.nju.software.vo.DeepStockVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 王栋 on 2016/8/13 0013.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/mvc.xml","/applicationContext.xml"})
public class StockInfoDaoTest {
    @Resource
    StockInfoDao stockInfoDao;

    @Test
    public void test1(){
        List<StockInfoByCom> stockInfoByComs = stockInfoDao.getComStockInfo("sh600000", TimeUtil.getDate("2016-08-12"));

        for(StockInfoByCom com:stockInfoByComs){
            System.out.println(com);
        }
    }

    @Test
    public void test2(){
        //TODO
        // 测试过18点之后的
        // 还没测试当前交易日18点之前的(即爬取网页获取数据的方式)
        List<StockInfoByPer> stockInfoByPers = stockInfoDao.getPerStockInfo("sh600000",TimeUtil.getDate("2016-08-17"));

        for(StockInfoByPer stockInfoByPer : stockInfoByPers){
            System.out.println(stockInfoByPer);
        }

    }

    @Test
    public void test3(){
        List<DeepStockVO> deepStockVOs = stockInfoDao.getDeepStockVo("sh600000");
        for (DeepStockVO vo:deepStockVOs){
            System.out.println(vo);
        }

    }

}
