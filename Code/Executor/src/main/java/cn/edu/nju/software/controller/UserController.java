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


    /**
     *
     * @param session (前端不用传递)
     * @param userName (用户名)
     * @param nickName (昵称)
     * @param password (密码)
     * @param mail (邮件)
     * @return MsgInfo
     *         MsgInfo 返回格式为Json格式,其结构为private boolean state;
     *                                          private String info;
     *                                         private Object object = null;
     *         其中state表示本次操作是否成功,info表示失败的信息
     */
    @RequestMapping("/register")
    @ResponseBody
    public MsgInfo register(HttpSession session, String userName, String nickName, String password, String mail) {
        MsgInfo info = userService.register(userName, nickName, password, mail);
        session.setAttribute("user", (User) info.getObject());
        return info;
    }

    /**
     *
     * @param session
     * @param userName (用户名)
     * @param password (密码)
     * @return MsgInfo
     *         MsgInfo 返回格式为Json格式,其结构为private boolean state;
     *                                          private String info;
     *                                         private Object object = null;
     *         其中state表示本次登录是否成功,info表示失败的信息
     */

    @RequestMapping("/login")
    @ResponseBody
    public MsgInfo login(HttpSession session,String userName, String password){
        MsgInfo info = userService.login(userName,password);
        session.setAttribute("user",(User)info.getObject());
        return info;
    }

    /**
     *
     * @param session
     * @param userName (用户名)
     * @return MsgInfo
     *         MsgInfo 返回格式为Json格式,其结构为private boolean state;
     *                                          private String info;
     *                                         private Object object = null;
     *         其中state表示本次找回密码是否成功,info表示邮件发送失败的信息
     */
    @RequestMapping("/findPassword")
    @ResponseBody
    public MsgInfo findPassword(HttpSession session,String userName){

        String num = new Random().nextInt(10000)+"";
        MsgInfo info = userService.findPassword(userName, num);
       if(true == info.isState()){
           session.setAttribute("userName",userName);
           session.setAttribute(SHA256.encrypt(num),userName);
       }
        return info;
    }

    /**
     *
     * @param session
     * @return MsgInfo
     *         MsgInfo 返回格式为Json格式,其结构为private boolean state;
     *                                          private String info;
     *                                         private Object object = null;
     *         其中state表示本次重新发送邮件是否成功,info表示失败的信息
     */
    @RequestMapping("/repeatMail")
    @ResponseBody
    public MsgInfo repeatMail(HttpSession session){
        String userName = (String) session.getAttribute("userName");
        String num = new Random().nextInt(10000)+"";
        MsgInfo info = userService.findPassword(userName, num);
        if(true == info.isState()){
            session.setAttribute(SHA256.encrypt(num),userName);
        }
        return info;
    }

    /**
     *
     * @param userName
     * @param num (邮件里面所带的URL后面跟的参数,前端捕获后需要传递过来)
     * @param password(用户所需要修改的新密码)
     * @return  MsgInfo
     *         MsgInfo 返回格式为Json格式,其结构为private boolean state;
     *                                          private String info;
     *                                         private Object object = null;
     *         其中state表示本次修改密码是否成功,info表示失败的信息
     */
    @RequestMapping("/modifyPassword")
    @ResponseBody
    public MsgInfo modifyPassword(String userName,String num,String password){



       return userService.modifyPassword(userName,num,SHA256.encrypt(password));

    }

    @RequestMapping("/logout")
    @ResponseBody
    public MsgInfo logout(HttpSession session){


        session.setAttribute("user",null);
        return new MsgInfo(true,"成功登录");
    }


    /**
     *
     * @param session
     * @return  MsgInfo
     *         MsgInfo 返回格式为Json格式,其结构为private boolean state;
     *                                          private String info;
     *                                         private Object object = null;
     *         其中state表示用户是否登录,info返回状态信息,Obejct表示用户账号的详情的信息
     */


    @RequestMapping("/isLogin")
    @ResponseBody
    public MsgInfo isLogin(HttpSession session){

        User tem =(User) session.getAttribute("user");
        if(null == tem ){
            return new MsgInfo(false,"请登录");
        }
        return new MsgInfo(true,"已登录",tem);
    }

    // TODO 添加logout方法
    // TODO: 16-9-13  发送邮件后仍可使用原密码登录
}
