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

        System.out.println(TimeUtil.getCurrentIime(48));
    }

    @Test
    public void timeNodeToDateTest(){
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String day = df.format(date);
        System.out.println(day);
        System.out.println(TimeUtil.timeNodeToDate(1,48));
        assertEquals(true,TimeUtil.timeNodeToDate(1,48).matches(day+" 09:30:\\d{2}"));
    }

}