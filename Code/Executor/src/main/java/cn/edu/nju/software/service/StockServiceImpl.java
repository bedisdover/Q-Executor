package cn.edu.nju.software.service;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.dao.StockInfoDao;
import cn.edu.nju.software.dao.StockJsonDao;
import cn.edu.nju.software.dao.StockNowTimeDao;
import cn.edu.nju.software.model.StockBasicInfo;
import cn.edu.nju.software.model.StockInfoByCom;
import cn.edu.nju.software.model.StockInfoByPer;

import cn.edu.nju.software.po.Stock5MInPO;
import cn.edu.nju.software.utils.JdbcUtil;
import cn.edu.nju.software.utils.StockUtil;
import cn.edu.nju.software.utils.StringUtil;
import cn.edu.nju.software.utils.TimeUtil;
import cn.edu.nju.software.vo.*;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private static final int POINTS_NUMS = 100;

    @Override
    public MsgInfo getStockNowTime(String Code) {

        try {
            return new MsgInfo(true, "成功", stockNowTimeDao.getNowTime(Code, getDate()));
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgInfo(false, "数据获取错误", null);
        }

    }

    @Override
    public List<StockKLineVO> getKLineByMinute(String codeNum, int interval) {
        int size = interval/5;
        int days = POINTS_NUMS/(48/size) + 2;

        List<Stock5MInPO> stock5MInPOs = new ArrayList<Stock5MInPO>();
        codeNum =  StockUtil.getCode(codeNum);
        int num = Integer.parseInt(codeNum.substring(2))%20;
        ArrayList<StockKLineVO> stockKLineVOs = new ArrayList<StockKLineVO>();
        String sql = "SELECT date,open,high,low,close FROM "+ StringUtil.HISTORY_5MIN_DATA+num+" WHERE CODE = \""+codeNum+"\" ORDER BY DATE DESC limit 0,"+days*48;

        try {
            Connection connection = JdbcUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Stock5MInPO stock5MInPO = new Stock5MInPO(resultSet.getTimestamp(1),resultSet.getDouble(2),resultSet.getDouble(3),resultSet.getDouble(4),resultSet.getDouble(5));
                stock5MInPOs.add(stock5MInPO);

                //stockForMLPOs.add(getStockForMLPO(resultSet));

            }

            Collections.reverse(stock5MInPOs);
//            for (Stock5MInPO po : stock5MInPOs){
//                System.out.println(po);
//            }
            int offset = 0 ;
            for (int i = 0 ; i < stock5MInPOs.size() ;i++){

                if (TimeUtil.getDateHHmmss(stock5MInPOs.get(i).getTime().getTime()).equals("09:35:00")){
                    offset = i;

                    break;
                }
            }
            stock5MInPOs = stock5MInPOs.subList(offset,stock5MInPOs.size());
            for (int i = 0 ; i < stock5MInPOs.size()/size ; i++){
                double open = stock5MInPOs.get(size*i).getOpen();
                double high = stock5MInPOs.get(size*i).getHigh();
                double low = stock5MInPOs.get(size*i).getLow();
                String date = TimeUtil.getDetailTime(new Date(stock5MInPOs.get(size*i+size-1).getTime().getTime()));
                double close = stock5MInPOs.get(size*i+size-1).getClose();
                for (int j = 0 ; j < size ; j++ ){
                    double highTemp = stock5MInPOs.get(size*i+j).getHigh();
                    double lowTemp = stock5MInPOs.get(size*i+j).getLow();
                    if (highTemp>high){
                        high = highTemp;
                    }
                    if (lowTemp<low){
                        low = lowTemp;
                    }
                }
                stockKLineVOs.add(new StockKLineVO(date,open,high,close,low));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return stockKLineVOs;
        }

        if (stockKLineVOs.size()<100){
            return stockKLineVOs;
        }
        return stockKLineVOs.subList(stockKLineVOs.size()-100,stockKLineVOs.size());

    }

    @Override
    public List<StockKLineVO> getKLineByDay(String Code) {
        List<StockKLineVO> result = stockJsonDao.getKLineByDay(Code);
        if (result.size()>POINTS_NUMS){
            return result.subList(result.size()-POINTS_NUMS,result.size());
        }
        return result;
    }

    @Override
    public List<StockKLineVO> getKLineByWeek(String Code) {
        List<StockKLineVO> result = stockJsonDao.getKLineByWeek(Code);
        if (result.size()>POINTS_NUMS){
            return result.subList(result.size()-POINTS_NUMS,result.size());
        }
        return result;
    }

    @Override
    public List<StockKLineVO> getKLineByMonth(String Code) {

        List<StockKLineVO> result = stockJsonDao.getKLineByMonth(Code);
        if (result.size()>POINTS_NUMS){
            return result.subList(result.size()-POINTS_NUMS,result.size());
        }
        return result;

    }


    @Override
    public List<StockInfoByPer> getPerStockInfo(String Code) {
        try {
            return stockInfoDao.getPerStockInfo(Code, getDate());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<StockInfoByCom> getComStockInfo(String Code) {
        try {
            return stockInfoDao.getComStockInfo(Code, getDate());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<StockInfoByCom> getComStockInfo(String Code, double param) {
        try {
            return stockInfoDao.getComStockInfo(Code, getDate(), param);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<StockInfoByPrice> getStockInfoByPrice(String Code) {
        try {
            double totalTrunover = 0;
            List<StockInfoByPer> list = stockInfoDao.getPerStockInfo(Code, getDate());
            HashMap<Double, Double> hash = new HashMap<Double, Double>();

            double temPrice = 0;
            double temTrunover = 0;
            for (StockInfoByPer per : list) {
                temPrice = per.getPrice();
                temTrunover = per.getVolume();

                totalTrunover += temTrunover;

                Double result = hash.get(temPrice);

                if (null == result) {
                    hash.put(temPrice, temTrunover);
                } else {
                    hash.put(temPrice, (temTrunover + result));
                }
            }

            if (totalTrunover == 0) {
                return null;
            }

            List<StockInfoByPrice> result = new ArrayList<StockInfoByPrice>();

            Iterator iter = hash.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry<Double, Double> entry = (Map.Entry<Double, Double>) iter.next();

                result.add(new StockInfoByPrice(entry.getKey(), entry.getValue(), round((entry.getValue() / totalTrunover), 3, BigDecimal.ROUND_CEILING)));
            }
            Collections.sort(result);
            return result;
        } catch (Exception e) {
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
        while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        List<StockInfoByPer> stockInfoByPers = stockInfoDao.getPerStockInfo(code, calendar.getTime());
        //当日的一分钟线
        long compare = TimeUtil.getMillisByHHmmss(stockInfoByPers.get(0).getTime()) / (1000 * 60) * 60000;
        String temp = TimeUtil.getDateHHmmss(compare);
        double volume = 0.0;
        double amount = 0.0;
        for (StockInfoByPer stockInfoByPer : stockInfoByPers) {
            long millis = TimeUtil.getMillisByHHmmss(stockInfoByPer.getTime());
            if (millis > compare)
                continue;
            else if (millis <= compare && millis > compare - 60000) {
                amount += stockInfoByPer.getTotalNum();
                volume += round(stockInfoByPer.getTotalNum() / stockInfoByPer.getPrice(), 2, BigDecimal.ROUND_HALF_UP);
            } else {

                double price = 0.0;
                if (volume != 0.0) {
                    price = round(amount / volume, 2, BigDecimal.ROUND_HALF_UP);
                }
                stockInfoByTimes.add(new StockInfoByTime(temp, price, volume));
                compare -= 60000;
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

    @Override
    public List<KDJVO> getKDJ(String codeNum) {
        //是9天  个人认为为了以后方便计算到底是多少天 先将9作为参数代入 将来也比较好修改
        return getKDJVOs(codeNum,9);
    }


    @Override
    public List<IndexVO> getRSI(String codeNum) {

        List<IndexVO> result = new ArrayList<IndexVO>();
        List<StockKLineVO> vos = stockJsonDao.getKLineByDay(codeNum);
        //12天的数据算出最后一天这样子
        if (vos.size()<12){
            return result;
        }

        result = getRSIList(vos,12);

        if (result.size()<POINTS_NUMS){
            return result;
        }

        return result.subList(result.size()-POINTS_NUMS,result.size());

    }

    @Override
    public List<IndexVO> getBIAS(String codeNum) {


        List<IndexVO> result = new ArrayList<IndexVO>();

        List<StockKLineVO> vos = stockJsonDao.getKLineByDay(codeNum);
        if (vos.size()<12){
            return result;
        }

        result = getBIASList(vos,12);

        if (result.size()<POINTS_NUMS){
            return result;
        }

        return result.subList(result.size()-POINTS_NUMS,result.size());
    }

    @Override
    public List<IndexVO> getMACD(String codeNum) {


        List<IndexVO> result = new ArrayList<IndexVO>();

        List<StockKLineVO> vos = stockJsonDao.getKLineByDay(codeNum);
        if (vos.size()<12){
            return result;
        }

        result = getMACDList(vos,12);

        if (result.size()<POINTS_NUMS){
            return result;
        }

        return result.subList(result.size()-POINTS_NUMS,result.size());
    }

    private List<IndexVO> getMACDList(List<StockKLineVO> vos,int days){
        List<IndexVO> result = new ArrayList<IndexVO>();
        return result;
    }


    //计算BIAS
    private List<IndexVO> getBIASList(List<StockKLineVO> vos, int days){
        List<IndexVO> result = new ArrayList<IndexVO>();

        for(int i = days-1 ; i < vos.size() ; i++){
            double closes = 0.0;
            for (int j = i-days+1 ; j <= i ;j++ ){
                closes+=vos.get(j).getClose();
            }
            double BIAS = (vos.get(i).getClose()/(closes/days) - 1) *100;
            result.add(new IndexVO(vos.get(i).getDate(),BIAS));
        }
        return result;
    }

    //计算rsi
    private List<IndexVO> getRSIList(List<StockKLineVO> vos,int days){
        List<IndexVO> result = new ArrayList<IndexVO>();
        for(int i = days ; i < vos.size(); i++){
            double increase = 0.0;
            double decrease = 0.0;
            int upDays = 0 ;
            int downDays = 0;
            for (int j = i - days + 1 ; j < i ; j++){
                double close1 = vos.get(j).getClose();
                double close2 = vos.get(j-1).getClose();
                if (close1>close2){
                    increase += (close1-close2);
                    upDays++;
                }else if (close1<close2){
                    decrease+=(close2-close1);
                    downDays++;
                }
            }
            if (upDays==0){
                result.add(new IndexVO(vos.get(i).getDate(),0));
            }else if (downDays==0){
                result.add(new IndexVO(vos.get(i).getDate(),100));
            }else {
                double RSI = (increase/upDays)/(increase/upDays + decrease/downDays) *100;
                result.add(new IndexVO(vos.get(i).getDate(),RSI));
            }
        }
        return result;
    }

    //获取肯德基指数的中间方法 用于裁剪 剩下POINT_NUMS个点方便画图
    private List<KDJVO> getKDJVOs(String codeNum,int days){
        List<KDJVO> result = new ArrayList<KDJVO>();
        //九天
        List<StockKLineVO> vos = stockJsonDao.getKLineByDay(codeNum);
        if (vos.size()<days-1){
            return result;
        }

        result = getKDJList(vos,days);

        if (result.size()<POINTS_NUMS){
            return result;
        }

        return result.subList(result.size()-POINTS_NUMS,result.size());
    }
    //独立出来根据多个K线数据进行计算KDJ
    private List<KDJVO> getKDJList(List<StockKLineVO> vos,int days){
        List<KDJVO> kdjvos = new ArrayList<KDJVO>();
        //百度说如果没有昨日的话就是默认的是 KD 分别为50
        double kTemp = 50;
        double dTemp = 50;
        double jTemp = 0.0;

        for (int i = 0 ; i < vos.size()-days ; i++){
            double close = vos.get(i+days-1).getClose();
            String date = vos.get(i+days-1).getDate();
            double high = vos.get(i).getHigh();
            double low = vos.get(i).getLow();
            //找到九日的最小 最高
            for (int j = i+1 ; j<i+days ;j++){
                double tempHigh = vos.get(j).getHigh();
                double tempLow = vos.get(j).getLow();
                if (tempHigh>high){
                    high = tempHigh;
                }

                if (tempLow<low){
                    low = tempLow;
                }
            }

            //计算RSV
            double RSV = ((close-low)/(high-low))*100;
            double K = 2.0/3.0 * kTemp  + 1.0/3.0 * RSV;
            double D = 2.0/3.0 * dTemp + 1.0/3.0 * K;
            double J = 3*K-2*D;

            kTemp = K;
            dTemp = D;
            kdjvos.add(new KDJVO(K,D,J,date));
        }

        return kdjvos;
    }
}
