package present.panel.stock.center;

import present.charts.KLine;
import present.panel.stock.StockPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-7.
 * <p>
 * K线图面板
 */
public class KLinePanel extends CenterPanel {

    private StockPanel stockPanel;

    public KLinePanel(String stockCode, StockPanel stockPanel) {
        super.init();

        this.stockPanel = stockPanel;
        createUIComponents(stockCode);
    }

    private void createUIComponents(String stockCode) {
        SwingUtilities.invokeLater(() -> {
            try {
                this.add(new KLine().getKLine(stockCode), BorderLayout.CENTER);
            } catch (Exception e) {
                e.printStackTrace();
                stockPanel.displayError();
            }
        });
    }

    @Override
    public void getData() {
    }
}
