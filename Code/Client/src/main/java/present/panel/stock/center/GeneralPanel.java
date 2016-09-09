package present.panel.stock.center;

import bl.stock.GetStockDataServiceImpl;
import blservice.stock.GetStockDataService;
import present.charts.PieFactory;
import present.panel.stock.MyLabel;
import present.panel.stock.MyRenderer;
import present.panel.stock.MyTable;
import present.panel.stock.StockPanel;
import present.panel.stock.west.CurrentDataPanel;
import util.NumberUtil;
import vo.StockInfoByCom;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Y481L on 2016/8/27.
 * <p>
 * 大单面板
 */
public class GeneralPanel extends CenterPanel {

    private GeneralPanel panel;

    private String stockCode;

    private JRadioButton radio400, radio500, radio600, radio700, radio800, radio900, radio1000;

    private ButtonGroup buttonGroup;

    private JLabel general_amount, general_volume, total_amount, total_volume;

    private JPanel chartPanel, centerPanel;

    private Box labelBox;

    private double totalAmount, totalVolume;

    /**
     * 当前选择范围
     */
    private double rangeNum = 0;

    private StockPanel stockPanel;

    public GeneralPanel(String stockCode, CurrentDataPanel currentDataPanel, StockPanel stockPanel) {
        panel = this;
        this.stockCode = stockCode;
        this.stockPanel = stockPanel;

        totalAmount = currentDataPanel.getAmount() / 100;
        totalVolume = currentDataPanel.getVolume();

        init();
        createUIComponents();
        getData(rangeNum);
    }

    protected void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout(0, 5));

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            JPanel northPanel = new JPanel(new BorderLayout());
            {
                // 选项面板
                JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

                JLabel label = new MyLabel("成交量大于等于(≥):");

                radio400 = new JRadioButton("400手");
                radio500 = new JRadioButton("500手");
                radio600 = new JRadioButton("600手");
                radio700 = new JRadioButton("700手");
                radio800 = new JRadioButton("800手");
                radio900 = new JRadioButton("900手");
                radio1000 = new JRadioButton("1000手");

                buttonGroup = new ButtonGroup();

                radioPanel.add(label);
                radioPanel.add(radio400);
                radioPanel.add(radio500);
                radioPanel.add(radio600);
                radioPanel.add(radio700);
                radioPanel.add(radio800);
                radioPanel.add(radio900);
                radioPanel.add(radio1000);
                buttonGroup.add(radio400);
                buttonGroup.add(radio500);
                buttonGroup.add(radio600);
                buttonGroup.add(radio700);
                buttonGroup.add(radio800);
                buttonGroup.add(radio900);
                buttonGroup.add(radio1000);

                northPanel.add(radioPanel, BorderLayout.NORTH);
            }

            {
                chartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                labelBox = Box.createVerticalBox();

                general_amount = new MyLabel("大单成交量: --");
                total_amount = new MyLabel("总成交量: --");
                general_volume = new MyLabel("大单成交额: --");
                total_volume = new MyLabel("总成交额: --");

                labelBox.add(new MyLabel("  "));
                labelBox.add(new MyLabel("  "));
                labelBox.add(general_amount);
                labelBox.add(new MyLabel("  "));
                labelBox.add(total_amount);
                labelBox.add(new MyLabel("  "));
                labelBox.add(new MyLabel("  "));
                labelBox.add(general_volume);
                labelBox.add(new MyLabel("  "));
                labelBox.add(total_volume);

                chartPanel.add(labelBox, BorderLayout.EAST);

                northPanel.add(chartPanel, BorderLayout.CENTER);
            }

            panel.add(northPanel, BorderLayout.NORTH);

            {
                centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                panel.add(centerPanel, BorderLayout.CENTER);
            }

            addListeners();
        });
    }

    private void addListeners() {
        radio400.addActionListener(e -> getData(400));

        radio500.addActionListener(e -> getData(500));

        radio600.addActionListener(e -> getData(600));

        radio700.addActionListener(e -> getData(700));

        radio800.addActionListener(e -> getData(800));

        radio900.addActionListener(e -> getData(900));

        radio1000.addActionListener(e -> getData(1000));
    }

    @Override
    public void getData() {
        getData(rangeNum);
    }

    /**
     * 获取大单数据
     * @param filterNum 筛选条件，成交量的范围
     */
    private void getData(double filterNum) {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                GetStockDataService stockDataService = new GetStockDataServiceImpl();

                if (filterNum == 0) {
                    return stockDataService.getComStockInfo(stockCode);
                }

                rangeNum = filterNum;

                return stockDataService.getComStockInfo(stockCode, filterNum);
            }

            @Override
            protected void done() {
                try {
                    List stockInfoByComList = (List) get();

                    injectData(stockInfoByComList);
                } catch (Exception e) {
                    e.printStackTrace();
                    stockPanel.displayError();
                }
            }
        };

        worker.execute();
    }

    @SuppressWarnings("unchecked")
    private void injectData(List stockInfoByComList) {
        SwingUtilities.invokeLater(() -> {
            chartPanel.removeAll();

//            JPanel chart = PieFactory.getPieChart(calculateAmount(stockInfoByComList), totalAmount);
//            chart.setPreferredSize(new Dimension(200, 200));
//            this.chartPanel.add(chart, BorderLayout.WEST);
//            this.chartPanel.add(labelBox, BorderLayout.EAST);

            centerPanel.removeAll();
            centerPanel.add(createTable(stockInfoByComList), BorderLayout.CENTER);

            general_amount.setText("大单成交量: " +
                    NumberUtil.transferUnit(calculateAmount(stockInfoByComList)) + "手");
            general_volume.setText("大单成交额: " +
                    NumberUtil.transferUnit(calculateVolume(stockInfoByComList)) + "元");


            total_amount.setText("总成交量: " + NumberUtil.transferUnit(totalAmount) + "手");
            total_volume.setText("总成交额: " + NumberUtil.transferUnit(totalVolume) + "元");

            panel.revalidate();
            panel.repaint();
        });
    }

    private double calculateAmount(List<StockInfoByCom> stockInfoByComList) {
        double result = 0;

        for (StockInfoByCom stockInfoByCom : stockInfoByComList) {
            result += stockInfoByCom.getVolume();
        }

        return result;
    }

    private double calculateVolume(List<StockInfoByCom> stockInfoByComList) {
        double result = 0;

        for (StockInfoByCom stockInfoByCom : stockInfoByComList) {
            result += stockInfoByCom.getVolume() * stockInfoByCom.getPrice();
        }

        return Math.round(result * 100) / 100;
    }

    private JScrollPane createTable(List<StockInfoByCom> stockInfoByComList) {
        //字段名称
        String[] names = {"交易时间", "成交价", "价格变动", "成交量(手)", "成交额(万元)", "买卖盘性质"};
        Object[][] data = new Object[stockInfoByComList.size()][names.length];

        StockInfoByCom temp;
        for (int i = 0; i < data.length; i++) {
            temp = stockInfoByComList.get(i);

            data[i] = new Object[]{
                    temp.getTime(),
                    temp.getPrice(),
                    NumberUtil.round(temp.getChange_price(), 3),
                    temp.getVolume(),
                    NumberUtil.round(temp.getVolume() * temp.getPrice() / 1e4),
                    temp.getType()
            };
        }

        MyTable table = new MyTable(data, names);
        // 渲染“价格变动”的颜色
        table.setRenderer(new MyRenderer(2, 5));

        JScrollPane scrollPane = table.createTable();

        scrollPane.setPreferredSize(new Dimension(table.getColumnModel().getTotalColumnWidth() + 28, 300));

        return scrollPane;
    }
}
