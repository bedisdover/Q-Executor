package present;

import present.panel.account.LoginPanel;
import present.panel.account.RegisterPanel;
import present.panel.introduce.IntroPanel;
import present.panel.stock.SearchPanel;
import present.panel.trade.TradePanel;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 软件主窗体
 */
public class MainFrame extends JFrame{

    public static final int PANEL_W = 1000;

    public static final int PANEL_H = 600;

    private static final int MENU_W = 100;

    private static final int MENU_H = 48;

    public MainFrame() {

        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }
        catch (Exception e) {
            System.out.println("Look and feel Exception!");
        }

        this.addComponents();
        this.setAttributes();
    }

    private void setAttributes() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setFrameAtCenter();
        setMinimumSize(new Dimension(PANEL_W, PANEL_H + MENU_H));
        this.setVisible(true);
    }

    private void addComponents() {
        //内容面板
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        container.setLayout(new BorderLayout());
        PanelSwitcher switcher = new PanelSwitcher(container);
        IntroPanel current = new IntroPanel(switcher);
        switcher.setCurrent(current);
        container.add(current);
        this.add(container, BorderLayout.CENTER);

        //导航栏
        JMenuBar bar = new JMenuBar();
        bar.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //交易菜单
        JMenu trade = this.createMenu("交易");
        trade.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                switcher.jump(new TradePanel());
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        bar.add(trade);

        //股票菜单
        JMenu stock = this.createMenu("股票");
        stock.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                switcher.jump(new SearchPanel(switcher));
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        bar.add(stock);

        //简介菜单
        JMenu introduce = this.createMenu("简介");
        introduce.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                switcher.jump(new IntroPanel(switcher));
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        bar.add(introduce);

        //账户账单
        JMenu account = this.createMenu("账户");
        bar.add(account);
        //登录菜单
        JMenuItem login = this.createMenuItem("登录");
        login.addActionListener((e) -> switcher.jump(new LoginPanel(switcher)));
        account.add(login);

        //注册菜单
        JMenuItem register = this.createMenuItem("注册");
        register.addActionListener((e) -> switcher.jump(new RegisterPanel(switcher)));
        account.add(register);

        this.setJMenuBar(bar);
    }

    private JMenuItem createMenuItem(String name) {
        JMenuItem item = new JMenuItem(name);
        item.setPreferredSize(new Dimension(MENU_W, MENU_H));
        return item;
    }

    private JMenu createMenu(String name) {
        JMenu menu = new JMenu("        " + name);
        menu.setPreferredSize(new Dimension(MENU_W, MENU_H));
        return menu;
    }

    private void setFrameAtCenter() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        Dimension frame = this.getPreferredSize();
        this.setLocation((screen.width - frame.width) >> 1,
                (screen.height - frame.height) >> 1);
    }
}
