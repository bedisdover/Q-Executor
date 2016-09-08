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

    public static Image introduce;

    public static Image account_nav;

    public static Image account_content;

    public static ImageIcon login_btn;

    public static ImageIcon register_btn;

    public static ImageIcon findpw_btn;

    static {
        increase = new ImageIcon("src/main/resources/images/increase.png");
        decrease = new ImageIcon("src/main/resources/images/decrease.png");
        dull = new ImageIcon("src/main/resources/images/dull.png");
        login_btn = new ImageIcon("src/main/resources/images/login.png");
        register_btn = new ImageIcon("src/main/resources/images/register.png");
        findpw_btn = new ImageIcon("src/main/resources/images/findpw.png");
        introduce = new ImageIcon("src/main/resources/images/introduce.jpg").getImage();
        account_nav = new ImageIcon("src/main/resources/images/account_nav.jpg").getImage();
        account_content = new ImageIcon("src/main/resources/images/account_content.jpg").getImage();
    }
}
