package present.panel.loading;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-2.
 * <p>
 * 加载界面
 */
public class LoadingPanel extends JPanel {

    public LoadingPanel() {
        createUIComponents();
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            setBackground(new Color(0xECF0F1));
            setLayout(new GridLayout(3, 1));

            JPanel panel = new JPanel();
            panel.setBackground(new Color(0xECF0F1));
            add(panel);

            JLabel label = new JLabel(new ImageIcon("src/main/resources/images/loading.gif"));
            add(label);

            JPanel panel2 = new JPanel();
            panel2.setBackground(new Color(0xECF0F1));
            add(panel2);

            revalidate();
            repaint();
        });
    }
}
