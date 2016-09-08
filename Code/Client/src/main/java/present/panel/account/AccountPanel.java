package present.panel.account;

import present.MainFrame;
import present.PanelSwitcher;
import present.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Y481L on 2016/9/8.
 *
 * 账户面板，拥有注册、登录、找回密码功能
 */
public class AccountPanel extends JPanel {

    private static final int NAV_W = 405;

    private static final int NAV_H = MainFrame.PANEL_H;

    static final int NAV_UP_H = 230;

    private static final int BUTTON_W = 250;

    private static final int BUTTON_H = 56;

    private static final int BTN_PADDING = 28;

    private JPanel current = null;

    public AccountPanel(PanelSwitcher switcher) {
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        current = new LoginPanel(switcher);
        content.add(current, BorderLayout.NORTH);

        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.add(content, BorderLayout.CENTER);

        //导航
        JPanel nav = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(
                        ImageLoader.account_nav, 0, 0, this.getWidth(), this.getHeight(), null
                );
            }
        };
        nav.setPreferredSize(new Dimension(NAV_W, NAV_H));

        //导航栏上方空白面板
        JPanel up = new JPanel();
        up.setOpaque(false);
        up.setPreferredSize(new Dimension(NAV_W, NAV_UP_H));
        nav.add(up, BorderLayout.NORTH);

        //导航栏下方面板
        JPanel down = new JPanel(new BorderLayout());
        down.setOpaque(false);

        //按钮面板
        Box btnBox = Box.createVerticalBox();
        btnBox.setOpaque(false);
        btnBox.add(this.createBtnPanel("登录", ImageLoader.login_btn, (e) -> {
            content.remove(current);
            current = new LoginPanel(switcher);
            content.add(current, BorderLayout.CENTER);
            content.revalidate();
        }));
        btnBox.add(Box.createVerticalStrut(BTN_PADDING));
        btnBox.add(this.createBtnPanel("注册", ImageLoader.register_btn, (e) -> {
            content.remove(current);
            current = new RegisterPanel(switcher);
            content.add(current, BorderLayout.CENTER);
            content.revalidate();
        }));
        btnBox.add(Box.createVerticalStrut(BTN_PADDING));
        btnBox.add(this.createBtnPanel("找回密码", ImageLoader.findpw_btn, (e) -> {
            content.remove(current);
            current = new FindPWPanel(switcher);
            content.add(current, BorderLayout.CENTER);
            content.revalidate();
        }));

        down.add(btnBox, BorderLayout.NORTH);
        nav.add(down, BorderLayout.CENTER);

        this.add(nav, BorderLayout.WEST);
    }

    private JPanel createBtnPanel(String name, ImageIcon icon, ActionListener listener) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(NAV_W, BUTTON_H));
        panel.setOpaque(false);

        JButton btn = new JButton(name);
        btn.addActionListener(listener);
        btn.setIcon(icon);
        btn.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        panel.add(btn);
        return panel;
    }
}
