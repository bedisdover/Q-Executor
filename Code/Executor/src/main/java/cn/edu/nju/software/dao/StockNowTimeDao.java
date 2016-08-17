package cn.edu.nju.software.dao;

import cn.edu.nju.software.utils.StockUtil;
import cn.edu.nju.software.utils.StringUtil;
import cn.edu.nju.software.utils.TimeUtil;
import cn.edu.nju.software.vo.StockNowTimeVO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
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
        List<StockNowTimeVO> stockNowTimeVOs = new ArrayList<StockNowTimeVO>();
        codeNum = StockUtil.getCode(codeNum);//怕只传进来一个六位数代码 当然8位数最好
        int hashcode = getHashNum(codeNum.substring(2));
        //
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);
        //
        List<Object[]> datas = baseDao.execSqlQuery("select "+ StringUtil.CURRENT+","+StringUtil.TIME+" from "+StringUtil.STOCKS_INSTANCE+hashcode+" where "+StringUtil.CODE+
        "=\""+codeNum+"\" and "+StringUtil.TIME+">"+date.getTime()+" and "+StringUtil.TIME+"<"+calendar.getTime().getTime()+" order by "+StringUtil.TIME);

        //如果没有数据 获取前一天的返回
        if (datas.size()==0){
            calendar.setTime(date);
            calendar.add(Calendar.DATE,-1);
            return getNowTime(codeNum,calendar.getTime());
        }

        double avg_temp = 0.0;
        for (Object[] object : datas){
            double current = Double.parseDouble(object[0].toString());
            Date time = new Date(Long.parseLong(object[1].toString()));
//            if(stockNowTimeVOs.size()==0){
//                avg_temp = current;
//            }
//
            avg_temp = (avg_temp*stockNowTimeVOs.size()+current)/(stockNowTimeVOs.size()+1);
            stockNowTimeVOs.add(new StockNowTimeVO(TimeUtil.getDetailTime(time),(double) (Math.round(current*100))/100,(double) (Math.round(avg_temp*100))/100));

        }

        return stockNowTimeVOs;

    }



    public int getHashNum(String code){
        int result = Integer.parseInt(code);
        return  result%25;
    }
}
