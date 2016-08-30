package present.panel.stock;

import bl.GetTimeSeriesDataServiceImpl;
import blservice.GetTimeSeriesDataService;
import present.charts.TimeSeriesChart;

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

    private TimeSeriesChart timeSeriesChart;

    TimeSeriesPanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;

        getData();
        createUIComponents();
    }

    /**
     * 创建组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            timeSeriesChart = new TimeSeriesChart();

            panel = timeSeriesChart.getChart();
        });
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
                    timeSeriesChart.injectData(timeSeriesVOList);

                    panel.revalidate();
                    panel.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }
}
