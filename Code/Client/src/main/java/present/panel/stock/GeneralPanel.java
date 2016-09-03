package present.panel.stock;

import bl.GetStockDataServiceImpl;
import blservice.GetStockDataService;
import present.charts.GeneralPie;
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
public class GeneralPanel extends JPanel {

    private JPanel panel;

    private String stockCode;

    private JRadioButton radio400, radio500, radio600, radio700, radio800, radio900, radio1000;

    private ButtonGroup buttonGroup;

    private JLabel general_amount, general_volume, total_amount, total_volume;

    private JPanel centerPanel, southPanel;

    private Box labelBox;

    private double totalAmount, totalVolume;

    public GeneralPanel(String stockCode, CurrentDataPanel currentDataPanel) {
        panel = this;
        this.stockCode = stockCode;

        totalAmount = currentDataPanel.getAmount() / 100;
        totalVolume = currentDataPanel.getVolume();

        init();
        createUIComponents();
        getData(0);
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout(0, 5));

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

                JLabel label = new MyLabel("成交量大于等于(≥):");

                radio400 = new JRadioButton("400手");
                radio500 = new JRadioButton("500手");
                radio600 = new JRadioButton("600手");
                radio700 = new JRadioButton("700手");
                radio800 = new JRadioButton("800手");
                radio900 = new JRadioButton("900手");
                radio1000 = new JRadioButton("1000手");

                buttonGroup = new ButtonGroup();

                northPanel.add(label);
                northPanel.add(radio400);
                northPanel.add(radio500);
                northPanel.add(radio600);
                northPanel.add(radio700);
                northPanel.add(radio800);
                northPanel.add(radio900);
                northPanel.add(radio1000);
                buttonGroup.add(radio400);
                buttonGroup.add(radio500);
                buttonGroup.add(radio600);
                buttonGroup.add(radio700);
                buttonGroup.add(radio800);
                buttonGroup.add(radio900);
                buttonGroup.add(radio1000);

                panel.add(northPanel, BorderLayout.NORTH);
            }

            {
                centerPanel = new JPanel(new BorderLayout());

                centerPanel.setPreferredSize(new Dimension(1, 300));

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

                centerPanel.add(labelBox, BorderLayout.EAST);

                panel.add(centerPanel, BorderLayout.CENTER);
            }

            {
                southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                southPanel.setPreferredSize(new Dimension(1, 300));

                panel.add(southPanel, BorderLayout.SOUTH);
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

                return stockDataService.getComStockInfo(stockCode, filterNum);
            }

            @Override
            protected void done() {
                try {
                    List stockInfoByComList = (List) get();

                    injectData(stockInfoByComList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    @SuppressWarnings("unchecked")
    private void injectData(List stockInfoByComList) {
        SwingUtilities.invokeLater(() -> {
            centerPanel.removeAll();
            JPanel chartPanel = GeneralPie.getPieChart(calculateAmount(stockInfoByComList), totalAmount);
            centerPanel.add(chartPanel, BorderLayout.WEST);
            centerPanel.add(labelBox, BorderLayout.EAST);

            southPanel.removeAll();
            southPanel.add(createTable(stockInfoByComList));

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
        String[] names = {"交易时间", "成交价", "价格变动", "成交量(手)", "成交额(万元)", "交易总量", "买卖盘性质"};
        Object[][] data = new Object[stockInfoByComList.size()][names.length];

        StockInfoByCom temp;
        for (int i = 0; i < data.length; i++) {
            temp = stockInfoByComList.get(i);

            data[i] = new Object[]{
                    temp.getTime(),
                    temp.getPrice(),
                    temp.getChange_price(),
                    temp.getVolume(),
                    temp.getVolume() * temp.getPrice() / 1e4,
                    temp.getTotal_number(),
                    temp.getType()
            };
        }

        MyTable table = new MyTable(data, names);
        // 渲染“价格变动”的颜色
        table.setDefaultRenderer(Object.class, new MyRenderer(2));
        JScrollPane scrollPane = table.createTable();

        scrollPane.setPreferredSize(new Dimension(table.getColumnModel().getTotalColumnWidth() + 28, 300));

        return scrollPane;
    }
}
