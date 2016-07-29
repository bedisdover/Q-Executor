package cn.edu.nju.software.controller;


import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.model.User;
import cn.edu.nju.software.service.UserService;
import cn.edu.nju.software.utils.SHA256;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * Created by Jiayiwu on 16/7/29.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Controller
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping("/register")
    @ResponseBody
    public MsgInfo register(HttpSession session,String userName, String nickName, String password, String mail){
      MsgInfo info = userService.register(userName,nickName,password,mail);
      session.setAttribute("user",(User)info.getObject());
       return info;
    }

    @RequestMapping("/login")
    @ResponseBody
    public MsgInfo login(HttpSession session,String userName, String password){
        MsgInfo info = userService.login(userName,password);
        session.setAttribute("user",(User)info.getObject());
        return info;
    }

    @RequestMapping("/findPassword")
    @ResponseBody
    public MsgInfo findPassword(HttpSession session,String userName){

        String num = new Random().nextInt(10000)+"";
        MsgInfo info = userService.findPassword(userName, num);
       if(true == info.isState()){
           session.setAttribute(SHA256.encrypt(num),userName);
       }
        return info;
    }

    @RequestMapping("/modifyPassword")
    @ResponseBody
    public MsgInfo modifyPassword(HttpSession session,String num,String password){

        if(null == num){
            return new MsgInfo(false,"链接错误");
        }else  if(null == password ){
            return  new MsgInfo(false,"密码错误");
        }

        String userName =(String) session.getAttribute(num);
        if(userName == null){
            return new MsgInfo(false,"该链接已经失效,请重新生成链接");
        }

        session.setAttribute(num,null);

       return userService.updatePassword(userName,SHA256.encrypt(password));

    }

    @RequestMapping("/isLogin")
    @ResponseBody
    public MsgInfo isLogin(HttpSession session){

        User tem =(User) session.getAttribute("user");
        if(null == tem ){
            return new MsgInfo(false,"请登录");
        }
        return new MsgInfo(true,"已登录",tem);
    }
}
