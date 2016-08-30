package blservice;

import config.MsgInfo;

/**
 * Created by song on 16-8-26.
 * <p>
 * 用户账户相关接口, 包括登录、注册、找回密码
 */
public interface UserService {
    /**
     * 注册，对应url：http:121.42.143.164/register
     *
     * @param userName 用户名
     * @param nickName 昵称（可为空）
     * @param password 密码
     * @param mail     邮箱
     * @return MsgInfo 返回格式为Json格式,其结构为private boolean state;
     * private String info;
     * private Object object = null;
     * 其中state表示本次操作是否成功,info表示失败的信息
     * @throws Exception 
     */
    MsgInfo register(String userName, String nickName, String password, String mail) throws Exception;

    /**
     * 登录，对应url：http:121.42.143.164/login
     *
     * @param userName 用户名
     * @param password 密码
     * @return MsgInfo 返回格式为Json格式, 其结构为private boolean state;
     * private String info;
     * private Object object = null;
     * 其中state表示本次登录是否成功,info表示失败的信息
     * @throws Exception 
     */
    MsgInfo login(String userName, String password) throws Exception;

    /**
     * 判断用户是否登录, url: http:121.42.143.164/isLogin
     *
     * @param userName 用户名
     * @return MsgInfo
     * MsgInfo 返回格式为Json格式,其结构为private boolean state;
     * private String info;
     * private Object object = null;
     * 其中state表示用户是否登录,info返回状态信息,Object表示用户账号的详情的信息
     * @throws Exception 
     */
    MsgInfo isLogin(String userName) throws Exception;
}
