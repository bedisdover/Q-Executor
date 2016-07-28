package cn.edu.nju.software.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by song on 16-7-27.
 * <p>
 * 账户信息管理，包括：
 * 用户注册、登陆
 * 修改用户信息（昵称/邮箱/密码）
 * 找回密码
 */
@Controller
public class AccountController {

    /**
     * 注册用户
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    public void register(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 登录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    public void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getParameter("userName"));
        System.out.println(request.getParameter("password"));
        System.out.println("LoginController.login");
    }

    /**
     * 修改账户信息（昵称/邮箱/密码）
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/modifyInfo"}, method = {RequestMethod.POST})
    public void modifyInfo(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 找回密码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/retrievePassword"}, method = {RequestMethod.POST})
    public void retrievePassword(HttpServletRequest request, HttpServletResponse response) {

    }
}
