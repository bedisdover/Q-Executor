package present.panel.stock.center;

import bl.stock.GetStockDataServiceImpl;
import blservice.stock.GetStockDataService;
import present.component.MyProgressPanel;
import present.component.MyRenderer;
import present.component.MyTable;
import present.panel.stock.StockPanel;
import util.NumberUtil;
import util.StockUtil;
import vo.StockInfoByPer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Dictionary;
import java.util.Hashtable;
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

    private void createUIComponents() {


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
            // 去除加载动画的影响
            if (!(panel.getLayout() instanceof BorderLayout)) {
                panel.removeAll();

                panel.setLayout(new GridBagLayout());
                GridBagConstraints constraints = new GridBagConstraints();

                constraints.gridwidth = 1;
                constraints.gridheight = 0;
                constraints.weightx = 1;
                constraints.weighty = 0;
                panel.add(new JPanel(), constraints);

                constraints.gridwidth = 10;
                constraints.gridheight = 0;
                constraints.weightx = 0;
                constraints.weighty = 1;
                panel.add(createTable(stockInfoByPerList), constraints);

                constraints.gridwidth = 0;
                constraints.gridheight = 0;
                constraints.weightx = 1;
                constraints.weighty = 0;
                panel.add(new JPanel(), constraints);

                constraints.gridwidth = 0;
                constraints.weightx = 1;
                constraints.weighty = 1;
//                panel.add(new JPanel(), constraints);

                panel.revalidate();
                panel.repaint();
            } else {
                JScrollPane scrollPane = createTable(stockInfoByPerList);
                panel.removeAll();

                {
                    JPanel toolPanel = new JPanel();

                    toolPanel.add(new JLabel("当日时间线 → "));

//                    toolPanel.add(createSlider(stockInfoByPerList.get(0).getTime()));
                    toolPanel.add(new MyProgressPanel(this));

                    panel.add(toolPanel, BorderLayout.NORTH);
                }
                panel.add(scrollPane, BorderLayout.CENTER);

                panel.revalidate();
                panel.repaint();

            }
        });
    }

    private JSlider createSlider(String currentTime) {
        JSlider slider = new JSlider();

        Dictionary<Integer, Component> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("9:30"));
        labelTable.put(25, new JLabel("10:30"));
        labelTable.put(50, new JLabel("11:30   13:00"));
        labelTable.put(75, new JLabel("14:00"));
        labelTable.put(100, new JLabel("15:00"));

        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);
//        slider.setEnabled(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("SinglePanel.stateChanged");
            }
        });
        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("SinglePanel.mouseClicked");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("SinglePanel.mouseEntered");
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("SinglePanel.mouseDragged");
            }
        });

        return slider;
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
