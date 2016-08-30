package present.panel.home;

import present.PanelSwitcher;
import present.panel.account.LoginPanel;
import present.panel.account.RegisterPanel;
import present.panel.introduce.IntroPanel;
import present.panel.stock.GeneralPanel;
import present.panel.stock.PriceSharePanel;
import present.panel.stock.SinglePanel;
import present.panel.stock.StockPanel;
import present.panel.trade.TradePanel;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 标题面板
 */
public class TitlePanel extends JPanel{

    private JButton login = new JButton("登录");

    private JButton register = new JButton("注册");

    private JMenu stock = new JMenu("股票");

    private JButton trade = new JButton("交易");

    private JButton introduce = new JButton("简介");

    private PanelSwitcher switcher;

    private static final int BUTTON_W = 96;

    private static final int BUTTON_H = 48;

    private static final int PADDING = 6;

    private static final int PANEL_W = BUTTON_W * 10;

    private static final int PANEL_H = BUTTON_H + (PADDING << 1);

    public TitlePanel(PanelSwitcher switcher) {
        this.switcher = switcher;
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        this.addButtons();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.BLACK);
        g.drawString("Q-Executor", 0, 0);
    }

    private void addButtons() {
        //交易导航菜单
        this.initButton(trade);
        this.add(trade);

        //股票导航菜单
        JMenuBar bar = new JMenuBar();
        stock.add(initMenuItem("大单", (e) -> switcher.jump(new GeneralPanel("sh600000"))));
        stock.add(initMenuItem("逐笔", (e) -> switcher.jump(new SinglePanel("sh600000"))));
        stock.add(initMenuItem("分价", (e) -> switcher.jump(new PriceSharePanel("sh600000"))));
        stock.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                switcher.jump(new StockPanel("sh600000"));
            }

            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });

        stock.setPreferredSize(new Dimension(BUTTON_W / 3, BUTTON_H));
        JMenu m1 = new JMenu();
        m1.setPreferredSize(new Dimension(BUTTON_W / 3, BUTTON_H));
        m1.setEnabled(false);
        JMenu m2 = new JMenu();
        m2.setPreferredSize(new Dimension(BUTTON_W / 3, BUTTON_H));
        m2.setEnabled(false);
        bar.add(m1);
        bar.add(stock);
        bar.add(m2);
        this.add(bar);

        //简介导航菜单
        this.initButton(introduce);
        this.add(introduce);

        //登录导航菜单
        this.initButton(login);
        this.add(login);

        //注册导航菜单
        this.initButton(register);
        this.add(register);

        this.setButtonHandler();
    }

    private void initButton(JButton btn) {
        btn.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        btn.setContentAreaFilled(false);
    }

    private JMenuItem initMenuItem(String text, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        item.addActionListener(listener);
        return item;
    }

    private void setButtonHandler() {
        login.addActionListener((e) -> switcher.jump(new LoginPanel(switcher)));
        register.addActionListener((e) -> switcher.jump(new RegisterPanel()));
        trade.addActionListener((e) -> switcher.jump(new TradePanel()));
        introduce.addActionListener((e) -> switcher.jump(new IntroPanel()));
    }
}
