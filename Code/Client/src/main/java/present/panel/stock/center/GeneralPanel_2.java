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
public class GeneralPanel_2 extends CenterPanel {

    private GeneralPanel_2 panel;

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

    public GeneralPanel_2(String stockCode, CurrentDataPanel currentDataPanel, StockPanel stockPanel) {
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
            panel.setLayout(new GridBagLayout());

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;

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

                constraints.gridwidth = 1;
                constraints.weightx = 1;
                constraints.weighty = 0;
                panel.add(new JPanel(), constraints);

                constraints.gridwidth = 10;
                constraints.weightx = 0;
                constraints.weighty = 0;
                panel.add(radioPanel, constraints);

                constraints.gridwidth = 0;
                constraints.weightx = 1;
                constraints.weighty = 0;
                panel.add(new JPanel(), constraints);
            }

            {
                chartPanel = new JPanel(new BorderLayout());

                chartPanel.setPreferredSize(new Dimension(1, 200));

                labelBox = Box.createVerticalBox();
                labelBox.setPreferredSize(new Dimension(400, 1));

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

                constraints.gridwidth = 1;
                constraints.weightx = 0;
                constraints.weighty = 0;
                panel.add(new JPanel(), constraints);

                constraints.gridwidth = 10;
                constraints.weightx = 0;
                constraints.weighty = 0;
                panel.add(chartPanel, constraints);

                constraints.gridwidth = 0;
                constraints.weightx = 1;
                constraints.weighty = 0;
                panel.add(new JPanel(), constraints);
            }

            {
                centerPanel = new JPanel(new BorderLayout());

                constraints.gridwidth = 1;
                constraints.weightx = 0;
                constraints.weighty = 1;
                panel.add(new JPanel(), constraints);

                constraints.gridwidth = 10;
                constraints.weightx = 0;
                constraints.weighty = 1;
                panel.add(centerPanel, constraints);

                constraints.gridwidth = 0;
                constraints.weightx = 1;
                constraints.weighty = 1;
                panel.add(new JPanel(), constraints);
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

            String[] titles = new String[]{"大单", "其它"};
            double[] values = new double[]{calculateAmount(stockInfoByComList), totalAmount};
            JPanel chart = PieFactory.getPieChart(titles, values);

            String[] titles_1 = new String[]{"买盘", "卖盘", "中性盘"};
            double[] values_1 = calculateSell(stockInfoByComList);
            JPanel chart_1 = PieFactory.getPieChart(titles_1, values_1);

            chartPanel.add(chart, BorderLayout.WEST);
            chartPanel.add(labelBox, BorderLayout.CENTER);
            chartPanel.add(chart_1, BorderLayout.EAST);

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

    /**
     * 计算买盘、卖盘、中性盘的成交量
     */
    private double[] calculateSell(List<StockInfoByCom> stockInfoByComList) {
        double[] result = new double[]{0, 0, 0};

        for (StockInfoByCom stockInfoByCom : stockInfoByComList) {
            result[stockInfoByCom.getTypeNum()] += stockInfoByCom.getVolume();
        }

        return result;
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

        return table.createTable();
    }
}
