package present.panel.trade;

import present.MainFrame;
import present.panel.stock.center.TimeSeriesPanel;
import present.utils.ImageLoader;
import vo.VolumeVO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TradePanel extends JPanel {

    private static final int PARAM_PANEL_W = 420;

    private static final int PARAM_PANEL_H = 380;

    private static final int RT_PANEL_W = MainFrame.PANEL_W - PARAM_PANEL_W;

    private static final int RT_PANEL_H = PARAM_PANEL_H;

    private static final int MSG_PANEL_W = PARAM_PANEL_W;

    private static final int MSG_PANEL_H = MainFrame.PANEL_H - PARAM_PANEL_H;

    private MessagePanel msg = new MessagePanel(MSG_PANEL_W, MSG_PANEL_H);

    private TimeSeriesPanel timeSeriesPanel = new TimeSeriesPanel();

    private JPanel timeContainer = new JPanel(new BorderLayout());

    private JPanel msgContainer = new JPanel(new BorderLayout());

    private JPanel empty_time = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(
                    ImageLoader.empty_time_series, 0, 0, this.getWidth(), this.getHeight(),
                    0, 0,
                    ImageLoader.empty_time_series.getWidth(null),
                    ImageLoader.empty_time_series.getHeight(null),
                    null
            );
        }
    };

    private JPanel empty_msg = new JPanel() {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(
                    ImageLoader.empty_msg, 0, 0, this.getWidth(), this.getHeight(),
                    0, 0,
                    ImageLoader.empty_msg.getWidth(null),
                    ImageLoader.empty_msg.getHeight(null),
                    null
            );
        }
    };

    public TradePanel() {
        ParamPanel param = new ParamPanel(PARAM_PANEL_W, PARAM_PANEL_H, this);

        empty_time.setPreferredSize(new Dimension(RT_PANEL_W, RT_PANEL_H));
        timeContainer.setPreferredSize(new Dimension(RT_PANEL_W, RT_PANEL_H));
        timeContainer.add(empty_time, BorderLayout.CENTER);

        empty_msg.setPreferredSize(new Dimension(MSG_PANEL_W, MSG_PANEL_H));
        msgContainer.setPreferredSize(new Dimension(MSG_PANEL_W, MSG_PANEL_H));
        msgContainer.add(empty_msg, BorderLayout.CENTER);

        Box up = Box.createHorizontalBox();
        up.add(param);
        up.add(timeContainer);

        this.setLayout(new BorderLayout());
        this.add(up, BorderLayout.NORTH);
        this.add(msgContainer, BorderLayout.CENTER);
    }

    void updateMsgPanel(List<VolumeVO> result, String type) {
        msgContainer.remove(empty_msg);
        msgContainer.add(msg, BorderLayout.CENTER);
        msg.update(result, type);
    }

    void updateTimeSeriesPanel(String code) {
        timeContainer.remove(empty_time);
        timeContainer.add(timeSeriesPanel, BorderLayout.CENTER);
        timeSeriesPanel.setStockCode(code);
    }

}
