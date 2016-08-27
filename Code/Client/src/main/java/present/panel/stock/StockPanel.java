package present.panel.stock;

import sun.plugin.javascript.JSClassLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 * <p>
 * 股票界面容器
 */
public class StockPanel extends JPanel {

    private JPanel panel;

    public StockPanel() {
        panel = this;

        init();
        createUIComponents();
    }

    /**
     * 初始化panel
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(Color.LIGHT_GRAY);
            panel.setLayout(new BorderLayout());
        });
    }

    /**
     * 创建组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            NamePanel namePanel = new NamePanel();
            panel.add(namePanel, BorderLayout.NORTH);

            {
//                JPanel sidePanel = new JPanel();
//                sidePanel.setLayout(new BorderLayout());
//                sidePanel.setPreferredSize(new Dimension(200, 1));
//                sidePanel.setBackground(new Color(0x333333));

                CurrentDataPanel currentDataPanel = new CurrentDataPanel();
                JScrollPane scrollPane = new JScrollPane(currentDataPanel);
                scrollPane.setPreferredSize(new Dimension(200, 1));
//                sidePanel.add(currentDataPanel, BorderLayout.NORTH);

                panel.add(scrollPane, BorderLayout.WEST);
            }


        });
    }
}

