package present.panel.stock.center;

import bl.stock.GetStockDataServiceImpl;
import blservice.stock.GetStockDataService;
import present.charts.DepthLine;
import present.panel.stock.StockPanel;
import vo.DeepStockVO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by song on 16-9-1.
 * <p>
 * 深度面板
 */
public class DepthPanel extends CenterPanel {
    private String stockCode;

    private StockPanel stockPanel;

    public DepthPanel(String stockCode, StockPanel stockPanel) {
        this.stockCode = stockCode;
        this.stockPanel = stockPanel;

        super.init();
        getData();
    }

    @Override
    public void getData() {
        SwingWorker<List<DeepStockVO>, Void> worker = new SwingWorker<List<DeepStockVO>, Void>() {
            @Override
            protected List<DeepStockVO> doInBackground() throws Exception {
                GetStockDataService stockDataService = new GetStockDataServiceImpl();

                return stockDataService.getStockDepth(stockCode);
            }

            @Override
            protected void done() {
                try {
                    injectData(get());
                } catch (Exception e) {
                    e.printStackTrace();
                    stockPanel.displayError();
                }
            }
        };

        worker.execute();
    }

    private void injectData(List<DeepStockVO> deepStockVOList) {
        SwingUtilities.invokeLater(() -> {
            this.removeAll();
            this.add(DepthLine.getChart(deepStockVOList), BorderLayout.CENTER);

            this.revalidate();
            this.repaint();
        });
    }
}
