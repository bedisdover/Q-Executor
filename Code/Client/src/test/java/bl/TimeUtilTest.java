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
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 30);
        assertEquals(TimeUtil.timeToNode(c), 1);
        System.out.println(TimeUtil.timeToNode(c));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,30);
//        System.out.println(TimeUtil.timeToNode(calendar)+" 10:10");
        assertEquals(TimeUtil.timeToNode(calendar),1);

        calendar.set(Calendar.HOUR_OF_DAY,11);
        calendar.set(Calendar.MINUTE,0);
//        System.out.println(TimeUtil.timeToNode(calendar)+" 13:01");
        assertEquals(TimeUtil.timeToNode(calendar),19);

        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,10);
//        System.out.println(TimeUtil.timeToNode(calendar)+" 12:10");
        assertEquals(TimeUtil.timeToNode(calendar),25);

        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,10);
//        System.out.println(TimeUtil.timeToNode(calendar)+" 15:10");
        assertEquals(TimeUtil.timeToNode(calendar),-2);

    }

    @Test
    public void timeNodeToDateTest(){
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String day = df.format(date);
        System.out.println(TimeUtil.timeNodeToDate(1));
        assertEquals(true,TimeUtil.timeNodeToDate(1).matches(day+" 09:30"));
        System.out.println(TimeUtil.timeNodeToDate(22));
        assertEquals(true,TimeUtil.timeNodeToDate(22).matches(day+" 11:15"));
        System.out.println(TimeUtil.timeNodeToDate(25));
        assertEquals(true,TimeUtil.timeNodeToDate(25).matches(day+" 13:00"));
        System.out.println(TimeUtil.timeNodeToDate(48));
        assertEquals(true,TimeUtil.timeNodeToDate(48).matches(day+" 14:55"));
        System.out.println(TimeUtil.timeNodeToDate(50));
        assertEquals(null,TimeUtil.timeNodeToDate(50));


    }

}