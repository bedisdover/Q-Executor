package present.panel.account;

import present.MainFrame;
import present.PanelSwitcher;
import present.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Y481L on 2016/9/8.
 *
 * 账户面板，拥有注册、登录、找回密码功能
 */
public class AccountPanel extends JPanel {

    private static final int NAV_W = 620;

    private static final int NAV_H = MainFrame.PANEL_H;

    static final int NAV_UP_H = 140;

    private static final int TEXT_W = 600;

    private static final int TEXT_H = NAV_UP_H;

    private static final int BUTTON_W = 250;

    private static final int BUTTON_H = 56;

    private static final int BTN_PADDING = 28;

    private JComponent current = null;

    public AccountPanel(PanelSwitcher switcher) {

        //内容面板容器
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        current = new LoginPanel(switcher);
        content.add(current, BorderLayout.CENTER);

        //导航面板
        JPanel nav = createNavPanel(content, switcher);
        nav.setOpaque(false);

        this.setLayout(new BorderLayout());
        this.add(content, BorderLayout.CENTER);
        this.add(nav, BorderLayout.WEST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                ImageLoader.account_bg, 0, 0, this.getWidth(), this.getHeight(), null
        );
    }

    private JPanel createNavPanel(JPanel content, PanelSwitcher switcher) {
        //导航
        JPanel nav = new JPanel(new BorderLayout());
        nav.setPreferredSize(new Dimension(NAV_W, NAV_H));

        //导航栏内容全部置于北部
        Box north = Box.createVerticalBox();
        north.setOpaque(false);
        nav.add(north, BorderLayout.NORTH);

        //导航栏上方空白面板
        JPanel up = new JPanel();
        up.setOpaque(false);
        up.setPreferredSize(new Dimension(NAV_W, NAV_UP_H));
        north.add(up);

        //导航栏文字说明
        JPanel text = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(
                        ImageLoader.account_text, 0, 0,
                        TEXT_W, TEXT_H, null
                );
            }
        };
        text.setOpaque(false);
        text.setPreferredSize(new Dimension(
                TEXT_W, TEXT_H
        ));
        JPanel textContainer = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );
        textContainer.setOpaque(false);
        textContainer.add(text);
        north.add(textContainer);

        //导航栏按钮面板
        north.add(createBtnBox(content, switcher));

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
        btnBox.add(this.createBtnPanel("登录", (e) ->
            current = PanelSwitcher.jump(content, current, new LoginPanel(switcher))
        ));

        btnBox.add(Box.createVerticalStrut(BTN_PADDING));

        //注册
        btnBox.add(this.createBtnPanel("注册", (e) ->
            current = PanelSwitcher.jump(content, current, new RegisterPanel())
        ));

        btnBox.add(Box.createVerticalStrut(BTN_PADDING));

        //找回密码
        btnBox.add(this.createBtnPanel("找回密码", (e) ->
            current = PanelSwitcher.jump(content, current, new FindPWPanel())
        ));

        btnBox.add(Box.createVerticalStrut(BTN_PADDING));

        //问卷
        btnBox.add(this.createBtnPanel("风险偏好设置", (e) ->
            current = PanelSwitcher.jump(content, current, new RiskSettingPanel())
        ));

        return btnBox;
    }

    /**
     * 创建按钮
     * @param listener 按钮监听器
     * @param text 按钮文字
     * @return 包含按钮的面板
     */
    private JPanel createBtnPanel(String text, ActionListener listener) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 1));
        panel.setPreferredSize(new Dimension(NAV_W, BUTTON_H + 1));
        panel.setOpaque(false);

        JButton btn = new JButton();
        btn.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        btn.setForeground(Color.WHITE);
        btn.setText(text);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        btn.addActionListener(listener);
        btn.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        panel.add(btn);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btn.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btn.setForeground(Color.WHITE);
            }
        });

        return panel;
    }

}
