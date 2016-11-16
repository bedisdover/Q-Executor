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
            SendMailFactory.sendMail(tem.getMail(),tem.getUserName(),SHA256.encrypt(randomNum));
        }catch (Exception e){
            e.printStackTrace();
            return  new MsgInfo(false,"邮件发送失败");
        }

        tem.setRandomFindPassword(SHA256.encrypt(randomNum));
        userDao.updateUser(tem);

        String mail = tem.getMail();

        if(mail.length()>4){
            StringBuffer buffer = new StringBuffer(mail);
            buffer.replace(1, 3, "*");
            mail = buffer.toString();
        }

        return new MsgInfo(true,"邮件发送成功,请查看您的邮箱:"+mail);
    }

    @Override
    public MsgInfo modifyPassword(String userName, String findnum, String password) {
        if(null == findnum){
            return new MsgInfo(false,"链接已经失效,请重新生成链接");
        }else  if(null == password ){
            return  new MsgInfo(false,"请正确输入密码");
        }else  if (null == userName){
            return new MsgInfo(false,"链接已经失效,请重新生成链接");
        }
        User user = userDao.findUserbyName(userName);

        if(!findnum.equals(user.getRandomFindPassword()))
            return new MsgInfo(false,"链接已经失效,请重新生成链接");

        user.setRandomFindPassword(null);
        user.setPassword(password);
        return userDao.updateUser(user);
    }

    public MsgInfo updatePassword(String userName, String password) {

        User tem = userDao.findUserbyName(userName);
        if(null == tem)
            return new MsgInfo(false,"用户名不存在");
        try {
            tem.setPassword(SHA256.encrypt(password));
            userDao.updateUserPassword(tem);
        }catch (Exception e){
            e.printStackTrace();
            return  new MsgInfo(false,"修改密码错误");
        }
        return new MsgInfo(true,"修改密码成功");
    }
}
