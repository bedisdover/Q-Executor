package cn.edu.nju.software.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by song on 16-7-27.
 * <p>
 * 判断登录信息
 */
@Controller
public class LoginController {

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getParameter("userName"));
        System.out.println(request.getParameter("password"));
        System.out.println("LoginController.login");
    }
}
