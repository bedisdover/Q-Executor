package present.panel.stock;

import present.charts.KLine;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 * <p>
 * 股票界面容器
 */
public class StockPanel extends JPanel {

    JPanel panel;

    JPanel centerPanel;

    public StockPanel() {
        panel = this;

        init();
        createUIComponents();
        createCenterPanel();
    }

    /**
     * 初始化panel
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout());

            panel.revalidate();
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
                CurrentDataPanel currentDataPanel = new CurrentDataPanel();
                JScrollPane scrollPane = new JScrollPane(currentDataPanel);
                scrollPane.setPreferredSize(new Dimension(200, 1));

                panel.add(scrollPane, BorderLayout.WEST);
            }
        });
    }

    /**
     * 创建中部面板
     */
    void createCenterPanel() {
        SwingUtilities.invokeLater(() -> {
            panel.add(new KLine().getKLine("sh600000"), BorderLayout.CENTER);
            panel.revalidate();
        });
    }
}