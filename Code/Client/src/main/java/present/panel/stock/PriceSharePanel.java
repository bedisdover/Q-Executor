package present.panel.stock;

import present.charts.KLine;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/27.
 * <p>
 * 分价面板
 */
public class PriceSharePanel extends StockPanel {

    @Override
    void createCenterPanel() {
        SwingUtilities.invokeLater(() -> {
            centerPanel = new CenterPanel();

            panel.add(centerPanel, BorderLayout.CENTER);
        });
    }

    private static class CenterPanel extends JPanel {
        private JPanel panel;

        CenterPanel() {
            panel = this;

            init();
            createUIComponents();
        }

        private void init() {
            SwingUtilities.invokeLater(() -> {
                panel.setLayout(new BorderLayout());

                panel.revalidate();
            });
        }

        private void createUIComponents() {
            SwingUtilities.invokeLater(() -> {
//                panel.add(new PriceSharePlot().getPlotChart("sh600000"), BorderLayout.CENTER);
                panel.add(new KLine().getKLine("sh600000"), BorderLayout.CENTER);
            });
        }
    }
}
