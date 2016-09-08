package present.panel.stock;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-8-27.
 *
 * 自定义label
 */
public class MyLabel extends JLabel {
    public MyLabel() {
        this.setFont(new Font("微软雅黑", Font.PLAIN, 14));
    }

    public MyLabel(String text) {
        this.setText(text);
        this.setFont(new Font("微软雅黑", Font.PLAIN, 14));
    }
}
