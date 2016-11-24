package present.component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/31.
 *
 * 多行文本显示
 */
public class TextBlock extends JPanel {

    /**
     *
     * @param line  文本行数
     * @param text  文本
     */
    public TextBlock(int line, String text) {
        this(line, text, new Font("微软雅黑", Font.PLAIN, 10), Color.black);
    }

    /**
     *
     * @param line  文本行数
     * @param text  文本
     * @param font  文本字体
     * @param color 文本颜色
     */
    public TextBlock(int line, String text, Font font, Color color) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        int length = text.length();
        int num = length / line;
        num += Math.ceil((length % line) / (double) line);
        for(int i = 0; i < length; i += num) {
            int end = (i + num) >= length ? length : (i + num);
            JLabel label = new JLabel(text.substring(i, end));
            label.setOpaque(false);
            label.setFont(font);
            label.setForeground(color);
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(label);
            panel.setOpaque(false);
            this.add(panel);
        }

        this.setOpaque(false);
    }
}
