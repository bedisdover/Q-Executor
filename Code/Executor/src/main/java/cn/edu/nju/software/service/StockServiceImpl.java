package cn.edu.nju.software.service;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.dao.StockNowTimeDao;
import cn.edu.nju.software.utils.TimeUtil;
import cn.edu.nju.software.vo.StockNowTimeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Jiayiwu on 8/9/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service
public class StockServiceImpl implements StockService {
    @Resource
    StockNowTimeDao stockNowTimeDao;
    @Override
    public MsgInfo getStockNowTime(String Code) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String sDate = TimeUtil.getNowDate();
            Date date = null;
            Calendar cal =  Calendar.getInstance();
            do{
            date = format.parse(sDate);
            cal.setTime(date);
             if(!(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)){
                 break;
             }
                sDate = TimeUtil.getPassedDate(1,sDate);
            }while (true);

            return new MsgInfo(true,"成功",stockNowTimeDao.getNowTime(Code,format.parse(sDate))) ;
        }catch (Exception e){
            e.printStackTrace();
            return new MsgInfo(false,"数据获取错误",null) ;
        }

    }
}
