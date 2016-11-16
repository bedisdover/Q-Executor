package vwap_verify;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public boolean setTime(String stockid,Date date){

        return false;
    }

    public int getCurVol(String stockid,int i){

        return 0;
    }
    public List<Integer> getVWAPDayVol(String stockid,Date date){

        setTime(stockid,date);

        return null;
    }
    public List<Integer> getActualDayVol(String stockid,Date date){

        setTime(stockid,date);

        return null;
    }
    public List<Integer> getUniformDayVol(String stockid, Date date){

        setTime(stockid,date);

        return null;
    }

    private List<Double> getPriceList(String stockid, Date date){

        return null;
    }
    public void process(){
        String stockid;

        for(int i = 0;i<stockList.length;i++){
            String out = "";
            stockid = stockList[i];
            out += stockid+"  ";
            Date date = new Date();
            //TODO
            out += date.toString()+":";
            List<Integer> vwapVolList = getVWAPDayVol(stockid,date);
            List<Integer> uniformVolList = getUniformDayVol(stockid,date);
            List<Integer> actualVolList = getActualDayVol(stockid,date);

            List<Double> priceList = getPriceList(stockid,date);

            double vwapAvgPrice = getAvgPrice(vwapVolList,priceList);
            double uniformAvgPrice = getAvgPrice(uniformVolList,priceList);
            double actualAvgPrice = getAvgPrice(actualVolList,priceList);

            double vwapBP = (vwapAvgPrice - actualAvgPrice)*10000.0/actualAvgPrice;
            double uniformBP = (uniformAvgPrice - actualAvgPrice)*10000.0/actualAvgPrice;
            out += vwapAvgPrice+" "+uniformAvgPrice+" "+actualAvgPrice+"  "+vwapBP+" "+uniformBP;
            savefile(out);
        }
    }
    private double getAvgPrice(List<Integer> vol,List<Double> price){
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
