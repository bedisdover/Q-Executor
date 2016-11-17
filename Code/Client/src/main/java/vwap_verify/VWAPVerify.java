package vwap_verify;

import bl.vwap.StockDataServiceImpl;
import bl.vwap.VWAP;
import bl.vwap.VWAP_Param;
import blservice.vwap.StockDataService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhangYF on 2016/11/16.
 */
public class VWAPVerify {

    private static final String stockList[] = { "600085","600352","601607",
                                                "600533","600563","600628",
                                                "600193","600523","600657",
                                                "600116","600165","600862"};

    private static final long userVolArray[] = {  1234,1234,1234,
                                            1234,1234,1234,
                                            1234,1234,1234,
                                            1234,1234,1234};

    public List<Long> getVWAPDayVol(String stockid,long userVol1,Date date){

        VWAP vwap = VWAP.getInstance();
        List<Long> volList = new ArrayList<>();
//        long userVol = userVol1;
//        for(int i=1;i<=48;i++){
//            VWAP_Param param = new VWAP_Param(userVol,stockid,1.0,i,1,48);
//            try {
//                long currentVol = vwap.getVWAPVol(param,date);
//                userVol = userVol - currentVol;
//                volList.add(currentVol);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        return volList;
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

        for(int i = 0;i<stockList.length;i++){
            String out = "";
            stockid = stockList[i];
            out += stockid+"  ";
            Date date = new Date();
            //TODO
            out += date.toString()+":";
            userVol = userVolArray[i];
            List<Long> vwapVolList = getVWAPDayVol(stockid,userVol,date);
            List<Long> uniformVolList = getUniformDayVol(userVol);
            List<Long> actualVolList = getActualDayVol(stockid,date);

            List<Double> priceList = getPriceList(stockid,date);

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
        double sumPrice = 0;
        double avgPrice;
        long volsum = 0;
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
