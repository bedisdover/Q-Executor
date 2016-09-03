package present.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-3.
 * <p>
 * 颜色工具类
 */
public class ColorUtil {
    /**
     * "涨"
     */
    public static final Color INC_COLOR = Color.RED;
    /**
     * "跌"
     */
    public static final Color DEC_COLOR = new Color(0x1e8726);

    /**
     * "平"
     */
    public static final Color DULL_COLOR = Color.BLACK;

    /**
     * 根据涨跌幅获取文本颜色
     *
     * @param inc_dec 涨跌额
     * @return 涨---INC_COLOR,跌---DEC_COLOR
     */
    public static Color getTextColor(double inc_dec) {
        if (inc_dec > 0) {
            return INC_COLOR;
        } else if (inc_dec < 0) {
            return DEC_COLOR;
        } else {
            return DULL_COLOR;
        }
    }

    /**
     * 根据买卖盘性质获取文本颜色
     * @param type 性质
     * @return 买---INC_COLOR,卖---DEC_COLOR,中性---DULL_COLOR
     */
    public static Color getTextColor(String type) {
        switch (type) {
            case "买盘":
                return INC_COLOR;
            case "卖盘":
                return DEC_COLOR;
            default:
                return DULL_COLOR;
        }
    }

    public static ImageIcon getIcon(double inc_dec) {
        if (inc_dec > 0) {
            return ImageLoader.increase;
        } else if (inc_dec < 0) {
            return ImageLoader.decrease;
        } else {
            return ImageLoader.dull;
        }
    }
}
