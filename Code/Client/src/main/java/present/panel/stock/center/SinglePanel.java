package present.panel.stock.center;

import bl.stock.GetStockDataServiceImpl;
import blservice.stock.GetStockDataService;
import present.component.MyProgressPanel;
import present.component.MyRenderer;
import present.component.MyTable;
import present.component.ProgressListener;
import present.panel.stock.StockPanel;
import util.NumberUtil;
import util.StockUtil;
import util.TimeUtil;
import vo.StockInfoByPer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Y481L on 2016/8/27.
 * <p>
 * 逐笔面板
 */
public class SinglePanel extends CenterPanel implements ProgressListener {

    private SinglePanel panel;

    private String stockCode;

    private StockPanel stockPanel;

    private MyProgressPanel progress;

    private JScrollPane scrollPane;

    public SinglePanel(String stockCode, StockPanel stockPanel) {
        panel = this;
        this.stockCode = stockCode;
        this.stockPanel = stockPanel;

        super.init();

        getData();
    }

    private void createUIComponents() {
        JPanel toolPanel = new JPanel();

        toolPanel.add(new JLabel("当日时间线 → "));

        progress = new MyProgressPanel(this);

        toolPanel.add(progress);

        panel.add(toolPanel, BorderLayout.NORTH);
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
                    stockInfoByPerList = get();
                } catch (Exception e) {
                    stockPanel.displayError();
                    e.printStackTrace();
                }

                injectData(stockInfoByPerList);
            }
        };

        worker.execute();
    }

    private void injectData(List<StockInfoByPer> stockInfoByPerList) {
        SwingUtilities.invokeLater(() -> {
            if (scrollPane != null) {
                panel.remove(scrollPane);
            }

            progress.setPercent(getTimePercent(stockInfoByPerList.get(0).getTime()));

            scrollPane = createTable(stockInfoByPerList);
            panel.add(scrollPane, BorderLayout.CENTER);

            panel.revalidate();
            panel.repaint();
        });
    }

    /**
     * 获得最新交易时间占整个交易日区间的比例
     *
     * @param latestTime 最新交易时间点,以有数据为准
     * @return 占比
     */
    private double getTimePercent(String latestTime) {
        return TimeUtil.getTimePercent(latestTime);
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

    @Override
    public void valueChanged(double percent) {
        System.out.println(percent);
    }

    @Override
    public String getToolTipText(double percent) {
        return "10:10 -- 10:20";
    }
}
