package present.utils;

import javax.swing.*;
import java.awt.*;

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

    public static Image light;

    static {
        increase = new ImageIcon("images/increase.png");
        decrease = new ImageIcon("images/decrease.png");
        dull = new ImageIcon("images/dull.png");
        light = new ImageIcon("src/main/java/resources/images/light.png").getImage();
    }
}
