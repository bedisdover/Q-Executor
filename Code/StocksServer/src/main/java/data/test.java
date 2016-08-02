package data;

import util.ConnectionFactory;
import util.DateUtil;

import java.sql.Connection;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class test {

    public static void main(String[] args){

        System.out.println(DateUtil.getDateByDetail("2016-8-2 09:15:00").getTime());
        System.out.println(DateUtil.getDateByDetail("2016-8-3 09:12:00").getTime()-
        DateUtil.getAMStart().getTime());

        System.out.println(DateUtil.getDateByDetail("2016-8-3 11:33:00").getTime()-
                DateUtil.getAMEnd().getTime());
        System.out.println(DateUtil.getDateByDetail("2016-8-3 12:57:00").getTime()-
                DateUtil.getPMStart().getTime());
        System.out.println(DateUtil.getDateByDetail("2016-8-3 15:03:00").getTime()-
                DateUtil.getPMEnd().getTime());

        System.out.println(DateUtil.getDateByDetail("2016-08-03 12:57:00").getTime()-
                DateUtil.getDateByDetail("2016-8-3 12:57:00").getTime());

        System.out.println(DateUtil.isWeekends());
    }
}
