package cn.edu.nju.software.service;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.dao.StockInfoDao;
import cn.edu.nju.software.dao.StockJsonDao;
import cn.edu.nju.software.dao.StockNowTimeDao;
import cn.edu.nju.software.model.StockBasicInfo;
import cn.edu.nju.software.model.StockInfoByCom;
import cn.edu.nju.software.model.StockInfoByPer;
import cn.edu.nju.software.utils.TimeUtil;
import cn.edu.nju.software.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jiayiwu on 8/9/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Service
public class StockServiceImpl implements StockService {
    @Resource
    StockNowTimeDao stockNowTimeDao;
    @Resource
    StockInfoDao stockInfoDao;
    @Resource
    StockJsonDao stockJsonDao;
    @Override
    public MsgInfo getStockNowTime(String Code) {

        try{
            return new MsgInfo(true,"成功",stockNowTimeDao.getNowTime(Code,getDate())) ;
        }catch (Exception e){
            e.printStackTrace();
            return new MsgInfo(false,"数据获取错误",null) ;
        }

    }


    @Override
    public List<StockKLineVO> getKLineByDay(String Code) {
        return stockJsonDao.getKLineByDay(Code);
    }

    @Override
    public List<StockKLineVO> getKLineByWeek(String Code) {
        return stockJsonDao.getKLineByWeek(Code);
    }

    @Override
    public List<StockKLineVO> getKLineByMonth(String Code) {
        return stockJsonDao.getKLineByMonth(Code);
    }


    @Override
    public List<StockInfoByPer> getPerStockInfo(String Code) {
        try {
           return stockInfoDao.getPerStockInfo(Code,getDate());
        }catch (Exception e){
           e.printStackTrace();
            return null;
        }
    }



    @Override
    public List<StockInfoByCom> getComStockInfo(String Code) {
        try {
            return stockInfoDao.getComStockInfo(Code,getDate());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<StockInfoByCom> getComStockInfo(String Code, double param) {
        try {
            return stockInfoDao.getComStockInfo(Code,getDate(),param);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<StockInfoByPrice> getStockInfoByPrice(String Code) {
        try{
            double totalTrunover=0;
            List<StockInfoByPer> list = stockInfoDao.getPerStockInfo(Code,getDate());
            HashMap<Double,Double> hash = new HashMap<Double, Double>();

            double temPrice=0;
            double temTrunover=0;
            for(StockInfoByPer per:list){
                temPrice  = per.getPrice();
                temTrunover = per.getVolume();

                totalTrunover+=temTrunover;

                Double result = hash.get(temPrice);

                if(null == result){
                    hash.put(temPrice,temTrunover);
                }else {
                    hash.put(temPrice,(temTrunover+result));
                }
            }

            if(totalTrunover ==0){
                return null;
            }

            List<StockInfoByPrice> result = new ArrayList<StockInfoByPrice>();

            Iterator iter = hash.entrySet().iterator();

            while (iter.hasNext()){
                Map.Entry<Double,Double> entry = (Map.Entry<Double,Double>) iter.next();

                result.add(new StockInfoByPrice(entry.getKey(),entry.getValue(),round((entry.getValue()/totalTrunover),3,BigDecimal.ROUND_CEILING)));
            }
            Collections.sort(result);
            return  result;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public StockBasicInfo getStockBasicInfo(String Code) {
        return stockInfoDao.getBasicInfo(Code);
    }

    @Override
    public List<HotStockVO> getHotStocks() {

        return stockJsonDao.getHotStocks();
    }

    //---------------------------------------栋栋写这里-----------------------------------------------------------------------
    @Override
    public List<StockInfoByTime> getStockInfoByTime(String code) {
        List<StockInfoByTime> stockInfoByTimes = new ArrayList<StockInfoByTime>();
        //有疑问 date参数是不是作为传入参数
        //1.获取每笔交易
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
            calendar.add(Calendar.DATE,-1);
        }
        List<StockInfoByPer> stockInfoByPers = stockInfoDao.getPerStockInfo(code,calendar.getTime());
        //当日的一分钟线
        long compare = TimeUtil.getMillisByHHmmss(stockInfoByPers.get(0).getTime())/(1000*60) *60000;
        String temp = TimeUtil.getDateHHmmss(compare);
        double volume = 0.0;
        double amount = 0.0;
        for (StockInfoByPer stockInfoByPer : stockInfoByPers){
            long millis = TimeUtil.getMillisByHHmmss(stockInfoByPer.getTime());
            if(millis>compare)
                continue;
            else if (millis<=compare&&millis>compare-60000){
                amount+=stockInfoByPer.getTotalNum();
                volume+=round(stockInfoByPer.getTotalNum()/stockInfoByPer.getPrice(),2,BigDecimal.ROUND_HALF_UP);
            }else {

                double price = 0.0;
                if (volume !=0.0){
                    price = round(amount/volume,2,BigDecimal.ROUND_HALF_UP);
                }
                stockInfoByTimes.add(new StockInfoByTime(temp,price,volume));
                compare-=60000;
                temp = TimeUtil.getDateHHmmss(compare);
                volume = 0.0;
                amount = 0.0;
            }
        }
        return stockInfoByTimes;
    }
//---------------------------------------分割线-----------------------------------------------------------------------

    private Date getDate() throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            String sDate = TimeUtil.getNowDate();
            Date date = null;
            Calendar cal = Calendar.getInstance();
            do {
                date = format.parse(sDate);
                cal.setTime(date);
                if (!(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                    break;
                }
                sDate = TimeUtil.getPassedDate(1, sDate);
            } while (true);

            return format.parse(sDate);

    }
    @Override
    public List<DeepStockVO> getDeepStock(String Code) {
        return stockInfoDao.getDeepStockVo(Code);
    }

    private static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }


}
