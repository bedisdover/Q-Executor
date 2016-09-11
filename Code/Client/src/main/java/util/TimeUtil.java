package util;

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

    /**
     * 1小时的毫秒数
     */
    public static final long HOUR_SIZE = 3600000L;

    public static long getTimeLong(String date) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(date);
            c.setTime(d);
            long result = c.getTimeInMillis();
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public static String getNowDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = sdf.format(date);
        return result;
    }

    public static String getPassedDate(double days, String nowDate) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(nowDate);
            c.setTime(date);
            long offset = (long) days * 24 * 60 * 60 * 1000;
            long time = c.getTimeInMillis() - offset;
            c.setTimeInMillis(time);
            Date resultDate = c.getTime();
            String result = sdf.format(resultDate);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDetailTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static Date getDate(String string) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String getDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static boolean isSameDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH) &&
                calendar.get(Calendar.DATE) == calendar1.get(Calendar.DATE)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOver6PM(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 18) {
            return true;
        }

        return false;
    }


    public static long getMillisByHHmmss(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = dateFormat.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static String getDateHHmmss(long millis) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(millis);
        return dateFormat.format(date);

    }

    public static Date getDayBefore(String date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(getDate(date));
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - 1);

        return calendar.getTime();
    }

    public static Date getDayAfter(String date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(getDate(date));

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);

        return calendar.getTime();
    }

    public static Date getStartTime() {
        return getTime("9:29");
    }

    public static Date getEndTime() {
        return getTime("15:01");
    }

    public static Date getInterruptTime() {
        return getTime("11:29");
    }

    public static Date getResumeTime() {
        return getTime("13:01");
    }

    /**
     * 获得最新交易时间占整个交易日区间的比例
     *
     * @param time 最新交易时间点,以有数据为准,格式:"HH:mm:ss"
     * @return 占比
     * @see present.panel.stock.center.SinglePanel#getTimePercent(String)
     */
    public static double getTimePercent(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        try {
            long temp = dateFormat.parse(time).getTime() - dateFormat.parse("09:30:00").getTime();

            if (temp < HOUR_SIZE << 1) {// 9:30~11:30
                return temp / (HOUR_SIZE << 2);
            } else if (temp >= HOUR_SIZE << 1 && temp <= HOUR_SIZE * 3.5){// 11:30~13:00
                return 0.5;
            } else if (temp > HOUR_SIZE * 3.5 && temp < HOUR_SIZE * 5.5){// 13:00~15:00
                return (temp - HOUR_SIZE * 3.5) / (HOUR_SIZE << 1);
            } else {
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static Date getTime(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = null;

        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
