package cn.edu.nju.software.service;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.dao.StockBasicInfoDao;
import cn.edu.nju.software.dao.StockJsonDao;
import cn.edu.nju.software.dao.UserDao;
import cn.edu.nju.software.model.StockBasicInfo;
import cn.edu.nju.software.model.User;
import cn.edu.nju.software.utils.StringHashUtil;
import cn.edu.nju.software.vo.NowTimeSelectedStockInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Jiayiwu on 8/14/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */

@Service
public class UserSelectedStockImpl implements UserSelectedStock {

    @Resource
    UserDao userDao;

    @Resource
    StockJsonDao stockJsonDao;

    @Resource
    StockBasicInfoDao stockBasicInfoDao;

    @Override
    public MsgInfo getUserSelectedStock(String userName) {

        User user = userDao.findUserbyName(userName);

        HashMap<String,String> hash = StringHashUtil.string2hash(user.getSelect_stockNum());



        List<NowTimeSelectedStockInfoVO> list = hash2list(hash);


        if(list.size() == 0)
            return new MsgInfo(false,"还未选择任何股票",list);

        return new MsgInfo(true,"成功",list);
    }

    @Override
    public MsgInfo addUserSelectedStock(String userName,String codeNum) {

        User user = userDao.findUserbyName(userName);


        List<NowTimeSelectedStockInfoVO> list = new ArrayList<>();
        HashMap<String,String> hash = StringHashUtil.string2hash(user.getSelect_stockNum());

        if(hash == null){
            user.setSelect_stockNum(codeNum);
            userDao.updateUser(user);
            return  new MsgInfo(true,"添加成功",list.add(stockJsonDao.getNowTimeStockInfo(codeNum)));
        }

        String value = hash.get(codeNum);

        if(value != null){
            return new MsgInfo(false,"该股票已经存在自选股票列表中",hash2list(hash));
        }
        hash.put(codeNum,codeNum);

        user.setSelect_stockNum(StringHashUtil.hash2string(hash));

        userDao.updateUser(user);

        return new MsgInfo(true,"添加成功",hash2list(hash));
    }

    @Override
    public MsgInfo deleteUserSelectedStock(String userName,String codeNum) {

        User user = userDao.findUserbyName(userName);


        List<NowTimeSelectedStockInfoVO> list = new ArrayList<>();
        HashMap<String,String> hash = StringHashUtil.string2hash(user.getSelect_stockNum());

        if(hash == null ){
            return new MsgInfo(false,"股票列表中无任何数据,无法删除");
        }else if(hash.get(codeNum) == null){
            return new MsgInfo(false,"股票列表中无该股票",hash2list(hash));
        }

        hash.put(codeNum,null);

        user.setSelect_stockNum(StringHashUtil.hash2string(hash));
        userDao.updateUser(user);

        return new MsgInfo(true,"删除成功",hash2list(hash));
    }

    private  List<NowTimeSelectedStockInfoVO> hash2list(HashMap<String,String> param){


        List<NowTimeSelectedStockInfoVO> list = new ArrayList<>();

        if(null == param){
            return list;
        }

        Iterator iter = param.entrySet().iterator();
//        while (iter.hasNext()){
//            Map.Entry<String,String> entry = (Map.Entry<String,String>) iter.next();
//            if(entry.getValue() != null) {
//                list.add(stockJsonDao.getNowTimeStockInfo(entry.getValue()));
//            }
//        }

        while (iter.hasNext()){
            Map.Entry<String,String> entry = (Map.Entry<String,String>) iter.next();
            if(entry.getValue() != null) {
                NowTimeSelectedStockInfoVO nowTimeSelectedStockInfoVO = stockJsonDao.getNowTimeStockInfo(entry.getValue());
                StockBasicInfo stockBasicInfo = stockBasicInfoDao.getStockBasicInfo(nowTimeSelectedStockInfoVO.getGid());
                nowTimeSelectedStockInfoVO.setName(stockBasicInfo.getName());
                list.add(nowTimeSelectedStockInfoVO);
            }
        }


        return list;
    }
}
