package present.panel.account;

import bl.user.UserServiceImpl;
import blservice.user.UserService;
import config.MsgInfo;
import present.PanelSwitcher;
import present.component.QPasswordField;
import present.component.QTextField;
import present.component.VerticalBox;
import present.panel.stock.SearchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 用户登录界面
 */
public class LoginPanel extends VerticalBox {

    private UserService service = new UserServiceImpl();

    private PanelSwitcher switcher;

    private QTextField name = new QTextField("邮箱/用户名");

    private QPasswordField password = new QPasswordField("密码");

    private JButton login = new JButton("登录");

    private JButton register = new JButton("注册");

    private JButton findPW = new JButton("找回密码");

    private static final int PADDING = 20;

    public static String LOGIN_USER = null;

    public static String LOGIN_PW = null;

    public static boolean IS_LOGIN = false;

    public LoginPanel(PanelSwitcher switcher) {
        this.switcher = switcher;

        super.addStrut(AccountPanel.NAV_UP_H);
        super.addItem(name);
        super.addStrut(PADDING);
        super.addItem(password);
        super.addStrut(PADDING);
        super.addItem(login);

        this.addListeners();
    }

    private void addListeners() {
        AbstractAction enter = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = name.getText();
                String pw = new String(password.getPassword());
                try {
                    MsgInfo result = service.login(user, pw);
                    if(result.isState()) {
                        LOGIN_USER = user;
                        LOGIN_PW = pw;
                        IS_LOGIN = true;
                    }
                    JOptionPane.showMessageDialog(LoginPanel.this, result.getInfo());
                    if (result.isState()) {
                        switcher.jump(new SearchPanel(switcher));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginPanel.this, "网络异常");
                }
            }
        };

        password.getInputMap().put(KeyStroke.getKeyStroke('\n'), "login");
        password.getActionMap().put("login", enter);

        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                enter.actionPerformed(null);
            }
        });

        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                switcher.jump(new RegisterPanel());
            }
        });

        findPW.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                switcher.jump(new FindPWPanel());
            }
        });
    }
}
