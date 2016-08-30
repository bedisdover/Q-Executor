package present.panel.account;

import bl.UserServiceImpl;
import blservice.UserService;
import present.component.QTextField;
import present.component.QPasswordField;
import present.panel.home.NavPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 用户登录界面
 */
public class LoginPanel extends JPanel {

    private UserService service = new UserServiceImpl();

    private QTextField name = new QTextField("邮箱/用户名");

    private QPasswordField password = new QPasswordField("密码");

    private JButton login = new JButton("登录");

    private JButton register = new JButton("注册");

    private JButton findPW = new JButton("找回密码");

    private static final int COMPONENT_NUM = 5;

    private static final int WIDTH = 300;

    private static final int HEIGHT = 56;

    private static final int PADDING = 20;

    public static String LOGIN_USER = null;

    public LoginPanel() {
        this.addComponents();
    }

    private void addComponents() {
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(PADDING << 1));
        JLabel title = new JLabel("登录");
        title.setFont(new Font("宋体", Font.PLAIN, 30));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(title);
        box.add(panel);
        box.add(Box.createVerticalStrut(PADDING << 1));
        box.add(this.wrapComponent(name));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapComponent(password));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapComponent(login));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapComponent(register));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapComponent(findPW));
        box.add(Box.createVerticalStrut(
                NavPanel.PANEL_H - (HEIGHT + PADDING) * COMPONENT_NUM
        ));
        this.setLayout(new BorderLayout());
        this.add(box);
    }

    private JPanel wrapComponent(JComponent c) {
        c.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        c.setFont(new Font("宋体", Font.PLAIN, 22));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(c);
        return panel;
    }
}
