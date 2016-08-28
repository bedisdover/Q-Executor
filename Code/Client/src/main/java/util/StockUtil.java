package util;

/**
 * Created by 王栋 on 2016/8/12 0012.
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

    public static int getType(String type){
        if(type.equals("买盘"))
            return 0;
        if (type.equals("卖盘"))
            return 1;
        else
            return 2;
    }

    public static double getChangePrice(String changePrice){
        if(changePrice.equals("--")){
            return 0.0;
        }
        else return Double.parseDouble(changePrice);
    }
}
