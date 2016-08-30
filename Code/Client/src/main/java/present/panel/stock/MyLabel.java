package present.panel.stock;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-8-27.
 *
 * 自定义label
 */
class MyLabel extends JLabel {
    MyLabel() {
        this.setFont(new Font("微软雅黑", Font.PLAIN, 14));
    }

    MyLabel(String text) {
        this.setText(text);
        this.setFont(new Font("微软雅黑", Font.PLAIN, 14));
    }
}
