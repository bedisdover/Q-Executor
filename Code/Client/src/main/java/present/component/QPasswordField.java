package present.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 自定义密码框，拥有占位符功能
 */
public class QPasswordField extends JPasswordField {

    private String placeholder;

    public QPasswordField(String placeholder) {
        this.placeholder = placeholder;
        this.setHorizontalAlignment(JPasswordField.CENTER);
        this.setForeground(Color.GRAY);
        this.setEchoChar((char) 0);
        this.setText(placeholder);
        this.setHandler();
    }

    private void setHandler() {
        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String pw = new String(getPassword());
                if (pw.isEmpty()) {
                    setForeground(Color.GRAY);
                    setEchoChar((char) 0);
                    setText(placeholder);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                String text = new String(getPassword());
                if (text.equals(placeholder)) {
                    setText("");
                    setEchoChar('*');
                    setForeground(Color.BLACK);
                }
            }
        });
    }
}
