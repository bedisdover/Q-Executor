package present.utils;

import javax.swing.*;

/**
 * Created by song on 16-8-26.
 * <p>
 * 图片加载类
 */
public class ImageLoader {
    /**
     * 涨、跌、停
     */
    public static ImageIcon increase, decrease, dull;

    static {
        increase = new ImageIcon("src/main/resources/images/increase.png");
        decrease = new ImageIcon("src/main/resources/images/decrease.png");
        dull = new ImageIcon("src/main/resources/images/dull.png");
    }
}
