package present.panel.stock;

import bl.GetStockDataServiceImpl;
import bl.SelfSelectServiceImpl;
import blservice.GetStockDataService;
import blservice.SelfSelectService;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import present.charts.KLine;
import present.panel.account.LoginPanel;
import present.panel.error.ErrorPanel;
import vo.NowTimeSelectedStockInfoVO;
import vo.StockBasicInfoVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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

    private JPanel timeSeriesPanel, depthPanel, generalPanel, singlePanel, priceSharePanel;

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
                currentDataPanel = new CurrentDataPanel(stockCode);
                JScrollPane scrollPane = new JScrollPane(currentDataPanel);
                scrollPane.setPreferredSize(new Dimension(200, 1));

                panel.add(scrollPane, BorderLayout.WEST);
            }

            try {
                centerPanel = kLinePanel = new KLine().getKLine(stockCode);
            } catch (Exception e) {
                e.printStackTrace();
            }

            panel.add(centerPanel, BorderLayout.CENTER);
        });
    }

    /**
     * 创建中部面板
     */
    private void createCenterPanel(final String panelType) {
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
                case "DepthPanel":
                    if (depthPanel == null) {
                        depthPanel = new DepthPanel(stockCode);
                    }

                    centerPanel = depthPanel;
                    break;
                case "GeneralPanel":
                    if (generalPanel == null) {
                        generalPanel = new GeneralPanel(stockCode, currentDataPanel);
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
                case "ErrorPanel":
                    centerPanel = new ErrorPanel();
                    break;
            }

            panel.add(centerPanel, BorderLayout.CENTER);

            panel.revalidate();
            panel.repaint();
        });
    }

    /**
     * 获取基本数据，用于设置namePanel及currentDataPanel中的BasicInfoPanel
     */
    private void getData() {
        SwingWorker<StockBasicInfoVO, Void> worker = new SwingWorker<StockBasicInfoVO, Void>() {
            @Override
            protected StockBasicInfoVO doInBackground() throws Exception {
                GetStockDataService stockDataService = new GetStockDataServiceImpl();

                StockBasicInfoVO stockBasicInfoVO = null;
                try {
                    stockBasicInfoVO = stockDataService.getBasicInfo(stockCode);
                } catch (Exception e) {
                    createCenterPanel("ErrorPanel");
                    e.printStackTrace();
                }

                return stockBasicInfoVO;
            }

            @Override
            protected void done() {
                try {
                    StockBasicInfoVO stockBasicInfoVO = get();

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

        private JButton btn_kLine, btn_TimeSeries, btn_depth, btn_general, btn_single, btn_priceShare;

        private JButton portrait;

        NamePanel() {
            panel = this;

            init();
            createUIComponents();
            getAllPortrait();
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

                    labelName = new JLabel("");
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
                    setButtonStyle(btn_kLine);
                    btn_TimeSeries = new JButton("分时");
                    btn_depth = new JButton("深度");
                    btn_general = new JButton("大单");
                    btn_single = new JButton("逐笔");
                    btn_priceShare = new JButton("分价");

                    portrait = new JButton("添加自选");
                    portrait.setPreferredSize(new Dimension(90, 30));
                    portrait.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));

                    eastPanel.add(btn_kLine);
                    eastPanel.add(btn_TimeSeries);
                    eastPanel.add(btn_depth);
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
                    resetButtonStyle();
                    setButtonStyle(btn_kLine);
                }
            });

            btn_TimeSeries.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("TimeSeriesPanel");
                    resetButtonStyle();
                    setButtonStyle(btn_TimeSeries);
                }
            });

            btn_depth.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("DepthPanel");
                    resetButtonStyle();
                    setButtonStyle(btn_depth);
                }
            });

            btn_general.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("GeneralPanel");
                    resetButtonStyle();
                    setButtonStyle(btn_general);
                }
            });

            btn_single.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("SinglePanel");
                    resetButtonStyle();
                    setButtonStyle(btn_single);
                }
            });

            btn_priceShare.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createCenterPanel("PriceSharePanel");
                    resetButtonStyle();
                    setButtonStyle(btn_priceShare);
                }
            });

            portrait.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!LoginPanel.IS_LOGIN) {
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showConfirmDialog(StockPanel.this, "尚未登录，请先登录", "登录", JOptionPane.CLOSED_OPTION));

                        return;
                    }

                    if (portrait.getText().equals("添加自选")) {
                        addPortrait();
                    } else {
                        removePortrait();
                    }
                }
            });
        }

        private void getAllPortrait() {
            if (LoginPanel.IS_LOGIN) {
                SwingWorker<List<NowTimeSelectedStockInfoVO>, Void> worker =
                        new SwingWorker<List<NowTimeSelectedStockInfoVO>, Void>() {
                            @Override
                            protected List<NowTimeSelectedStockInfoVO> doInBackground() throws Exception {
                                SelfSelectService selfSelectService = new SelfSelectServiceImpl();

                                return selfSelectService.getUserSelectedStock(LoginPanel.LOGIN_USER, LoginPanel.LOGIN_PW);
                            }

                            @Override
                            protected void done() {
                                try {
                                    List<NowTimeSelectedStockInfoVO> stockList = get();

                                    for (NowTimeSelectedStockInfoVO aStockList : stockList) {
                                        if (aStockList.getGid().equals(stockCode)) {
                                            portrait.setText("取消自选");
                                            portrait.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                worker.execute();
            }
        }

        /**
         * 添加自选
         */
        private void addPortrait() {
            try {
                SelfSelectService selfSelect = new SelfSelectServiceImpl();
                selfSelect.addUserSelectedStock(stockCode, LoginPanel.LOGIN_USER, LoginPanel.LOGIN_PW);

                portrait.setText("取消自选");
                portrait.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 取消自选
         */
        private void removePortrait() {
            try {
                SelfSelectService selfSelect = new SelfSelectServiceImpl();
                selfSelect.deleteUserSelectedStock(stockCode, LoginPanel.LOGIN_USER, LoginPanel.LOGIN_PW);

                portrait.setText("添加自选");
                portrait.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void setButtonStyle(JButton button) {
            button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        }

        /**
         * 重置按钮样式
         */
        private void resetButtonStyle() {
            btn_kLine.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
            btn_TimeSeries.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
            btn_depth.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
            btn_general.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
            btn_single.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
            btn_priceShare.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
        }

        public void setName(String name) {
            SwingUtilities.invokeLater(() -> labelName.setText(name));
        }
    }
}