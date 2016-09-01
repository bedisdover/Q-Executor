package present.panel.stock;

import javax.swing.*;

/**
 * Created by song on 16-9-1.
 *
 * 深度面板
 */
class DepthPanel extends JPanel {
    private JPanel panel;

    private String stockCode;

    DepthPanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;
    }
}
