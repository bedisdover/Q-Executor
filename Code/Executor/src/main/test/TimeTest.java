import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 王栋 on 2016/8/13 0013.
 */
public class TimeTest {

    @Test
    public void test1() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        System.out.println(dateFormat.format(date));
        System.out.println(dateFormat.parse(dateFormat.format(date)).getTime());
        System.out.println(dateFormat.format(new Date(dateFormat.parse(dateFormat.format(date)).getTime())));

        System.out.println(dateFormat.format(new Date(dateFormat.parse(dateFormat.format(date)).getTime()+10000)));
    }
}
