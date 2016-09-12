package present.panel.account;

import bl.user.UserServiceImpl;
import blservice.user.UserService;
import config.MsgInfo;
import present.component.QPasswordField;
import present.component.QTextField;
import present.component.VerticalBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 用户注册界面
 */
public class RegisterPanel extends VerticalBox{

    private UserService service = new UserServiceImpl();

    private QTextField email = new QTextField("邮箱");

    private QTextField name = new QTextField("用户名");

    private QTextField nickname = new QTextField("昵称");

    private QPasswordField password = new QPasswordField("密码");

    private QPasswordField confirmPW = new QPasswordField("确认密码");

    private JButton register = new JButton("注册");

    private static final int PADDING = 20;

    public RegisterPanel() {

        super.addStrut(PADDING << 2);
        super.addItem(email);
        super.addStrut(PADDING);
        super.addItem(name);
        super.addStrut(PADDING);
        super.addItem(nickname);
        super.addStrut(PADDING);
        super.addItem(password);
        super.addStrut(PADDING);
        super.addItem(confirmPW);
        super.addStrut(PADDING);
        super.addItem(register);

        this.addBtnListener();
    }

    private void addBtnListener() {
        AbstractAction action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterPanel.this, "网络异常");
                }
            }
        };
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                action.actionPerformed(null);
            }
        });

        confirmPW.getInputMap().put(KeyStroke.getKeyStroke('\n'), "register");
        confirmPW.getActionMap().put("register", action);
    }
}
