package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class DateUtil {

    public static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public static DateFormat dateFormatDetail = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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
}
