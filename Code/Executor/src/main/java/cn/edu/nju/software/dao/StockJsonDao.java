package cn.edu.nju.software.dao;

import cn.edu.nju.software.vo.StockKLineVO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
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
        String url = "http://api.finance.ifeng.com/akdaily/?code="+getCode(stockID)+"&type=last";
        List<StockKLineVO> stockKLineVOs = new ArrayList<StockKLineVO>();
        String content = getContentFromURL(url);
        if (content==null){
            return stockKLineVOs;
        }
        exportData(stockKLineVOs,content);

        return stockKLineVOs;
    }

    public List<StockKLineVO> getKLineByWeek(String stockID){
        String url = "http://api.finance.ifeng.com/akweekly/?code="+getCode(stockID)+"&type=last";
        List<StockKLineVO> stockKLineVOs = new ArrayList<StockKLineVO>();
        String content = getContentFromURL(url);
        if (content==null){
            return stockKLineVOs;
        }
        exportData(stockKLineVOs,content);
        return stockKLineVOs;
    }

    public List<StockKLineVO> getKLineByMonth(String stockID){
        String url = "http://api.finance.ifeng.com/akmonthly/?code="+getCode(stockID)+"&type=last";
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
                        Double.parseDouble(data.getString(12).replaceAll(",","")),Double.parseDouble(data.getString(13).replaceAll(",","")));
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream()));
            String line = null;
            line = reader.readLine();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getCode(String code){
        if(code.length()==8){
            return code;
        }

        if (code.length()==6){
            if (code.charAt(0)=='6'){
                code="sh"+code;
            }else {
                code = "sz"+code;
            }
        }
        return code;
    }
}
