package present.panel.stock;

import bl.GetStockDataServiceImpl;
import blservice.GetStockDataService;
import util.StockUtil;
import vo.StockInfoByPer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Y481L on 2016/8/27.
 * <p>
 * 逐笔面板
 */
public class SinglePanel extends JPanel {

    private JPanel panel;

    public SinglePanel(String stockCode) {
        panel = this;

        init();
        getData(stockCode);
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout());
        });
    }

    private void getData(String stockCode) {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                GetStockDataService stockDataService = new GetStockDataServiceImpl();

                return stockDataService.getPerStockInfo(stockCode);
            }

            @Override
            protected void done() {
                try {
                    List stockInfoByPerList = (List) get();

                    injectData(stockInfoByPerList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    private void injectData(List stockInfoByPerList) {
        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = createTable(stockInfoByPerList);

            panel.add(scrollPane, BorderLayout.WEST);

            panel.revalidate();
            panel.repaint();
        });
    }

    @SuppressWarnings("unchecked")
    private JScrollPane createTable(List stockInfoByPerList) {
        //字段名称
        String[] names = {"交易时间", "成交价", "价格变动", "成交量(手)", "成交额(万元)", "买卖盘性质"};
        Object[][] data = new Object[stockInfoByPerList.size()][names.length];

        StockInfoByPer temp;
        for (int i = 0; i < data.length; i++) {
            temp = (StockInfoByPer) stockInfoByPerList.get(i);

            data[i] = new Object[]{
                    temp.getTime(),
                    temp.getPrice(),
                    temp.getChange_price(),
                    temp.getVolume(),
                    temp.getTotalNum() / 1e4,
                    StockUtil.getType(temp.getType())
            };
        }

        MyTable table = new MyTable(data, names);
        JScrollPane scrollPane = table.createTable();

        scrollPane.setPreferredSize(new Dimension(table.getColumnModel().getTotalColumnWidth() + 28, 500));

        return scrollPane;
    }
}
