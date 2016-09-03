package present.panel.stock;

import bl.GetTimeSeriesDataServiceImpl;
import blservice.GetTimeSeriesDataService;
import org.json.JSONException;
import present.charts.TimeSeriesChart;
import present.panel.loading.LoadingPanel;
import vo.StockTimeSeriesVO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
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

        createUIComponents();
        getData();
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout());

            panel.add(new LoadingPanel(), BorderLayout.CENTER);

            panel.revalidate();
        });
    }

    /**
     * 加载数据
     */
    private void getData() {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground()  {
                GetTimeSeriesDataService timeSeriesDataService = new GetTimeSeriesDataServiceImpl();

                List timeSeriesVOList = new ArrayList();
                try {
                    timeSeriesVOList = timeSeriesDataService.getData(stockCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return timeSeriesVOList;
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
            panel.removeAll();
            panel.add(TimeSeriesChart.getChart(stockTimeSeriesVOList));

            panel.revalidate();
            panel.repaint();
        });
    }
}
