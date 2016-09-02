package present.panel.error;

import javax.swing.*;

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
            this.setLayout(null);

            label = new JLabel(new ImageIcon("src/main/resources/images/error.png"));
            label.setBounds(100, 200, 180, 170);

            this.add(label);
            this.repaint();
        });
    }
}
