package bl;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by ZhangYF on 2016/8/29.
 */
public class TimeUtilTest {
    @Test
    public void getCurrentIime() throws Exception {

        System.out.println(TimeUtil.getCurrentIime());
    }

    @Test
    public void timeToNodeTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,10);
        calendar.set(Calendar.MINUTE,10);
        assertEquals(TimeUtil.timeToNode(calendar),7);

        calendar.set(Calendar.HOUR_OF_DAY,13);
        calendar.set(Calendar.MINUTE,1);
        assertEquals(TimeUtil.timeToNode(calendar),25);

        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,10);
        assertEquals(TimeUtil.timeToNode(calendar),-1);

        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,10);
        assertEquals(TimeUtil.timeToNode(calendar),-2);

    }

    @Test
    public void timeNodeToDateTest(){
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String day = df.format(date);
        System.out.println(day);
//        System.out.println(TimeUtil.timeNodeToDate(1,48));
//        assertEquals(true,TimeUtil.timeNodeToDate(1,48).matches(day+" 09:30:\\d{2}"));
    }

}