package bl;

import bl.stock.GetStockDataServiceImpl;
import bl.user.SelfSelectServiceImpl;
import bl.user.UserServiceImpl;
import org.junit.Test;
import vo.HotStockVO;

/**
 * Created by song on 16-8-24.
 *
 *
 */
public class TestTest {
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }


    @Test
    public void test() throws Exception{
        UserServiceImpl im = new UserServiceImpl();
        im.login("wujiayibest","test1234");

        SelfSelectServiceImpl imc = new SelfSelectServiceImpl();
        SelfSelectServiceImpl selfSelectService = new SelfSelectServiceImpl();
        selfSelectService.getUserSelectedStock("wujiayibest","test1234");
//        imc.getUserSelectedStock();
    }

    @Test
    public void test2() throws Exception {
        GetStockDataServiceImpl impl = new GetStockDataServiceImpl();
        for (HotStockVO vo : impl.getHotStock()){
            System.out.println(vo);
        }
    }
}