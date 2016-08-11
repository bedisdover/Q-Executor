package cn.edu.nju.software.dao;

import cn.edu.nju.software.vo.StockKLineVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jiayiwu on 8/11/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Repository
public class StockJsonDao {
    public List<StockKLineVO> getKLineByDay(String stockID){
    return null;
    }

    public List<StockKLineVO> getKLineByWeek(String stockID){
        return null;
    }

    public List<StockKLineVO> getKLineByMonth(String stockID){
        return null;
    }
}
