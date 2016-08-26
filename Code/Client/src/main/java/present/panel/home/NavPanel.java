package present.panel.home;

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

    private JButton stock = new JButton("股票");

    private JButton trade = new JButton("交易");

    private JButton introduce = new JButton("简介");

    private PanelSwitcher switcher;

    private static final int BUTTON_NUM = 3;

    private static final int PANEL_W = 120;

    public static final int PANEL_H = 600;

    private static final int PADDING = 6;

    private static final int BUTTON_W = PANEL_W - (PADDING << 1);

    private static final int BUTTON_H = 42;

    public NavPanel(PanelSwitcher switcher) {
        this.switcher = switcher;
        this.initButton();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        Box box = Box.createVerticalBox();
        this.initButtonBox(box);
        this.add(box);
        this.setBackground(Color.BLUE);
        this.setButtonHandler();
    }

    private void initButton() {
        trade.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        stock.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        introduce.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
    }

    private void initButtonBox(Box box) {
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapButton(trade));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapButton(stock));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapButton(introduce));
        box.add(Box.createVerticalStrut(
                PANEL_H - (BUTTON_H + PADDING) * BUTTON_NUM
        ));
    }

    private Box wrapButton(JButton btn) {
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(PADDING));
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(btn);
        box.add(panel);
        box.add(Box.createHorizontalStrut(PADDING));
        return box;
    }

    private void setButtonHandler() {
        trade.addActionListener((e) -> switcher.jump(new TradePanel()));
        stock.addActionListener((e) -> switcher.jump(new StockPanel()));
        introduce.addActionListener((e) -> switcher.jump(new IntroPanel()));
    }

}
