package bl.time;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by song on 16-9-11.
 */
public class TimeBlImplTest {
    @Test
    public void getCurrentTime() throws Exception {
        TimeBlImpl timeBl = new TimeBlImpl();

        System.out.println(timeBl.getCurrentTime());
    }

}