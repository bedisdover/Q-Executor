package present.panel.stock;

import bl.GetStockDataServiceImpl;
import blservice.GetStockDataService;
import present.charts.KLine;
import vo.StockBasicInfoVO;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 * <p>
 * 股票界面容器
 */
public class StockPanel extends JPanel {

    JPanel panel;

    JPanel centerPanel;

    String stockCode;

    public StockPanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;

        init();
        getData();
        createUIComponents();
        createCenterPanel();
    }

    /**
     * 初始化panel
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout());

            panel.revalidate();
        });
    }

    /**
     * 创建组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            NamePanel namePanel = new NamePanel(stockCode);
            panel.add(namePanel, BorderLayout.NORTH);

            {
                CurrentDataPanel currentDataPanel = new CurrentDataPanel();
                JScrollPane scrollPane = new JScrollPane(currentDataPanel);
                scrollPane.setPreferredSize(new Dimension(200, 1));

                panel.add(scrollPane, BorderLayout.WEST);
            }
        });
    }

    /**
     * 创建中部面板
     */
    void createCenterPanel() {
        SwingUtilities.invokeLater(() -> {
            panel.add(new KLine().getKLine("sh600000"), BorderLayout.CENTER);
            panel.revalidate();
        });
    }

    private void getData() {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                GetStockDataService getStockData = new GetStockDataServiceImpl();

                return getStockData.getBasicInfo("sh600008");
            }

            @Override
            protected void done() {
                try {
                    StockBasicInfoVO stockBasicInfoVO = (StockBasicInfoVO) get();
                    System.out.println(stockBasicInfoVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }
}