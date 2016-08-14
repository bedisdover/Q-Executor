package cn.edu.nju.software.service;

import cn.edu.nju.software.config.MsgInfo;

/**
 * Created by Jiayiwu on 8/14/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public interface UserSelectedStock {
    public MsgInfo getUserSelectedStock(String userName);

    public MsgInfo addUserSelectedStock(String userName,String codeNum);

    public MsgInfo deleteUserSelectedStock(String userName,String codeNum);
}
