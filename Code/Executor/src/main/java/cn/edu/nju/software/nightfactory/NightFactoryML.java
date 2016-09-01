package cn.edu.nju.software.nightfactory;


import cn.edu.nju.software.service.MLForVWAPServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Liu on 2016/8/29.
 */
public class NightFactoryML implements ServletContextListener {

    private Timer timer;
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    public void contextDestroyed(ServletContextEvent event) {
        timer.cancel();
        event.getServletContext().log("timer destroyed!");
    }

    public void contextInitialized(ServletContextEvent event) {
        timer = new Timer();
        event.getServletContext().log("timer begin!");
        // 设置每晚00:02分执行任务
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 2);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();

        timer.schedule( MLForVWAPServiceImpl.getInstance(), date, PERIOD_DAY);

        event.getServletContext().log("add to the schedule list!");
    }

}
