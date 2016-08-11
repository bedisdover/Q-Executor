import cn.edu.nju.software.dao.StockJsonDao;
import cn.edu.nju.software.vo.StockKLineVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 王栋 on 2016/8/11 0011.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/mvc.xml","/applicationContext.xml"})
public class StockJsonDaoTest {
    @Resource
    StockJsonDao stockJsonDao;

    @Test
    public void testDaily(){
        List<StockKLineVO> stockKLineVOs = stockJsonDao.getKLineByDay("sh600000");
        for (StockKLineVO vo:stockKLineVOs){
            System.out.println(vo.toString());
        }
    }

    @Test
    public void testWeekly(){
        List<StockKLineVO> stockKLineVOs = stockJsonDao.getKLineByWeek("sh600000");
        for (StockKLineVO vo:stockKLineVOs){
            System.out.println(vo.toString());
        }
    }

    @Test
    public void testMonthly(){
        List<StockKLineVO> stockKLineVOs = stockJsonDao.getKLineByMonth("sh6000000");
        for (StockKLineVO vo:stockKLineVOs){
            System.out.println(vo.toString());
        }
    }
}
