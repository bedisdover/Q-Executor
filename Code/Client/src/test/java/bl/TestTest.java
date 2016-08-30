package bl;

import org.junit.Test;

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
        imc.getUserSelectedStock();
    }
}