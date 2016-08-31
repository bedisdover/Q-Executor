package bl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ZhangYF on 2016/8/29.
 */
public class TimeUtil {

    /**
     * 获取当前时间片
     * @param timeNum 时间片数量
     * @return 当前为第几个时间片，如果不在交易时间段，返回-1
     */
    public static int getCurrentIime(int timeNum){
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        hour = 14;
        minute = 55;

        if(hour>=9 && hour<=11){
            if(hour==9 && minute<30){
                return -1;
            }else if(hour==11 && minute>=30){
               return -1;
            }else{
                return ((hour-9)*60+minute-30)/timeNumTOLength(timeNum)+1;
            }
        }
        if(hour>=13 && hour <15){
            return ((hour-13)*60+minute)/timeNumTOLength(timeNum)+timeNum/2+1;
        }else{
            return -1;
        }

    }

    /**
     * 强时间片数量转换为时间片长度，单位：分钟
     * @param timeNum 时间片数量
     * @return 时间片长度
     */
    private static int timeNumTOLength(int timeNum){
        return 240/timeNum;
    }

    /**
     * 将时间片转化为对应时间
     * @param timeNode 时间片
     * @param timeNum 时间片数量
     * @return yyyy-MM-dd HH:mm:ss格式时间
     */
    public static String timeNodeToDate(int timeNode, int timeNum){
        Calendar calendar=Calendar.getInstance();
        int timeLength = 240/timeNum;
        if(timeNode<(timeNum/2)){
            calendar.set(Calendar.HOUR_OF_DAY,9);
            calendar.set(Calendar.MINUTE,30);
            calendar.add(Calendar.MINUTE,timeLength*(timeNode-1));
        }else{
            calendar.set(Calendar.HOUR_OF_DAY,13);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,1);
            calendar.add(Calendar.MINUTE,timeLength*(timeNode - timeNum/2 -1));
        }
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = calendar.getTime();
        String time = df.format(date);
        time = time.substring(0,time.length()-2);
        time = time+"05";
        return time;
    }
}