package util;

import com.sun.javafx.geom.AreaOp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class DateUtil {

    public static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    //TMD 老子查了之后才知道SimpleDateFormat HH是24小时 hh是12小时
    public static DateFormat dateFormatDetail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date getDate(String format){
        Date date = new Date();
        date.setTime(0);
        try{
            date = dateFormat.parse(format);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("转化时间为millis毫秒有问题，可能遇到不好的timeToMarket的格式‘0’");
            return date;
        }
    }

    public static synchronized Date getDateByDetail(String format){
            Date date = new Date();
            try{
                date = dateFormatDetail.parse(format);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                System.err.println("转化时间有问题，默认以当前时间存储");
                return date;
            }
        }



    public static Date getAMStart(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,13);
        calendar.set(Calendar.SECOND,30);
        return calendar.getTime();
    }

    public static Date getAMEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,11);
        calendar.set(Calendar.MINUTE,31);
        calendar.set(Calendar.SECOND,30);
        return calendar.getTime();
    }

    public static Date getPMStart(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,58);
        calendar.set(Calendar.SECOND,30);
        return calendar.getTime();
    }
    public static Date getPMEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,1);
        calendar.set(Calendar.SECOND,30);
        return calendar.getTime();
    }

    public static boolean isWeekends(){
        Calendar calendar = Calendar.getInstance();
        int day =  calendar.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY){
            return true;
        }
        return false;
    }

    public static boolean isClose(){
        Date date = new Date();
        long millis = date.getTime();
        if(isWeekends()){
            return true;
        }

        if ((millis>getAMStart().getTime()&&millis<getAMEnd().getTime())||
                (millis>getPMStart().getTime()&&millis<getPMEnd().getTime())){
            return false;
        }
        return true;
    }
}
