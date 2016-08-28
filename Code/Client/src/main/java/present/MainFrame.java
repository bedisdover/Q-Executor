package present;

import present.panel.account.LoginPanel;
import present.panel.account.RegisterPanel;
import present.panel.introduce.IntroPanel;
import present.panel.stock.StockPanel;
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

    private static final int PANEL_W = 900;

    private static final int PANEL_H = 600;

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
        this.setVisible(true);
        this.pack();
        this.setFrameAtCenter();
    }

    private void addComponents() {
        //内容面板
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        container.setLayout(new BorderLayout());
        IntroPanel current = new IntroPanel();
        container.add(current);
        PanelSwitcher switcher = new PanelSwitcher(container, current);
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
                switcher.jump(new StockPanel());
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
//        //大单菜单项
//        JMenuItem general = this.createMenuItem(
//                "大单", (e) -> switcher.jump(new GeneralPanel())
//        );
//        stock.add(general);
//        //逐笔菜单项
//        JMenuItem single = this.createMenuItem(
//                "逐笔", (e) -> switcher.jump(new SinglePanel())
//        );
//        stock.add(single);
//        //分价菜单项
//        JMenuItem price = this.createMenuItem(
//                "分价", (e) -> switcher.jump(new PriceSharePanel())
//        );
//        stock.add(price);
        bar.add(stock);

        //简介菜单
        JMenu introduce = this.createMenu("简介");
        introduce.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                switcher.jump(new IntroPanel());
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        bar.add(introduce);

        //登录菜单
        JMenu login = this.createMenu("登录");
        login.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                switcher.jump(new LoginPanel());
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        bar.add(login);

        //注册菜单
        JMenu register = this.createMenu("注册");
        register.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                switcher.jump(new RegisterPanel());
            }

            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        bar.add(register);

        this.setJMenuBar(bar);

//        TitlePanel title = new TitlePanel(switcher);
//        NavPanel nav = new NavPanel(switcher);
//        this.add(title, BorderLayout.NORTH);
//        this.add(nav, BorderLayout.WEST);

    }

    private JMenu createMenu(String name) {
        JMenu menu = new JMenu("        " + name);    //文字无法居中，用空格代替...
        menu.setPreferredSize(new Dimension(MENU_W, MENU_H));
        return menu;
    }

//    private JMenuItem createMenuItem(String name, ActionListener listener) {
//        JMenuItem item = new JMenuItem(name);
//        item.setPreferredSize(new Dimension(MENU_W, MENU_H));
//        item.addActionListener(listener);
//        return item;
//    }

    private void setFrameAtCenter() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        Dimension frame = this.getPreferredSize();
        this.setLocation((screen.width - frame.width) >> 1,
                (screen.height - frame.height) >> 1);
    }
}
