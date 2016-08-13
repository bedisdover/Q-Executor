import cn.edu.nju.software.dao.StockInfoDao;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by 王栋 on 2016/8/13 0013.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/mvc.xml","/applicationContext.xml"})
public class StockInfoDaoTest {
    @Resource
    StockInfoDao stockInfoDao;

    
}
