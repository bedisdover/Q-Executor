package present.panel.stock.center;

import present.charts.KLine;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-7.
 * <p>
 * K线图面板
 */
public class KLinePanel extends CenterPanel {

    public KLinePanel(String stockCode) {
        super.init();
        createUIComponents(stockCode);
    }

    private void createUIComponents(String stockCode) {
        SwingUtilities.invokeLater(() -> {
            try {
                this.add(new KLine().getKLine(stockCode), BorderLayout.CENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void getData() {
    }
}
