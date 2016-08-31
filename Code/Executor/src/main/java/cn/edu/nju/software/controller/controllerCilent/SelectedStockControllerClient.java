package cn.edu.nju.software.controller.controllerCilent;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.dao.UserDao;
import cn.edu.nju.software.model.User;
import cn.edu.nju.software.service.UserSelectedStock;
import cn.edu.nju.software.utils.SHA256;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by JiayiWu on 8/30/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Controller
public class SelectedStockControllerClient {
    @Resource
    UserSelectedStock userSelectedStock;
    @Resource
    UserDao userDao;

    @RequestMapping("/getUserSelectedStockClient")
    @ResponseBody
    public MsgInfo getUserSelectedStock(String userName,String password){
        User user = (User) userDao.findUserbyName(userName);

        if(null == user)
            return new MsgInfo(false,"用户未登录");

        if(!user.getPassword().equals(SHA256.encrypt(password)))
            return new MsgInfo(false,"密码错误,请重新登录");


        return userSelectedStock.getUserSelectedStock(user.getUserName());
    }

    @RequestMapping("/addUserSelectedStockClient")
    @ResponseBody
    public MsgInfo addUserSelectedStock(String userName,String password,String codeNum){
        User user = (User) userDao.findUserbyName(userName);
        if(null == user)
            return new MsgInfo(false,"用户未登录");

        if(!user.getPassword().equals(SHA256.encrypt(password)))
            return new MsgInfo(false,"密码错误,请重新登录");


        return userSelectedStock.addUserSelectedStock(user.getUserName(),codeNum);
    }

    @RequestMapping("/deleteUserSelectedStockClient")
    @ResponseBody
    public MsgInfo deleteUserSelectedStock(String userName,String password,String codeNum){
        User user = (User) userDao.findUserbyName(userName);
        if(null == user)
            return new MsgInfo(false,"用户未登录");

        if(!user.getPassword().equals(SHA256.encrypt(password)))
            return new MsgInfo(false,"密码错误,请重新登录");

        return userSelectedStock.deleteUserSelectedStock(user.getUserName(),codeNum);
    }
}
