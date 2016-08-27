package present.panel.stock;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-8-26.
 * <p>
 * 数据面板，包含
 * 今  开:	68.98	最  高:	70	    换手率:	1.37%	成交量:	3.62万手
 * 昨  收:	68.42	最  低:	68.51	市盈率TTM:	68.02	成交额:	2.50亿元
 * 振  幅:	2.18%	市净率:	20.87	每股收益:	0.267元	总市值:	32.87亿元
 */
class DataPanel extends JPanel {
    private JPanel panel;

    private MyLabel labelOpen, labelHigh, labelTurnOver, labelAmount,
            labelClose, labelLow, labelPe, labelVolume, labelAmplitude,
            labelPb;

    private MyLabel open, high, turnOver, amount, close, low,
            pe, volume, amplitude, pb;

    DataPanel() {
        panel = this;

        init();
        createUIComponents();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(new Color(0xcccccc));
            panel.setPreferredSize(new Dimension(1, 320));
            panel.setLayout(new BorderLayout());

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                Box westPanel = Box.createVerticalBox();

                labelOpen = new MyLabel("今   开");
                labelClose = new MyLabel("昨   收");
                labelHigh = new MyLabel("最   高");
                labelLow = new MyLabel("最   低");
                labelAmplitude = new MyLabel("振   幅");
                labelTurnOver = new MyLabel("换手率");
                labelAmount = new MyLabel("成交量");
                labelVolume = new MyLabel("成交额");
                labelPe = new MyLabel("市盈率");
                labelPb = new MyLabel("市净率");

                westPanel.add(labelOpen);
                westPanel.add(labelClose);
                westPanel.add(labelHigh);
                westPanel.add(labelLow);
                westPanel.add(labelAmplitude);
                westPanel.add(labelTurnOver);
                westPanel.add(labelAmount);
                westPanel.add(labelVolume);
                westPanel.add(labelPe);
                westPanel.add(labelPb);

                panel.add(westPanel, BorderLayout.WEST);
            }

            {
                Box eastPanel = Box.createVerticalBox();

                open = new MyLabel("23.43");
                close = new MyLabel("12.54");
                high = new MyLabel("22.21");
                low = new MyLabel("20.12");
                amplitude = new MyLabel("1.67%");
                turnOver = new MyLabel("2.43%");
                amount = new MyLabel("4.41万手");
                volume = new MyLabel("3.05亿元");
                pe = new MyLabel("68.02");
                pb = new MyLabel("20.87");

                eastPanel.add(open);
                eastPanel.add(close);
                eastPanel.add(high);
                eastPanel.add(low);
                eastPanel.add(amplitude);
                eastPanel.add(turnOver);
                eastPanel.add(amount);
                eastPanel.add(volume);
                eastPanel.add(pe);
                eastPanel.add(pb);

                panel.add(eastPanel, BorderLayout.EAST);
            }
        });
    }
}
