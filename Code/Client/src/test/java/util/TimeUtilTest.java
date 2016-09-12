package util;

import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.*;

/**
 * Created by song on 16-9-11.
 */
public class TimeUtilTest {
    @Test
    public void getTimePercent() throws Exception {
        assertEquals(0, TimeUtil.getTimePercent("9:30:00"), 0.001);
        assertEquals(0.5, TimeUtil.getTimePercent("11:30:00"), 0.001);
        assertEquals(1, TimeUtil.getTimePercent("15:00:00"), 0.001);
    }
}