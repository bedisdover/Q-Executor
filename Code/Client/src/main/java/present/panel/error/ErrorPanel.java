package present.panel.error;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-1.
 * <p>
 * 错误页面
 */
public class ErrorPanel extends JPanel {

    private JLabel label;

    public ErrorPanel() {
        createUIComponents();
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            this.setLayout(new GridLayout(3, 1));

            this.add(new JPanel());
            label = new JLabel(new ImageIcon("src/main/resources/images/error.png"));

            this.add(label);
            this.add(new JPanel());
            this.repaint();
        });
    }
}
