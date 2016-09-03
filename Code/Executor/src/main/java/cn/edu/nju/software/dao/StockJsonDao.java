package cn.edu.nju.software.dao;

import cn.edu.nju.software.config.State;
import cn.edu.nju.software.config.StringMessage;
import cn.edu.nju.software.utils.JsonDataUtil;
import cn.edu.nju.software.utils.StockUtil;
import cn.edu.nju.software.utils.TimeUtil;
import cn.edu.nju.software.vo.HotStockVO;
import cn.edu.nju.software.vo.NowTimeSelectedStockInfoVO;
import cn.edu.nju.software.vo.StockKLineVO;
import org.hibernate.type.CalendarType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.PortableInterceptor.HOLDING;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jiayiwu on 8/11/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
@Repository
public class StockJsonDao {

    //获取JSONArray的一个标志
    private static final String RECORD = "record";

    public List<StockKLineVO> getKLineByDay(String stockID){
        String url = "http://api.finance.ifeng.com/akdaily/?code="+ StockUtil.getCode(stockID)+"&type=last";
        List<StockKLineVO> stockKLineVOs = new ArrayList<StockKLineVO>();
        String content = getContentFromURL(url);
        if (content==null){
            return stockKLineVOs;
        }
        exportData(stockKLineVOs,content);

        return stockKLineVOs;
    }

    public List<StockKLineVO> getKLineByWeek(String stockID){
        String url = "http://api.finance.ifeng.com/akweekly/?code="+ StockUtil.getCode(stockID)+"&type=last";
        List<StockKLineVO> stockKLineVOs = new ArrayList<StockKLineVO>();
        String content = getContentFromURL(url);
        if (content==null){
            return stockKLineVOs;
        }
        exportData(stockKLineVOs,content);
        return stockKLineVOs;
    }

    public List<StockKLineVO> getKLineByMonth(String stockID){
        String url = "http://api.finance.ifeng.com/akmonthly/?code="+ StockUtil.getCode(stockID)+"&type=last";
        List<StockKLineVO> stockKLineVOs = new ArrayList<StockKLineVO>();
        String content = getContentFromURL(url);
        if (content==null){
            return stockKLineVOs;
        }
        exportData(stockKLineVOs,content);
        return stockKLineVOs;
    }

    //解析JSON字符串到StockLineVOs中去
    private void exportData(List<StockKLineVO> stockKLineVOs , String content){
        try {
            JSONObject object = new JSONObject(content);
            JSONArray array = object.getJSONArray(RECORD);
            int index = 0 ;
            //如果大于100的话那么解析就不是从0开始
            if(array.length()>100){
                index = array.length()-100;
            }

            for(;index<array.length();index++){
                JSONArray data = array.getJSONArray(index);
                StockKLineVO vo = new StockKLineVO(data.getString(0),Double.parseDouble(data.getString(1).replaceAll(",","")),
                        Double.parseDouble(data.getString(2).replaceAll(",","")),Double.parseDouble(data.getString(3).replaceAll(",","")),
                        Double.parseDouble(data.getString(4).replaceAll(",","")),Double.parseDouble(data.getString(5).replaceAll(",","")),
                        Double.parseDouble(data.getString(6).replaceAll(",","")),Double.parseDouble(data.getString(7).replaceAll(",","")),
                        Double.parseDouble(data.getString(8).replaceAll(",","")),Double.parseDouble(data.getString(9).replaceAll(",","")),
                        Double.parseDouble(data.getString(10).replaceAll(",","")),Double.parseDouble(data.getString(11).replaceAll(",","")),
                        Double.parseDouble(data.getString(12).replaceAll(",","")),Double.parseDouble(data.getString(13).replaceAll(",","")),
                        Double.parseDouble(data.getString(14).replaceAll(",","")));
                stockKLineVOs.add(vo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

    }

    private String getContentFromURL(String url) {

        try {
            URL ur = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(),"GBK"));
            String line = null;
            line = reader.readLine();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public  NowTimeSelectedStockInfoVO getNowTimeStockInfo(String codeNum) {
        StringMessage stringMessage= JsonDataUtil.getNowTimeStockResult(codeNum);
        if(stringMessage.getResult()== State.OK){
            net.sf.json.JSONArray jsonArray= net.sf.json.JSONArray.fromObject(stringMessage.getData());
            net.sf.json.JSONObject jsonObject1=jsonArray.getJSONObject(0);
            net.sf.json.JSONObject jsonObject2=jsonObject1.getJSONObject("data");
            NowTimeSelectedStockInfoVO po=new NowTimeSelectedStockInfoVO(jsonObject2.getString("gid"),jsonObject2.getDouble("increPer"),jsonObject2.getDouble("increase"),
                    jsonObject2.getString("name"),jsonObject2.getDouble("todayStartPri"),jsonObject2.getDouble("yestodEndPri"),jsonObject2.getDouble("nowPri"),
                    jsonObject2.getDouble("todayMax"),jsonObject2.getDouble("todayMin"),jsonObject2.getString("date"),jsonObject2.getString("time"),
                    jsonObject2.getDouble("traNumber"),jsonObject2.getDouble("traAmount"));
            return  po;

        }
        return null;
    }

    public List<HotStockVO> getHotStocks(){
        List<HotStockVO> stockVOs = new ArrayList<HotStockVO>();

        String date = TimeUtil.getLastworkDate();
        String url = "http://data.eastmoney.com/DataCenter_V3/stock2016/TradeDetail/pagesize=200,page=1,sortRule=-1,sortType=,startDate="+date+",endDate="+date+",gpfw=0,js=vardata_tab_1.html";
        String content = getContentFromURL(url);
        if (content==null){
            return stockVOs;
        }
        try {
//            System.out.println(content);
//            JSONObject jsonObject = new JSONObject(content.substring(content));

            JSONArray array = new JSONArray(content.substring(content.indexOf("["),content.indexOf("]")+1));

            List<HotStockVO> stockVOList = getStockVOsByJson(array);
            Collections.sort(stockVOList);
            stockVOs = stockVOList.subList(0,15);

            url="http://hq.sinajs.cn/list=";
            for (HotStockVO vo:stockVOs){
                String info = getContentFromURL(url+StockUtil.getCode(vo.getCode()));
                try {
                    vo.setCurrentPrice(info.split(",")[3]);
                }catch (Exception e){
                    vo.setCurrentPrice("--");
                    continue;
                }
            }
            return stockVOs;

        } catch (JSONException e) {
            e.printStackTrace();
            return stockVOs;
        }
    }

    private List<HotStockVO> getStockVOsByJson(JSONArray array) throws JSONException {
        List<HotStockVO> result = new ArrayList<HotStockVO>();
        for (int i = 0 ; i < array.length() ;i++){

            JSONObject object = array.getJSONObject(i);
            HotStockVO vo = new HotStockVO(object.getString("SCode"),
                    object.getString("SName"),object.getDouble("Chgradio"),
                    object.getString("Ctypedes"),object.getString("Tdate"));

            if (result.contains(vo)){
                for(HotStockVO obj :result){
                    if (obj.equals(vo)){
                        obj.addReason(vo.getReason());
                    }
                }
            }else{
                result.add(vo);
            }
        }

        return result;
    }

}
