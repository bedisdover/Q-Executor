package present.panel.stock;

import present.charts.PriceSharePlot;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/27.
 * <p>
 * 分价面板
 */
public class PriceSharePanel extends JPanel {

    private JPanel panel;

    private String stockCode;

    public PriceSharePanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;

        init();
        createUIComponents();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout());

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            panel.add(new PriceSharePlot().getPlotChart(stockCode), BorderLayout.CENTER);

            JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            southPanel.setPreferredSize(new Dimension(1, 200));
            southPanel.add(createTable());
            panel.add(southPanel, BorderLayout.SOUTH);
        });
    }

    private JScrollPane createTable() {
        Object[][] playerInfo = {
                {"阿呆", 66, 32, 98, false},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
        };

        //字段名称
        String[] Names = {"交易时间", "成交价", "成交量(手)", "成交额(万元)", "买卖盘性质"};

        return new MyTable(playerInfo, Names).createTable();
    }
}
