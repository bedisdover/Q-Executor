package util;

/**
 * Created by song on 16-8-28.
 * <p>
 * 数字工具类
 */
public class NumberUtil {

    /**
     * 转换单位
     *
     * @param number 数字
     * @param round  保留的小数位数
     * @return 转换后的数字
     * 示例：
     * 34234.12 ---> 3.42万
     */
    public static String transferUnit(double number, int round) {
        double temp = Math.pow(10, round);

        if (number >= 1e8) {
            return Math.round(number / 1e8 * temp) / temp + "亿";
        }

        if (number >= 1e4) {
            return Math.round(number / 1e4 * temp) / temp + "万";
        }

        return Math.round(number * temp) / temp + "";
    }

    /**
     * 转换单位，默认保留两位小数
     */
    public static String transferUnit(double number) {
        return transferUnit(number, 2);
    }

    /**
     * 默认保留两位小数
     */
    public static double round(double number) {
        return round(number, 2);
    }

    /**
     * 保留小数
     */
    public static double round(double number, int round) {
        double temp = Math.pow(10, round);
        return Math.round(number * temp) / temp;
    }

    /**
     * 获取小数的百分数形式, 默认保留两位小数
     * @return 0.00334 ---> 0.33%
     */
    public static String getPercent(double number) {
        return getPercent(number, 2);
    }

    public static String getPercent(double number, int round) {
        return round(number * 100, round) + "%";
    }
}
