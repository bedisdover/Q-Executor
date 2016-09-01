package present.panel.error;

import javax.swing.*;

/**
 * Created by song on 16-9-1.
 * <p>
 * 错误页面
 */
public class ErrorPanel extends JPanel {

    private JPanel panel;

    private JLabel label;

    public ErrorPanel() {
        panel = this;

        createUIComponents();
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(null);

            label = new JLabel(new ImageIcon("src/main/resources/images/error.png"));
            label.setBounds(100, 200, 180, 170);

            panel.add(label);
            panel.repaint();
        });
    }
}
