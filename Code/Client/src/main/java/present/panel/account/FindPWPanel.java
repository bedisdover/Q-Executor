package present.panel.account;

import bl.UserServiceImpl;
import blservice.UserService;
import config.MsgInfo;
import present.component.QTextField;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/30.
 *
 * 找回密码面板
 */
class FindPWPanel extends JPanel {

    private static final int PADDING = 40;

    private static final int WIDTH = 200;

    private static final int HEIGHT = 48;

    private UserService service = new UserServiceImpl();

    FindPWPanel() {
        Box box = Box.createVerticalBox();

        QTextField email = new QTextField("请输入用户名");
        email.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        box.add(Box.createVerticalStrut(PADDING));
        JPanel emailPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailPane.add(email);
        box.add(emailPane);

        JButton send = new JButton("发送邮件找回密码");
        send.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        send.addActionListener((e) -> {
            try {
                MsgInfo result = service.findPassword(email.getText());
                JOptionPane.showMessageDialog(this, result.getInfo());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "网络异常");
            }
        });
        box.add(Box.createVerticalStrut(PADDING));
        JPanel btnPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPane.add(send);
        box.add(btnPane);

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.NORTH);
    }
}
