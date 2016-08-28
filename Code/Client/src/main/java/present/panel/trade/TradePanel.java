package present.panel.trade;

import javax.swing.*;
import java.awt.*;

public class TradePanel extends JPanel {

    private static final int PARAM_PANEL_W = 360;

    private static final int PARAM_PANEL_H = 400;

    private static final int RT_PANEL_W = 640;

    private static final int RT_PANEL_H = PARAM_PANEL_H;

    private static final int MSG_PANEL_W = PARAM_PANEL_W;

    private static final int MSG_PANEL_H = 400;

    private static final int MONITOR_PANEL_W = RT_PANEL_W;

    private static final int MONITOR_PANEL_H = MSG_PANEL_H;

    public TradePanel() {
        init();
    }

    public void init() {

        ParamPanel param = new ParamPanel(PARAM_PANEL_W, PARAM_PANEL_H);
        RealTimePanel rt = new RealTimePanel(RT_PANEL_W, RT_PANEL_H);
        MessagePanel msg = new MessagePanel(MSG_PANEL_W, MSG_PANEL_H);
        MonitorPanel monitor = new MonitorPanel(MONITOR_PANEL_W, MONITOR_PANEL_H);

        Box up = Box.createHorizontalBox();
        up.add(param);
        up.add(rt);
        Box bottom = Box.createHorizontalBox();
        bottom.add(msg);
        bottom.add(monitor);

        this.setLayout(new BorderLayout());
        this.add(up, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.CENTER);
    }

}
