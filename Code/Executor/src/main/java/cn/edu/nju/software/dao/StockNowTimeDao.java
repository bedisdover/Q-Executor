package cn.edu.nju.software.dao;

import cn.edu.nju.software.vo.StockNowTimeVO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Jiayiwu on 8/9/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */

@Repository
public class StockNowTimeDao {
    @Resource
    BaseDao baseDao;

    public List<StockNowTimeVO> getNowTime(String codeNum, Date date){
        return null;
    }
}
