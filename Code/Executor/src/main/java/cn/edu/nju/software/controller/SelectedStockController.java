package cn.edu.nju.software.controller;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.model.User;
import cn.edu.nju.software.service.UserSelectedStock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Jiayiwu on 8/14/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */

@Controller
public class SelectedStockController {
    @Resource
    UserSelectedStock userSelectedStock;

    @RequestMapping("/getUserSelectedStock")
    @ResponseBody
    public MsgInfo getUserSelectedStock(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(null == user)
            return new MsgInfo(false,"用户未登陆");

        return userSelectedStock.getUserSelectedStock(user.getUserName());
    }

    @RequestMapping("/addUserSelectedStock")
    @ResponseBody
    public MsgInfo addUserSelectedStock(HttpSession session,String codeNum){
        User user = (User) session.getAttribute("user");
        if(null == user)
            return new MsgInfo(false,"用户未登陆");

        return userSelectedStock.addUserSelectedStock(user.getUserName(),codeNum);
    }

    @RequestMapping("/deleteUserSelectedStock")
    @ResponseBody
    public MsgInfo deleteUserSelectedStock(HttpSession session,String codeNum){
        User user = (User) session.getAttribute("user");
        if(null == user)
            return new MsgInfo(false,"用户未登陆");

        return userSelectedStock.deleteUserSelectedStock(user.getUserName(),codeNum);
    }
}
