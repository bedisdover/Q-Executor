package cn.edu.nju.software.utils;

import org.springframework.security.access.method.P;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jiayiwu on 8/9/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class TimeUtil {
    public static long getTimeLong(String date){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d=sdf.parse(date);
            c.setTime(d);
            long result=c.getTimeInMillis();
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public static String getNowDate(){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String result=sdf.format(date);
        return result;
    }
    public static String getPassedDate(double days,String nowDate){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = sdf.parse(nowDate);
            c.setTime(date);
            long offset=(long)days*24*60*60*1000;
            long time=c.getTimeInMillis()-offset;
            c.setTimeInMillis(time);
            Date resultDate=c.getTime();
            String result=sdf.format(resultDate);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDetailTime(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static Date getDate(String string){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String getDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static boolean isSameDate(Date date){
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1  = Calendar.getInstance();
        calendar.setTime(date);
        if(calendar.get(Calendar.YEAR)==calendar1.get(Calendar.YEAR)&&
                calendar.get(Calendar.MONTH)==calendar1.get(Calendar.MONTH)&&
                calendar.get(Calendar.DATE)==calendar1.get(Calendar.DATE)){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isOver6PM(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.HOUR_OF_DAY)>=18){
            return true;
        }

        return false;
    }


    public static long getMillisByHHmmss(String dateStr){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = dateFormat.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
