package present.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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

    public static Image empty_result;

    public static Image timer;

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

    public static Image pre_enter;

    public static Image pre_exit;

    public static Image next_enter;

    public static Image next_exit;

    public static Image baseBar;

    /**
     * 进度条三部分
     */
    public static Image progressBar_head, progressBar_body, progressBar_tail;

    public static Image hotTip;

    public static Image selfTip;

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
        empty_result = new ImageIcon("src/main/resources/images/empty_strategy.jpg").getImage();
        timer = new ImageIcon("src/main/resources/images/timer.png").getImage();

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

        pre_enter = new ImageIcon("src/main/resources/images/pre_enter.png").getImage();
        pre_exit = new ImageIcon("src/main/resources/images/pre_exit.png").getImage();
        next_enter = new ImageIcon("src/main/resources/images/next_enter.png").getImage();
        next_exit = new ImageIcon("src/main/resources/images/next_exit.png").getImage();

        hotTip = new ImageIcon("src/main/resources/images/hotTip.png").getImage();
        selfTip = new ImageIcon("src/main/resources/images/selfTip.png").getImage();

        try {
            baseBar = ImageIO.read(new File("src/main/resources/images/bar.png"));
            progressBar_head = ImageIO.read(new File("src/main/resources/images/progressBar_head.png"));
            progressBar_body = ImageIO.read(new File("src/main/resources/images/progressBar_body.png"));
            progressBar_tail = ImageIO.read(new File("src/main/resources/images/progressBar_tail.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
