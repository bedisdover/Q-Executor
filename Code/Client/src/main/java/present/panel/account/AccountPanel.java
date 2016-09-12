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

    private JComponent current = null;

    public AccountPanel(PanelSwitcher switcher) {

        //内容面板容器
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(AccountConst.BACKGROUND);
        current = new LoginPanel(switcher);
        content.add(current, BorderLayout.CENTER);

        //导航面板
        JPanel nav = createNavPanel(content, switcher);

        this.setLayout(new BorderLayout());
        this.add(content, BorderLayout.CENTER);
        this.add(nav, BorderLayout.WEST);
    }

    private JPanel createNavPanel(JPanel content, PanelSwitcher switcher) {
        //导航
        JPanel nav = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = ImageLoader.account_nav;
                int w = this.getWidth();
                int h = this.getHeight();
                g.drawImage(
                        img , 0, 0, w, h, null
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
        down.setPreferredSize(new Dimension(
                NAV_W, MainFrame.PANEL_H - NAV_UP_H
        ));
        //导航栏按钮面板
        down.add(createBtnBox(content, switcher), BorderLayout.NORTH);
        nav.add(down, BorderLayout.CENTER);

        return nav;
    }

    /**
     * 创建导航栏按钮面板
     * @param content 内容面板
     * @param switcher 面板转换器
     * @return 按钮面板
     */
    private Box createBtnBox(JPanel content, PanelSwitcher switcher) {
        Box btnBox = Box.createVerticalBox();
        btnBox.setOpaque(false);

        //登录
        btnBox.add(this.createBtnPanel(ImageLoader.login_btn, (e) ->
            current = PanelSwitcher.jump(content, current, new LoginPanel(switcher))
        ));

        btnBox.add(Box.createVerticalStrut(BTN_PADDING));

        //注册
        btnBox.add(this.createBtnPanel(ImageLoader.register_btn, (e) ->
            current = PanelSwitcher.jump(content, current, new RegisterPanel())
        ));

        btnBox.add(Box.createVerticalStrut(BTN_PADDING));

        //找回密码
        btnBox.add(this.createBtnPanel(ImageLoader.findpw_btn, (e) ->
            current = PanelSwitcher.jump(content, current, new FindPWPanel())
        ));

        btnBox.add(Box.createVerticalStrut(BTN_PADDING));

        //问卷
        btnBox.add(this.createBtnPanel(ImageLoader.questionnair_btn, (e) ->
            current = PanelSwitcher.jump(content, current, new QuestionnairePanel())
        ));

        return btnBox;
    }

    /**
     * 创建按钮
     * @param icon 按钮图标
     * @param listener 按钮监听器
     * @return 包含按钮的面板
     */
    private JPanel createBtnPanel(ImageIcon icon, ActionListener listener) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(NAV_W, BUTTON_H));
        panel.setOpaque(false);

        JButton btn = new JButton();
        btn.addActionListener(listener);
        btn.setIcon(icon);
        btn.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        panel.add(btn);
        return panel;
    }

}
