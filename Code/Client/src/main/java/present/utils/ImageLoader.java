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

    public static Image search_bg;

    public static Image empty_time_series;

    public static Image empty_msg;

    public static ImageIcon login_btn;

    public static ImageIcon register_btn;

    public static ImageIcon findpw_btn;

    public static ImageIcon questionnair_btn;

    public static ImageIcon question1;

    public static ImageIcon question2;

    public static ImageIcon question3;

    public static ImageIcon question4_1;

    public static ImageIcon question4_2;

    public static ImageIcon question5;

    public static ImageIcon question6_1;

    public static ImageIcon question6_2;

    public static ImageIcon question7_1;

    public static ImageIcon question7_2;

    static {
        increase = new ImageIcon("src/main/resources/images/increase.png");
        decrease = new ImageIcon("src/main/resources/images/decrease.png");
        dull = new ImageIcon("src/main/resources/images/dull.png");
        login_btn = new ImageIcon("src/main/resources/images/login.png");
        register_btn = new ImageIcon("src/main/resources/images/register.png");
        findpw_btn = new ImageIcon("src/main/resources/images/findpw.png");
        questionnair_btn = new ImageIcon("src/main/resources/images/questionnair.png");
        introduce = new ImageIcon("src/main/resources/images/introduce.jpg").getImage();
        account_nav = new ImageIcon("src/main/resources/images/account_nav.jpg").getImage();
        account_content = new ImageIcon("src/main/resources/images/account_content.jpg").getImage();
        search_bg = new ImageIcon("src/main/resources/images/search_bg.jpg").getImage();
        empty_time_series = new ImageIcon("src/main/resources/images/empty_time_series.jpg").getImage();
        empty_msg = new ImageIcon("src/main/resources/images/empty_strategy.jpg").getImage();

        question1 = new ImageIcon("src/main/resources/images/question1.png");
        question2 = new ImageIcon("src/main/resources/images/question2.png");
        question3 = new ImageIcon("src/main/resources/images/question3.png");
        question4_1 = new ImageIcon("src/main/resources/images/question4-1.png");
        question4_2 = new ImageIcon("src/main/resources/images/question4-2.png");
        question5 = new ImageIcon("src/main/resources/images/question5.png");
        question6_1 = new ImageIcon("src/main/resources/images/question6-1.png");
        question6_2 = new ImageIcon("src/main/resources/images/question6-2.png");
        question7_1 = new ImageIcon("src/main/resources/images/question7-1.png");
        question7_2 = new ImageIcon("src/main/resources/images/question7-2.png");
    }
}
