package cn.edu.nju.software.dao;

import cn.edu.nju.software.model.StockBasicInfo;
import cn.edu.nju.software.model.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Created by 王栋 on 2016/8/2 0002.
 */
@Repository
public class test {
    @Resource
    private static UserDao userDao;
    public static void main(String[] args){
//        StockBasicInfoDao stockBasicInfoDao = new StockBasicInfoDao();
//        List stocks = stockBasicInfoDao.getAllStocksBasicInfo();
//        for(Object stock :stocks){
//            System.out.println(stock.toString());
//        }

        User user = userDao.findUserbyName("bestwujiayi");
        System.out.println(user.getNickName());
    }
}
