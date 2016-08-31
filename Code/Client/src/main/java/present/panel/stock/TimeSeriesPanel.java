package present.panel.stock;

import bl.GetTimeSeriesDataServiceImpl;
import blservice.GetTimeSeriesDataService;
import present.charts.TimeSeriesChart;
import vo.StockTimeSeriesVO;

import javax.swing.*;
import java.util.List;

/**
 * Created by song on 16-8-28.
 * <p>
 * 分时图面板
 */
class TimeSeriesPanel extends JPanel {
    private JPanel panel;

    private String stockCode;

    TimeSeriesPanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;

        getData();
    }

    /**
     * 加载数据
     */
    private void getData() {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                GetTimeSeriesDataService timeSeriesDataService = new GetTimeSeriesDataServiceImpl();

                return timeSeriesDataService.getData(stockCode);
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void done() {
                try {
                    List timeSeriesVOList = (List) get();
                    injectData(timeSeriesVOList);

                    panel.revalidate();
                    panel.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    private void injectData(List<StockTimeSeriesVO> stockTimeSeriesVOList) {
        SwingUtilities.invokeLater(() -> {
            panel = TimeSeriesChart.getChart(stockTimeSeriesVOList);

            panel.repaint();
        });
    }
}
