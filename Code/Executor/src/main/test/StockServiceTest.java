import cn.edu.nju.software.service.StockService;
import cn.edu.nju.software.vo.StockKLineVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by st0001 on 2016/9/8 0008.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/mvc.xml","/applicationContext.xml"})
public class StockServiceTest {
    @Resource
    StockService stockService;

    @Test
    public void testGetMinKInfo(){
        List<StockKLineVO> stockKLineVOs = stockService.getKLineByMinute("sh600000",60);
        for (StockKLineVO vo : stockKLineVOs){
            System.out.println(vo);
        }
    }
}
