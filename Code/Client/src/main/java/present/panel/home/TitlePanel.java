package present.panel.home;

import present.PanelSwitcher;
import present.panel.account.LoginPanel;
import present.panel.account.RegisterPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 标题面板
 */
public class TitlePanel extends JPanel{

    private JButton login = new JButton("登录");

    private JButton register = new JButton("注册");

    private PanelSwitcher switcher;

    private static final int BUTTON_W = 96;

    private static final int BUTTON_H = 48;

    private static final int PADDING = 6;

    private static final int PANEL_W = BUTTON_W * 10;

    private static final int PANEL_H = BUTTON_H + (PADDING << 1);

    public TitlePanel(PanelSwitcher switcher) {
        this.switcher = switcher;
        this.initButton(login);
        this.initButton(register);
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        this.add(register);
        this.add(login);
        this.setButtonHandler();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.BLACK);
        g.drawString("Q-Executor", 0, 0);
    }

    private void initButton(JButton btn) {
        btn.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
    }

    private void setButtonHandler() {
        login.addActionListener((e) -> switcher.jump(new LoginPanel()));
        register.addActionListener((e) -> switcher.jump(new RegisterPanel()));
    }
}
