package present.panel.stock;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-8-26.
 * <p>
 * 实时数据面板
 */
class CurrentDataPanel extends JPanel {

    private JPanel panel;

    CurrentDataPanel() {
        panel = this;

        init();
        createUIComponents();
    }

    /**
     * 初始化
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(Color.GRAY);
            panel.setLayout(new BorderLayout());
            panel.setPreferredSize(new Dimension(800, 300));

            panel.revalidate();
        });
    }

    /**
     * 初始化组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            NamePanel namePanel = new NamePanel();
            panel.add(namePanel, BorderLayout.WEST);

            panel.revalidate();
        });
    }

    /**
     * 名称面板
     */
    private class NamePanel extends JPanel {
        private JLabel price, inc_dec, incNum, incRate, time;

        NamePanel() {

        }

        /**
         * 初始化
         */
        private void init() {

        }

        /**
         * 初始化组件
         */
        private void createUIComponents() {

        }
    }
}
