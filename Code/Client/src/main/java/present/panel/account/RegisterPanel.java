package present.panel.account;

import bl.UserServiceImpl;
import blservice.UserService;
import config.MsgInfo;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import present.MainFrame;
import present.PanelSwitcher;
import present.component.QPasswordField;
import present.component.QTextField;
import present.panel.home.NavPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 用户注册界面
 */
public class RegisterPanel extends JPanel{

    private UserService service = new UserServiceImpl();

    private PanelSwitcher switcher;

    private QTextField email = new QTextField("邮箱");

    private QTextField name = new QTextField("用户名");

    private QTextField nickname = new QTextField("昵称");

    private QPasswordField password = new QPasswordField("密码");

    private QPasswordField confirmPW = new QPasswordField("确认密码");

    private JButton register = new JButton("注册");

    private static final int COMPONENT_NUM = 6;

    private static final int WIDTH = 300;

    private static final int HEIGHT = 56;

    private static final int PADDING = 20;

    public RegisterPanel(PanelSwitcher switcher) {
        this.switcher = switcher;
        this.addComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                new ImageIcon("src/main/resources/images/city2.jpg").getImage(),
                0, 0, MainFrame.PANEL_W, MainFrame.PANEL_H, null
        );
    }

    private void addComponents() {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);

        box.add(Box.createVerticalStrut(PADDING << 1));
        JLabel title = new JLabel("注册");
        title.setFont(new Font("宋体", Font.PLAIN, 30));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.add(title);
        box.add(panel);

        box.add(Box.createVerticalStrut(PADDING << 1));
        box.add(this.wrapComponents(email));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapComponents(name));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapComponents(nickname));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapComponents(password));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapComponents(confirmPW));
        box.add(Box.createVerticalStrut(PADDING));
        box.add(this.wrapComponents(register));
        register.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        box.add(Box.createVerticalStrut(
                NavPanel.PANEL_H - (HEIGHT + PADDING) * COMPONENT_NUM
        ));
        this.addBtnListener();
        this.setLayout(new BorderLayout());
        this.add(box);
    }

    private JPanel wrapComponents(JComponent c) {
        c.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        c.setFont(new Font("宋体", Font.PLAIN, 22));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(c);
        panel.setOpaque(false);
        return panel;
    }

    private void addBtnListener() {
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                //验证密码和确认密码是否一致
                String pw = new String(password.getPassword());
                String pwConfirm = new String(confirmPW.getPassword());
                if (!pw.equals(pwConfirm)) {
                    JOptionPane.showMessageDialog(RegisterPanel.this, "确认密码与密码不一致");
                    return ;
                }

                try {
                    MsgInfo result = service.register(
                            name.getText(), nickname.getText(),
                            new String(password.getPassword()), email.getText()
                    );
                    JOptionPane.showMessageDialog(RegisterPanel.this, result.getInfo());
                    if (result.isState()) {
                        switcher.jump(new LoginPanel(switcher));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterPanel.this, "网络异常");
                }
            }
        });
    }
}
