package present.panel.home;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import present.PanelSwitcher;
import present.panel.introduce.IntroPanel;
import present.panel.stock.StockPanel;
import present.panel.trade.TradePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 导航面板
 */
public class NavPanel extends JPanel{

    private CollapsePanel stock;

    private CollapsePanel trade;

    private CollapsePanel introduce;

    private PanelSwitcher switcher;

    private static final int PANEL_W = 120;

    public static final int PANEL_H = 600;

    private static final int PADDING = 6;

    private static final int BUTTON_W = PANEL_W - (PADDING << 1);

    private static final int BUTTON_H = 42;

    public NavPanel(PanelSwitcher switcher) {
        this.switcher = switcher;
        this.initCollapsePanels();
        Box box = Box.createVerticalBox();
        this.initButtonBox(box);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        this.add(box, BorderLayout.NORTH);
    }

    private void initButtonBox(Box box) {
        box.add(Box.createVerticalStrut(PADDING));
        box.add(trade);
        box.add(Box.createVerticalStrut(PADDING));
        box.add(stock);
        box.add(Box.createVerticalStrut(PADDING));
        box.add(introduce);
//        box.add(Box.createVerticalStrut(PADDING));
//        JPanel panel = new JPanel();
//        panel.setPreferredSize(new Dimension(
//                WIDTH, PANEL_H - (BUTTON_H + PADDING) * BUTTON_NUM
//        ));
//        box.add(panel);
    }

    private void initCollapsePanels() {
        //交易
        trade = new CollapsePanel(new SuperButton("交易"), BUTTON_W, BUTTON_H,
                (e) -> switcher.jump(new TradePanel()));
        trade.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));

        //股票
        stock = new CollapsePanel(new SuperButton("股票"), BUTTON_W, BUTTON_H,
                (e) -> switcher.jump(new StockPanel("sh600000")));

//        SubButton general = new SubButton("大单");
//        // FIXME 传入CurrentDataPanel对象
//        general.addActionListener((e) -> switcher.jump(new GeneralPanel("sh600000", null)));
//        stock.addSubButton(general);
//
//        SubButton single = new SubButton("逐笔");
//        single.addActionListener((e) -> switcher.jump(new SinglePanel("sh600000")));
//        stock.addSubButton(single);
//
//        SubButton price = new SubButton("分价");
//        price.addActionListener((e) -> switcher.jump(new PriceSharePanel("sh600000")));
//        stock.addSubButton(price);

        stock.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));

        //简介
        introduce = new CollapsePanel(new SuperButton("简介"), BUTTON_W, BUTTON_H,
                (e) -> switcher.jump(new IntroPanel()));
        introduce.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
    }

    /**
     * 折叠父按钮
     */
    private class SuperButton extends JButton {
        SuperButton(String name) {
            this.setText(name);
            //当前按钮设置颜色的方法依赖于beautyEye这个Look And Feel 包
            this.setUI(new BEButtonUI(). setNormalColor(BEButtonUI.NormalColor.blue));
        }
    }

    /**
     * 折叠子按钮
     */
    private class SubButton extends JButton {
        SubButton(String name) {
            this.setText(name);
        }
    }

}
