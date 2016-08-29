package blservice;

import config.MsgInfo;

import java.util.List;

/**
 * Created by song on 16-8-28.
 * <p>
 * 自选股相关接口，包括添加、删除自选股及获取所有自选股
 */
public interface SelfSelectService {
    /**
     * 获取用户的自选股列表
     * url: /getUserSelectedStock
     * @throws Exception 
     */
    List<String> getUserSelectedStock() throws Exception;

    /**
     * 添加自选
     * url: /addUserSelectedStock
     * @throws Exception 
     */
    MsgInfo addUserSelectedStock(String codeNum) throws Exception;

    /**
     * 取消自选
     * url: /deleteUserSelectedStock
     * @throws Exception 
     */
    MsgInfo deleteUserSelectedStock(String codeNum) throws Exception;
}
