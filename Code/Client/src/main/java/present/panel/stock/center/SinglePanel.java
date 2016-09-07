package present.panel.stock.center;

import bl.GetStockDataServiceImpl;
import blservice.GetStockDataService;
import present.panel.stock.MyRenderer;
import present.panel.stock.MyTable;
import present.panel.stock.StockPanel;
import util.NumberUtil;
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
public class SinglePanel extends CenterPanel {

    private SinglePanel panel;

    private String stockCode;

    private StockPanel stockPanel;

    public SinglePanel(String stockCode, StockPanel stockPanel) {
        panel = this;
        this.stockCode = stockCode;
        this.stockPanel = stockPanel;

        super.init();
        getData();
    }

    @Override
    public void getData() {
        SwingWorker<List<StockInfoByPer>, Void> worker = new SwingWorker<List<StockInfoByPer>, Void>() {
            @Override
            protected List<StockInfoByPer> doInBackground() throws Exception {
                GetStockDataService stockDataService = new GetStockDataServiceImpl();

                return stockDataService.getPerStockInfo(stockCode);
            }

            @Override
            protected void done() {
                List<StockInfoByPer> stockInfoByPerList = null;
                try {
                    stockInfoByPerList =  get();
                } catch (Exception e) {
                    stockPanel.displayError();
                    e.printStackTrace();
                }

                injectData(stockInfoByPerList);
            }
        };

        worker.execute();
    }

    private void injectData(List stockInfoByPerList) {
        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = createTable(stockInfoByPerList);

            panel.removeAll();
            panel.add(scrollPane, BorderLayout.WEST);

            panel.revalidate();
            panel.repaint();
        });
    }

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
                    NumberUtil.round(temp.getChange_price(), 3),
                    temp.getVolume(),
                    temp.getTotalNum() / 1e4,
                    StockUtil.getType(temp.getType())
            };
        }

        MyTable table = new MyTable(data, names);
        table.setRenderer(new MyRenderer(2, 5));

        JScrollPane scrollPane = table.createTable();

        scrollPane.setPreferredSize(new Dimension(table.getColumnModel().getTotalColumnWidth() + 28, 500));

        return scrollPane;
    }
}
