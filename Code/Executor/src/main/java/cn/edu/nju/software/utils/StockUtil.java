package cn.edu.nju.software.utils;

/**
 * Created by 王栋 on 8/14/16.
 * Modify By JiayiWu
 * Reason:Recover File
 */
public class StockUtil {
    public static String getCode(String code){
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
