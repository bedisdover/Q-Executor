package present.panel.account;

import bl.UserServiceImpl;
import blservice.UserService;
import config.MsgInfo;
import present.PanelSwitcher;
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

    private static final int WIDTH = 250;

    private static final int HEIGHT = 48;

    private UserService service = new UserServiceImpl();

    FindPWPanel(PanelSwitcher switcher) {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);

        JLabel title = new JLabel("找回密码");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 28));
        title.setForeground(Color.white);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(title);
        panel.setOpaque(false);
        box.add(Box.createVerticalStrut(PADDING));
        box.add(panel);

        QTextField email = new QTextField("请输入用户名");
        email.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        email.setFont(new Font("等线", Font.PLAIN, 20));
        box.add(Box.createVerticalStrut(PADDING));
        JPanel emailPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailPane.add(email);
        emailPane.setOpaque(false);
        box.add(emailPane);

        JButton send = new JButton("发送邮件找回密码");
        send.setFont(new Font("等线", Font.PLAIN, 22));
        send.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        send.addActionListener((e) -> {
            try {
                MsgInfo result = service.findPassword(email.getText());
                JOptionPane.showMessageDialog(this, result.getInfo());
                if (result.isState()) {
                    switcher.jump(new LoginPanel(switcher));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "网络异常");
            }
        });
        box.add(Box.createVerticalStrut(PADDING));
        JPanel btnPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPane.add(send);
        btnPane.setOpaque(false);
        box.add(btnPane);

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.NORTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                new ImageIcon("src/main/resources/images/city3.jpg").getImage(),
                0, 0, this.getWidth(), this.getHeight(), null
        );
    }
}
