package present.panel.trade;

import present.MainFrame;

import javax.swing.*;
import java.awt.*;

public class TradePanel extends JPanel {

    private static final int PARAM_PANEL_W = 380;

    private static final int PARAM_PANEL_H = 380;

    private static final int RT_PANEL_W = MainFrame.PANEL_W - PARAM_PANEL_W;

    private static final int RT_PANEL_H = PARAM_PANEL_H;

    private static final int MSG_PANEL_W = PARAM_PANEL_W;

    private static final int MSG_PANEL_H = MainFrame.PANEL_H - PARAM_PANEL_H;

    MessagePanel msg = new MessagePanel(MSG_PANEL_W, MSG_PANEL_H);

//    private static final int MONITOR_PANEL_W = RT_PANEL_W;
//
//    private static final int MONITOR_PANEL_H = MSG_PANEL_H;

    public TradePanel() {
        ParamPanel param = new ParamPanel(PARAM_PANEL_W, PARAM_PANEL_H, this);
        RealTimePanel rt = new RealTimePanel(RT_PANEL_W, RT_PANEL_H);

//        MonitorPanel monitor = new MonitorPanel(MONITOR_PANEL_W, MONITOR_PANEL_H);
        Box up = Box.createHorizontalBox();
        up.add(param);
        up.add(rt);
        Box bottom = Box.createHorizontalBox();
        bottom.add(msg);
//        bottom.add(monitor);

        this.setLayout(new BorderLayout());
        this.add(up, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.CENTER);
        param.requestFocus();
    }

    public void updateMsgPanel() {
        msg.update();
    }

}
