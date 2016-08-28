package present.panel.trade;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 交易面板的实时监控面板（曲线图）
 */
class RealTimePanel extends JPanel {

    RealTimePanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
