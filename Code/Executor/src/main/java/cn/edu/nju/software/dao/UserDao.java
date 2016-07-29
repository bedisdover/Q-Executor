package cn.edu.nju.software.dao;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.model.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Jiayiwu on 16/7/29.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Repository
public class UserDao {
    @Resource
    BaseDao baseDao;

        public User findUser(String userName){
               List<User> users = ( List<User>)baseDao.findByProperty(User.class,"userName",userName);
            if(null!=users&&users.size()>=1)
                return users.get(0);
            else
                return null;
        }

    public MsgInfo saveUser(User user){


        try {
            baseDao.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return new MsgInfo(false,"用户数据保存异常");
        }

        return new MsgInfo(true,"用户保存成功",user);

    }

    public MsgInfo updateUser(User user){

        User tem = findUser(user.getUserName());

        if(null == tem)
            return new MsgInfo(false,"该用户不存在");
        try{
            tem.setPassword(user.getPassword());
            baseDao.update(tem);
        }catch (Exception e){
            e.printStackTrace();
            return new MsgInfo(false,"无法修改密码");
        }

        return new MsgInfo(true,"密码修改成功",tem);

    }

}
