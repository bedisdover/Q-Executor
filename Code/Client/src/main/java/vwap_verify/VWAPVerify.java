package vwap_verify;

import bl.vwap.StockDataServiceImpl;
import bl.vwap.VWAP;
import bl.vwap.VWAP_Param;
import blservice.vwap.StockDataService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhangYF on 2016/11/16.
 */
public class VWAPVerify {

    private static final String stockList[] = { "sh600085","sh600352","sh601607",
                                                "sh600533","sh600563",
                                                "sh600193","sh600523",
                                                "sh600116","sh600862"};

    private static final long userVolArray[] = {  1816086,8248370,2980771,
                                            3538053,489700,
                                            1077303,274673,
                                            1130674,1706140};

    public List<Long> getVWAPDayVol(String stockid,long userVol1,Date date){

        VWAP vwap = VWAP.getInstance();
        List<Long> volList = new ArrayList<>();
        long userVol = userVol1;
        for(int i=1;i<=48;i++){
            VWAP_Param param = new VWAP_Param(userVol,stockid,1.0,i,1,48);
            try {
                long currentVol = vwap.getVWAPVol(param,date);
                userVol = userVol - currentVol;
                volList.add(currentVol);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return volList;

//        List<Long> result = new ArrayList<>();
//        long vol = userVol1/48;
//        long sum = 0;
//        for(int i = 0;i<47;i++){
//            result.add(vol);
//            sum += vol;
//        }
//        result.add(userVol1-sum);
//
//        return result;
    }
    public List<Long> getActualDayVol(String stockid,Date date){
        StockDataService stockData = new StockDataServiceImpl();

        return stockData.getVolList(stockid,date);
    }
    public List<Long> getUniformDayVol(long userVol){
        List<Long> result = new ArrayList<>();
        long vol = userVol/48;
        long sum = 0;
        for(int i = 0;i<47;i++){
            result.add(vol);
            sum += vol;
        }
        result.add(userVol-sum);

        return result;
    }

    private List<Double> getPriceList(String stockid, Date date){
        StockDataService stockData =  new StockDataServiceImpl();
        return stockData.getPriceList(stockid,date);
    }
    public void process(){
        String stockid;
        long userVol;

        int num = stockList.length;
//        num = 2;
        for(int i = 0;i<num;i++){
            String out = "";
            stockid = stockList[i];
            out += stockid+"  ";
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat1.parse("2015-06-29");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            out += date.toString()+":";
            userVol = userVolArray[i];
            List<Long> vwapVolList = getVWAPDayVol(stockid,userVol,date);
            List<Long> uniformVolList = getUniformDayVol(userVol);
            List<Long> actualVolList = getActualDayVol(stockid,date);

            System.out.println(vwapVolList);
            System.out.println(uniformVolList);
            System.out.println(actualVolList);

            List<Double> priceList = getPriceList(stockid,date);

            if(vwapVolList == null || vwapVolList.size() == 0){
                continue;
            }
            if(uniformVolList == null || uniformVolList.size() == 0){
                continue;
            }
            if(actualVolList == null || actualVolList.size() == 0){
                continue;
            }if(priceList == null || priceList.size() == 0){
                continue;
            }
            double vwapAvgPrice = getAvgPrice(vwapVolList,priceList);
            double uniformAvgPrice = getAvgPrice(uniformVolList,priceList);
            double actualAvgPrice = getAvgPrice(actualVolList,priceList);

            double vwapBP = (vwapAvgPrice - actualAvgPrice)*10000.0/actualAvgPrice;
            double uniformBP = (uniformAvgPrice - actualAvgPrice)*10000.0/actualAvgPrice;
            out += vwapAvgPrice+" "+uniformAvgPrice+" "+actualAvgPrice+"  "+vwapBP+" "+uniformBP;
            System.out.println(out);
            savefile(out);
        }
    }
    private double getAvgPrice(List<Long> vol,List<Double> price){
        if(vol.size() == 0 || price.size() == 0){
            return  0;
        }
        double sumPrice = 0;
        double avgPrice;
        long volsum = 0;
        System.out.println(vol.size()+"  "+price.size());
        for (int i = 0; i < vol.size(); i++) {
            sumPrice += vol.get(i)*price.get(i);
            volsum += vol.get(i);
        }
        avgPrice = sumPrice/volsum;
        return avgPrice;
    }
    private void savefile(String out){
        String path = "verifydata.txt";
        FileWriter fw = null;
        try {
            fw = new FileWriter(path,true);

        PrintWriter pw = new PrintWriter(fw);
        pw.println(out);
        pw.close();
        fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        VWAPVerify verify = new VWAPVerify();
        String str1 = "test1";
        verify.savefile(str1);
        String str2 = "test2";
        verify.savefile(str2);

    }
}
