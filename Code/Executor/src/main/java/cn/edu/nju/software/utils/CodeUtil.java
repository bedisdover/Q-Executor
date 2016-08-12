package cn.edu.nju.software.utils;

/**
 * Created by 王栋 on 2016/8/12 0012.
 */
public class CodeUtil {
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
