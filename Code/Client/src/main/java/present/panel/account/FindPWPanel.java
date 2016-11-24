package present.panel.account;

import bl.user.UserServiceImpl;
import blservice.user.UserService;
import config.MsgInfo;
import present.component.QTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Y481L on 2016/8/30.
 *
 * 找回密码面板
 */
class FindPWPanel extends VerticalBox {

    private UserService service = new UserServiceImpl();

    FindPWPanel() {

        QTextField name = new QTextField("请输入用户名");
        super.addItem(name);
        JButton send = new JButton("发送邮件找回密码");
        super.addButton(send);

        AbstractAction action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MsgInfo result = service.findPassword(name.getText());
                    JOptionPane.showMessageDialog(FindPWPanel.this, result.getInfo());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FindPWPanel.this, "网络异常");
                }
            }
        };
        send.addActionListener(action);

    }
}
