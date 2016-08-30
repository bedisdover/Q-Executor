package present.panel.stock;

import bl.GetStockDataServiceImpl;
import blservice.GetStockDataService;
import present.charts.GeneralPie;
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

    private JRadioButton radio400, radio600, radio800, radio1000;

    private ButtonGroup buttonGroup;

    private JLabel general_amount, general_volume, total_amount, total_volume;

    private JPanel centerPanel, southPanel;

    public GeneralPanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;

        init();
        createUIComponents();
        getData();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(new Color(0xeeeeee));
            panel.setLayout(new BorderLayout(0, 20));

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

                JLabel label = new MyLabel("成交量大于等于(≥):");

                radio400 = new JRadioButton("400手");
                radio600 = new JRadioButton("600手");
                radio800 = new JRadioButton("800手");
                radio1000 = new JRadioButton("1000手");

                buttonGroup = new ButtonGroup();

                northPanel.add(label);
                northPanel.add(radio400);
                northPanel.add(radio600);
                northPanel.add(radio800);
                northPanel.add(radio1000);
                buttonGroup.add(radio400);
                buttonGroup.add(radio600);
                buttonGroup.add(radio800);
                buttonGroup.add(radio1000);

                panel.add(northPanel, BorderLayout.NORTH);
            }

            {
                centerPanel = new JPanel(new BorderLayout());

                centerPanel.setPreferredSize(new Dimension(1, 200));

                Box labelBox = Box.createVerticalBox();

                general_amount = new MyLabel("大单成交量: --");
                total_amount = new MyLabel("总成交量: --");
                general_volume = new MyLabel("大单成交额: --");
                total_volume = new MyLabel("总成交额: --");

                labelBox.add(general_amount);
                labelBox.add(total_amount);
                labelBox.add(new MyLabel("  "));
                labelBox.add(general_volume);
                labelBox.add(total_volume);

                centerPanel.add(labelBox, BorderLayout.CENTER);

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
        radio400.addActionListener(e -> {
            System.out.println("GeneralPanel.actionPerformed");
        });

        radio600.addActionListener(e -> {
            System.out.println(600);
        });

        radio800.addActionListener(e -> {
            System.out.println(800);
        });

        radio1000.addActionListener(e -> {
            System.out.println(1000);
        });
    }

    private void getData() {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                GetStockDataService stockDataService = new GetStockDataServiceImpl();

                return stockDataService.getComStockInfo(stockCode);
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
            centerPanel.add(GeneralPie.getPieChart(calculateAmount(stockInfoByComList), 23),
                    BorderLayout.WEST);

            southPanel.removeAll();
            southPanel.add(createTable(stockInfoByComList));

            southPanel.revalidate();
            southPanel.repaint();

            general_amount.setText("大单成交量: " + calculateAmount(stockInfoByComList));
            general_volume.setText("大单成交额: " + calculateVolume(stockInfoByComList));
            total_amount.setText("总成交量: " + "234134");
            total_volume.setText("总成交额: " + "234131324");

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

        return result;
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
                    temp.getVolume() * temp.getPrice(),
                    temp.getTotal_number(),
                    temp.getType()
            };
        }


        return new MyTable(data, names).createTable();
    }
}
