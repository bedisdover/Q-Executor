import cn.edu.nju.software.dao.UserDao;
import cn.edu.nju.software.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Jiayiwu on 16/8/2.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class test  {

    @Resource
    UserDao userDao;
    @Test
    public void myTest(){
//
        User user =userDao.findUserbyName("bestwujiayi");
        System.out.println(user.getNickName());
//        System.out.println("hh");
    }


}
