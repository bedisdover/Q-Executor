package cn.edu.nju.software.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;

/**
 * Created by song on 16-9-11.
 *
 * 时间控制器
 */
@Controller
public class TimeController {

    @RequestMapping("/currentTime")
    @ResponseBody
    public long getCurrentTime() {
        return Calendar.getInstance().getTime().getTime();
    }
}
