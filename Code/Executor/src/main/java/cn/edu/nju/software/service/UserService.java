package cn.edu.nju.software.service;

import cn.edu.nju.software.config.MsgInfo;
import org.springframework.stereotype.Service;

/**
 * Created by Jiayiwu on 16/7/29.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */

public interface UserService {

    public MsgInfo register(String userName,String nickName,String password,String mail);

    public MsgInfo login(String userName,String password);

    public MsgInfo findPassword(String userName,String randomNum);

    public MsgInfo updatePassword(String userName, String password);

}


