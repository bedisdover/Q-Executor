package present.panel.stock;

import present.utils.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by song on 16-8-26.
 * <p>
 * 价格面板，包含当前价格、涨跌额（涨跌幅）、数据时间
 */
class PricePanel extends JPanel {

    private JPanel panel;

    private JLabel price, inc_dec, incNum, incRate, time;

    PricePanel() {
        panel = this;

        init();
        createUIComponents();
    }

    /**
     * 初始化
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(new Color(182, 187, 187));
            panel.setPreferredSize(new Dimension(1, 80));
            panel.setLayout(new BorderLayout());

            panel.revalidate();
        });
    }

    /**
     * 初始化组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                JPanel northPanel = new JPanel();

                northPanel.setBackground(new Color(182, 187, 187));
                northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

                price = new JLabel("23");
                price.setToolTipText("最新价格");
                inc_dec = new JLabel(ImageLoader.increase);
                inc_dec.setBounds(0, 0, 100, 100);
                incNum = new JLabel("234");
                incNum.setToolTipText("涨跌额");
                incRate = new JLabel("(234)");
                incRate.setToolTipText("涨跌幅");

                northPanel.add(price);
                northPanel.add(inc_dec);
                northPanel.add(incNum);
                northPanel.add(incRate);

                panel.add(northPanel, BorderLayout.NORTH);

                panel.revalidate();
            }

            {
                JPanel southPanel = new JPanel();

                southPanel.setBackground(new Color(182, 187, 187));
                southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

                time = new JLabel("2016年08月26日 14:03:21");

                southPanel.add(time);

                panel.add(southPanel, BorderLayout.SOUTH);
            }

            panel.revalidate();
        });
    }
}
