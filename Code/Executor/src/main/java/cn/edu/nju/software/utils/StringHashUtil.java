package cn.edu.nju.software.utils;

import cn.edu.nju.software.dao.StockJsonDao;
import cn.edu.nju.software.vo.NowTimeSelectedStockInfoVO;
import cn.edu.nju.software.vo.StockInfoByPrice;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Jiayiwu on 8/14/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StringHashUtil {

    public static HashMap<String,String> string2hash(String param){

        if(null == param){
            return null;
        }

        String[] params =  param.split(" ");
        HashMap<String,String> hashMap = new HashMap<>();
        for(String tem:params){
            hashMap.put(tem,tem);
        }

        return hashMap;
    }

    public static String hash2string(HashMap<String,String> param){

        if( null == param)
            return null;

        Iterator iter = param.entrySet().iterator();

        String result="";
        while (iter.hasNext()){
            Map.Entry<String,String> entry = (Map.Entry<String,String>) iter.next();
            if(entry.getValue() != null){
                result+=entry.getValue()+" ";
            }

        }

        if(result.equals(""))
            return null;

        result = result.substring(0,result.length()-1);

        return result;
    }



}
