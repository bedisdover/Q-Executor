package bl;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by ZhangYF on 2016/8/29.
 */
public class TimeUtilTest {
    @Test
    public void getCurrentIime() throws Exception {
        TimeUtil timeUtil = new TimeUtil();
        System.out.println(timeUtil.getCurrentIime(48));
    }

    @Test
    public void timeNodeToDateTest(){
        TimeUtil timeUtil = new TimeUtil();
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String day = df.format(date);
        System.out.println(day);
        System.out.println(timeUtil.timeNodeToDate(1,48));
        assertEquals(true,timeUtil.timeNodeToDate(1,48).matches(day+" 09:30:\\d{2}"));
    }

}