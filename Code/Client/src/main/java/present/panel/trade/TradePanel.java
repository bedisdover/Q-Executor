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

    private JPanel empty = new JPanel() {
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

    public TradePanel() {
        ParamPanel param = new ParamPanel(PARAM_PANEL_W, PARAM_PANEL_H, this);
        timeSeriesPanel.setPreferredSize(new Dimension(RT_PANEL_W, RT_PANEL_H));
        timeContainer.setBackground(Color.black);
        empty.setPreferredSize(new Dimension(RT_PANEL_W, RT_PANEL_H));
        timeContainer.add(empty, BorderLayout.CENTER);

        Box up = Box.createHorizontalBox();
        up.add(param);
        up.add(timeContainer);
        Box bottom = Box.createHorizontalBox();
        bottom.add(msg);

        this.setLayout(new BorderLayout());
        this.add(up, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.CENTER);
    }

    void updateMsgPanel(List<VolumeVO> result, String type) {
        msg.update(result, type);
    }

    void updateTimeSeriesPanel(String code) {
        timeContainer.remove(empty);
        timeContainer.add(timeSeriesPanel, BorderLayout.CENTER);
        timeSeriesPanel.setStockCode(code);
    }

}
