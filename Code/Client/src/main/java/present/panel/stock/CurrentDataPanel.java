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
            panel.setBackground(new Color(0xeeeeee));
            panel.setLayout(new BorderLayout(0, 5));
            panel.setPreferredSize(new Dimension(1, 780));

            panel.revalidate();
        });
    }

    /**
     * 初始化组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            PricePanel pricePanel = new PricePanel();
            panel.add(pricePanel, BorderLayout.NORTH);

            {
                Box centerBox = Box.createVerticalBox();

                DataPanel dataPanel = new DataPanel();
                centerBox.add(dataPanel);
                HandicapPanel handicapPanel = new HandicapPanel();
                centerBox.add(handicapPanel);

                panel.add(centerBox, BorderLayout.CENTER);
            }

            BasicInfoPanel basicInfoPanel = new BasicInfoPanel();
            panel.add(basicInfoPanel, BorderLayout.SOUTH);

            panel.revalidate();
        });
    }
}
