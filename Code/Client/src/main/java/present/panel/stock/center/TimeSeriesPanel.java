package present.panel.stock.center;

import bl.stock.GetTimeSeriesDataServiceImpl;
import blservice.stock.GetTimeSeriesDataService;
import present.charts.TimeSeriesChart;
import present.panel.error.ErrorPanel;
import present.panel.loading.LoadingPanel;
import present.panel.stock.StockPanel;
import vo.StockTimeSeriesVO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 16-8-28.
 * <p>
 * 分时图面板
 */
public class TimeSeriesPanel extends CenterPanel {
    private TimeSeriesPanel panel;

    private String stockCode;

    private StockPanel stockPanel;

    public TimeSeriesPanel() {
        panel = this;
    }

    public TimeSeriesPanel(String stockCode, StockPanel stockPanel) {
        panel = this;
        this.stockCode = stockCode;
        this.stockPanel = stockPanel;

        createUIComponents();
        getData();
    }

    public void setStockCode(String stockCode) {
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
    public void getData() {
        SwingWorker<List<StockTimeSeriesVO>, Void> worker = new SwingWorker<List<StockTimeSeriesVO>, Void>() {
            @Override
            protected List<StockTimeSeriesVO> doInBackground() {
                GetTimeSeriesDataService timeSeriesDataService = new GetTimeSeriesDataServiceImpl();

                List<StockTimeSeriesVO> timeSeriesVOList = new ArrayList<>();
                try {
                    timeSeriesVOList = timeSeriesDataService.getData(stockCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (stockPanel != null) {
                        stockPanel.displayError();
                    } else {
                        panel.add(new ErrorPanel(panel));
                    }
                }

                return timeSeriesVOList;
            }

            @Override
            protected void done() {
                try {
                    List<StockTimeSeriesVO> timeSeriesVOList = get();
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
