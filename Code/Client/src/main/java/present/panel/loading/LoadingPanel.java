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

    /**
     * 停止加载动画, 渐变消失，完全消失后加入新的面板
     *
     * @param container 容器对象，包含动画面板
     * @param target    目标对象，动画结束后添加的面板
     */
    public void stopLoading(JPanel container, JPanel target) {
        SwingUtilities.invokeLater(() -> {
            new Thread() {
                @Override
                public void run() {
                    double opaque = 1;

                    for (int i = 0; i < 10; i++) {
                    }
                }
            };
        });
    }
}
