package bl.user;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by song on 16-11-25.
 */
public class SelfSelectServiceImplTest {
    @Test
    public void getUserSelectedStock() throws Exception {
        SelfSelectServiceImpl selfSelectService = new SelfSelectServiceImpl();

        selfSelectService.getUserSelectedStock("bedisdover", "1");
    }

}