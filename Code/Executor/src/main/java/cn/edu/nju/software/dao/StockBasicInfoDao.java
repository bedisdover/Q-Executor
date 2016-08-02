package cn.edu.nju.software.dao;

import cn.edu.nju.software.model.StockBasicInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王栋 on 2016/8/2 0002.
 */

@Repository
public class StockBasicInfoDao {
    @Resource
    BaseDao baseDao;

    public List<StockBasicInfo> getAllStocksBasicInfo() {
        try{
        List<StockBasicInfo> basicInfos = (List<StockBasicInfo>) baseDao.getAll(StockBasicInfo.class);
            return basicInfos;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<StockBasicInfo>();
        }
    }
}
