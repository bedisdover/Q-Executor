package present.panel.stock;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 * <p>
 * 股票界面容器
 */
public class StockPanel extends JPanel {


    public StockPanel() {
        init();
        createUIComponents();
    }

    /**
     * 初始化panel
     */
    private void init() {
        JPanel panel = this;

        SwingUtilities.invokeLater(() -> {
            panel.setBackground(Color.LIGHT_GRAY);
            panel.setLayout(new BorderLayout());
        });
    }

    /**
     * 创建组件
     */
    private void createUIComponents() {
        JPanel panel = this;

        SwingUtilities.invokeLater(() -> {
            CurrentDataPanel currentDataPanel = new CurrentDataPanel();

            panel.add(currentDataPanel, BorderLayout.NORTH);
        });
    }

}

