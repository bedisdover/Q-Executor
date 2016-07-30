package cn.edu.nju.software.controller;

import cn.edu.nju.software.config.MsgInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by song on 16-7-30.
 *
 */
@Controller
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request, String userName) {
        System.out.println(request.getParameter("userName"));
        System.out.println(request.getQueryString());
        System.out.println("TestController.test");
        System.out.println(userName + "+++");
        return "index";
    }
}
