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
    static ImageIcon increase, decrease, dull;

    public static Image introduce;

    //搜索面板背景图片
    public static Image search_bg;

    //带有登录提示的搜索面板背景图片
    public static Image search_bg_login_tip;

    //带有空白自选列表提示的搜索面板背景图片
    public static Image search_bg_empty_tip;

    //账户界面背景
    public static Image account_bg;

    //交易界面背景
    public static Image trade_bg;

    //账户界面文字
    public static Image account_text;

    //账户界面矩形框头部文字
    public static Image account_header;

    public static Image empty_time_series;

    public static Image empty_result;

    public static Image timer;

    public static Image pre_enter;

    public static Image pre_exit;

    public static Image next_enter;

    public static Image next_exit;

    public static ImageIcon mark;

    public static Image baseBar;

    public static Image progressBar;

    public static Image hotTip;

    public static Image selfTip;

    static {
        increase = new ImageIcon("src/main/resources/images/increase.png");
        decrease = new ImageIcon("src/main/resources/images/decrease.png");
        dull = new ImageIcon("src/main/resources/images/dull.png");

        introduce = new ImageIcon("src/main/resources/images/introduce.jpg").getImage();
        search_bg = new ImageIcon("src/main/resources/images/search_bg.jpg").getImage();
        search_bg_login_tip = new ImageIcon("src/main/resources/images/search_bg_login_tip.jpg").getImage();
        search_bg_empty_tip = new ImageIcon("src/main/resources/images/search_bg_empty_tip.jpg").getImage();
        account_bg = new ImageIcon("src/main/resources/images/account_bg.jpg").getImage();
        account_text = new ImageIcon("src/main/resources/images/account_text.png").getImage();
        account_header = new ImageIcon("src/main/resources/images/account_header.png").getImage();
        trade_bg = new ImageIcon("src/main/resources/images/trade_bg.jpg").getImage();
        empty_time_series = new ImageIcon("src/main/resources/images/empty_time_series.jpg").getImage();
        empty_result = new ImageIcon("src/main/resources/images/empty_strategy.jpg").getImage();
        timer = new ImageIcon("src/main/resources/images/timer.png").getImage();

        pre_enter = new ImageIcon("src/main/resources/images/pre_enter.png").getImage();
        pre_exit = new ImageIcon("src/main/resources/images/pre_exit.png").getImage();
        next_enter = new ImageIcon("src/main/resources/images/next_enter.png").getImage();
        next_exit = new ImageIcon("src/main/resources/images/next_exit.png").getImage();

        hotTip = new ImageIcon("src/main/resources/images/hotTip.png").getImage();
        selfTip = new ImageIcon("src/main/resources/images/selfTip.png").getImage();

        try {
            mark = new ImageIcon("src/main/resources/images/bookmarks.png");
            baseBar = ImageIO.read(new File("src/main/resources/images/bar.png"));
            progressBar = ImageIO.read(new File("src/main/resources/images/progressBar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
