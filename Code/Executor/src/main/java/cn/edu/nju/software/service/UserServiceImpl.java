package cn.edu.nju.software.service;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.dao.UserDao;
import cn.edu.nju.software.model.User;
import cn.edu.nju.software.utils.Mail.SendMailFactory;
import cn.edu.nju.software.utils.SHA256;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * Created by Jiayiwu on 16/7/29.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service
public class UserServiceImpl implements  UserService {

    @Resource
    UserDao userDao;

    public MsgInfo register(String userName, String nickName, String password, String mail) {
        User tem = userDao.findUserbyName(userName);
        if(null != tem){
            return  new MsgInfo(false,"用户名已经被注册");
        }

        return userDao.saveUser(new User(userName,nickName,SHA256.encrypt(password),mail));
    }

    public MsgInfo login(String userName, String password) {
        User tem = userDao.findUserbyName(userName);
        if(tem ==null){
            tem = userDao.findUserbymail(userName);
            if(null == tem){
                return new MsgInfo(false,"您输入的账号或密码有误");
            }else if(tem.getPassword().equals(SHA256.encrypt(password))) {
                return new MsgInfo(true,"登录成功",tem);
            }
        }else if(tem.getPassword().equals(SHA256.encrypt(password))){
            return new MsgInfo(true,"登录成功",tem);
        }
        return new MsgInfo(false,"登录失败");
    }

    public MsgInfo findPassword(String userName,String randomNum) {
        Random ra =new Random();
        User tem = userDao.findUserbyName(userName);
        if(null == tem){
            return new MsgInfo(false,"用户名不存在");
        }

        try{
            SendMailFactory.sendMail(tem.getMail(),SHA256.encrypt(randomNum));
        }catch (Exception e){
            e.printStackTrace();
            return  new MsgInfo(false,"邮件发送失败");
        }

        return new MsgInfo(true,"邮件发送成功");
    }

    public MsgInfo updatePassword(String userName, String password) {

        User tem = userDao.findUserbyName(userName);
        if(null == tem)
            return new MsgInfo(false,"用户名不存在");
        try {
            tem.setPassword(SHA256.encrypt(password));
            userDao.updateUser(tem);
        }catch (Exception e){
            e.printStackTrace();
            return  new MsgInfo(false,"修改密码错误");
        }
        return new MsgInfo(true,"修改密码成功");
    }
}
