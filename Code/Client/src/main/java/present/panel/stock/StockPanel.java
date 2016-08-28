package present.panel.stock;

import bl.GetStockDataServiceImpl;
import blservice.GetStockDataService;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import present.charts.KLine;
import vo.StockBasicInfoVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Y481L on 2016/8/25.
 * <p>
 * 股票界面容器
 */
public class StockPanel extends JPanel {

    private JPanel panel;

    private NamePanel namePanel;

    private CurrentDataPanel currentDataPanel;

    /**
     * 中部面板
     */
    private JComponent centerPanel;

    private JTabbedPane kLinePanel;

    private JPanel timeSeriesPanel, generalPanel, singlePanel, priceSharePanel;

    private String stockCode;

    public StockPanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;

        init();
        getData();
        createUIComponents();
    }

    /**
     * 初始化panel
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout());

            panel.revalidate();
        });
    }

    /**
     * 创建组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            namePanel = new NamePanel();
            panel.add(namePanel, BorderLayout.NORTH);

            {
                currentDataPanel = new CurrentDataPanel();
                JScrollPane scrollPane = new JScrollPane(currentDataPanel);
                scrollPane.setPreferredSize(new Dimension(200, 1));

                panel.add(scrollPane, BorderLayout.WEST);
            }

            centerPanel = kLinePanel = new KLine().getKLine(stockCode);
            panel.add(centerPanel, BorderLayout.CENTER);
        });
    }

    /**
     * 创建中部面板
     */
    private void createCenterPanel(String panelType) {
        SwingUtilities.invokeLater(() -> {
            panel.remove(centerPanel);

            switch (panelType) {
                case "KLinePanel":
                    centerPanel = kLinePanel;
                    break;
                case "TimeSeriesPanel":
                    if (timeSeriesPanel == null) {
                        timeSeriesPanel = new TimeSeriesPanel(stockCode);
                    }

                    centerPanel = timeSeriesPanel;
                    break;
                case "GeneralPanel":
                    if (generalPanel == null) {
                        generalPanel = new GeneralPanel(stockCode);
                    }

                    centerPanel = generalPanel;
                    break;
                case "SinglePanel":
                    if (singlePanel == null) {
                        singlePanel = new SinglePanel(stockCode);
                    }

                    centerPanel = singlePanel;
                    break;
                case "PriceSharePanel":
                    if (priceSharePanel == null) {
                        priceSharePanel = new PriceSharePanel(stockCode);
                    }

                    centerPanel = priceSharePanel;
                    break;
            }

            panel.add(centerPanel, BorderLayout.CENTER);

            panel.revalidate();
            panel.repaint();
        });
    }

    /**
     * 获取数据
     */
    private void getData() {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                GetStockDataService getStockData = new GetStockDataServiceImpl();

                return getStockData.getBasicInfo(stockCode);
            }

            @Override
            protected void done() {
                try {
                    StockBasicInfoVO stockBasicInfoVO = (StockBasicInfoVO) get();

                    namePanel.setName(stockBasicInfoVO.getName());
                    currentDataPanel.setBasicInfo(stockBasicInfoVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }


    /**
     * Created by song on 16-8-26.
     * <p>
     * 名称面板
     */
    private class NamePanel extends JPanel {

        private JPanel panel;

        private JLabel labelName, labelCode;

        private JButton btn_kLine, btn_TimeSeries, btn_general, btn_single, btn_priceShare;

        private JButton portrait;

        NamePanel() {
            panel = this;

            init();
            createUIComponents();
        }

        /**
         * 初始化
         */
        private void init() {
            SwingUtilities.invokeLater(() -> {
                panel.setBackground(new Color(0xeeeeee));
                panel.setPreferredSize(new Dimension(1, 50));
                panel.setLayout(new BorderLayout());

                panel.revalidate();
            });
        }

        /**
         * 初始化组件
         */
        private void createUIComponents() {
            SwingUtilities.invokeLater(() -> {
                {
                    JPanel westPanel = new JPanel();

                    westPanel.setBackground(new Color(0xeeeeee));
                    westPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));

                    labelName = new JLabel("浦发银行");
                    labelName.setFont(new Font("微软雅黑", Font.PLAIN, 20));

                    labelCode = new JLabel("(" + stockCode + ")");
                    labelCode.setFont(new Font("微软雅黑", Font.PLAIN, 16));

                    westPanel.add(labelName);
                    westPanel.add(labelCode);

                    panel.add(westPanel, BorderLayout.WEST);
                }
                {
                    JPanel eastPanel = new JPanel();

                    eastPanel.setBackground(new Color(0xeeeeee));
                    eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

                    btn_kLine = new JButton("K 线");
                    btn_TimeSeries = new JButton("分时");
                    btn_general = new JButton("大单");
                    btn_single = new JButton("逐笔");
                    btn_priceShare = new JButton("分价");

                    portrait = new JButton("添加自选");
                    portrait.setPreferredSize(new Dimension(80, 30));
                    portrait.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));

                    eastPanel.add(btn_kLine);
                    eastPanel.add(btn_TimeSeries);
                    eastPanel.add(btn_general);
                    eastPanel.add(btn_single);
                    eastPanel.add(btn_priceShare);
                    eastPanel.add(portrait);

                    panel.add(eastPanel, BorderLayout.EAST);

                    panel.revalidate();
                }

                addListeners();
            });
        }

        /**
         * 添加事件监听
         */
        private void addListeners() {
            btn_kLine.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("KLinePanel");
                }
            });

            btn_TimeSeries.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("TimeSeriesPanel");
                }
            });

            btn_general.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("GeneralPanel");
                }
            });

            btn_single.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("SinglePanel");
                }
            });

            btn_priceShare.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("PriceSharePanel");
                }
            });
        }

        public void setName(String name) {
            SwingUtilities.invokeLater(() -> labelName.setText(name));
        }
    }
}