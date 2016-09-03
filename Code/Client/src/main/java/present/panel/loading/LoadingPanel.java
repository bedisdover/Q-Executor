package present.panel.loading;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-2.
 * <p>
 * 加载界面
 */
public class LoadingPanel extends JPanel {
    private JPanel panel;

    /**
     * 渐变标记
     */
    private boolean flag;

    /**
     * 透明度
     */
    private float opacity = 1;

    public LoadingPanel() {
        panel = this;
        createUIComponents();
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            setBackground(new Color(0xECF0F1));

            Icon icon = new ImageIcon("src/main/resources/images/loading.gif");
            JLabel label = new JLabel(icon);

            label.setBounds(100, 100, 600, 300);

            this.add(label);
            this.repaint();
        });
    }
}
