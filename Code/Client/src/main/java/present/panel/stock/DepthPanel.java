package present.panel.stock;

import bl.GetStockDataServiceImpl;
import blservice.GetStockDataService;
import present.charts.DepthLine;
import vo.DeepStockVO;

import javax.swing.*;
import java.util.List;

/**
 * Created by song on 16-9-1.
 *
 * 深度面板
 */
class DepthPanel extends JPanel {
    private JPanel panel;

    private String stockCode;

    DepthPanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;

        getData();
    }

    private void getData() {
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
                }
            }
        };

        worker.execute();
    }

    private void injectData(List<DeepStockVO> deepStockVOList) {
        SwingUtilities.invokeLater(() -> {
            panel.removeAll();
            panel.add(DepthLine.getChart(deepStockVOList));

            panel.revalidate();
            panel.repaint();
        });
    }
}
