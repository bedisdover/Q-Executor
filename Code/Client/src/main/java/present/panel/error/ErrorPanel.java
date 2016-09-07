package present.panel.error;

import present.panel.stock.center.CenterPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-1.
 * <p>
 * 错误页面
 */
public class ErrorPanel extends CenterPanel {

    private JLabel label;

    /**
     * 前一个面板
     */
    private CenterPanel centerPanel;

    public ErrorPanel(CenterPanel centerPanel) {
        this.centerPanel = centerPanel;

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

    @Override
    public boolean getData() {
        // TODO 持续更新
        return true;
    }
}
