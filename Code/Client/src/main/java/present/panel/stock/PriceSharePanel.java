package present.panel.stock;

import bl.GetStockDataServiceImpl;
import blservice.GetStockDataService;
import present.charts.PriceSharePlot;
import vo.StockInfoByPrice;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

import java.util.List;

/**
 * Created by Y481L on 2016/8/27.
 * <p>
 * 分价面板
 */
public class PriceSharePanel extends JPanel {

    private JPanel panel;

    private String stockCode;

    private JPanel southPanel;

    public PriceSharePanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;

        init();
        createUIComponents();
        getData();
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

            southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            southPanel.setPreferredSize(new Dimension(1, 200));

            panel.add(southPanel, BorderLayout.SOUTH);
        });
    }

    private void getData() {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                GetStockDataService stockDataService = new GetStockDataServiceImpl();

                return stockDataService.getStockInfoByPrice(stockCode);
            }

            @Override
            protected void done() {
                try {
                    List stockInfoByPriceList = (List) get();

                    injectData(stockInfoByPriceList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    @SuppressWarnings("unchecked")
    private void injectData(List stockInfoByPriceList) {
        SwingUtilities.invokeLater(() -> {
            southPanel.removeAll();
            southPanel.add(createTable(stockInfoByPriceList));

            panel.revalidate();
            panel.repaint();
        });
    }

    private JScrollPane createTable(List<StockInfoByPrice> stockInfoByPriceList) {
        //字段名称
        String[] names = {"成交价", "成交量(手)", "占比(%)"};
        Object[][] data = new Object[stockInfoByPriceList.size()][names.length];

        StockInfoByPrice temp;
        for (int i = 0; i < data.length; i++) {
            temp = stockInfoByPriceList.get(i);

            if (temp.getPrice() == 0) {
                stockInfoByPriceList.remove(i);
                continue;
            }

            data[i] = new Object[]{
                    temp.getPrice(),
                    temp.getTrunover(),
                    temp.getPercent() * 100
            };
        }

        MyTable table = new MyTable(data, names);
        JScrollPane scrollPane = table.createTable();

        scrollPane.setPreferredSize(new Dimension(table.getColumnModel().getTotalColumnWidth() + 28, 300));

        return scrollPane;
    }
}
