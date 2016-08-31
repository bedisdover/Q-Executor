package present.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Created by Y481L on 2016/8/25.
 * <p>
 * 自定义文本框，支持占位符功能
 */
public class QTextField extends JTextField {

    private String placeholder;

    public QTextField(String placeholder) {
        this.placeholder = placeholder;
        this.setForeground(Color.GRAY);
        this.setText(placeholder);
        this.setHandler();
    }

    QTextField() {
        this("");
    }

    private void setHandler() {
        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String text = getText();
                if (text.isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(placeholder);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                String text = getText();
                if (text.equals(placeholder)) {
                    setForeground(Color.BLACK);
                    setText("");
                }
            }

        });
    }
}
