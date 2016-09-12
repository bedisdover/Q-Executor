package present.panel.stock.west;

import bl.stock.GetStockDataServiceImpl;
import blservice.stock.GetStockDataService;
import present.component.MyLabel;
import present.panel.stock.center.GeneralPanel;
import present.panel.stock.center.TimeSeriesPanel;
import present.utils.ColorUtil;
import util.NumberUtil;
import vo.StockBasicInfoVO;
import vo.StockNowTimeVO;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by song on 16-8-26.
 * <p>
 * 实时数据面板
 */
public class CurrentDataPanel extends JPanel {

    private JPanel panel;

    private String stockCode;

    private PricePanel pricePanel;

    private DataPanel dataPanel;

    private BasicInfoPanel basicInfoPanel;

    private HandicapPanel handicapPanel;

    private StockBasicInfoVO stockBasicInfoVO;

    private StockNowTimeVO stockNowTimeVO;

    /**
     * 收盘价
     *
     * @see TimeSeriesPanel
     */
    private static double close = 0;

    public CurrentDataPanel(String stockCode) {
        panel = this;
        this.stockCode = stockCode;

        init();
        getData();
        createUIComponents();
    }

    /**
     * 初始化
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
//            panel.setLayout(new BorderLayout(0, 5));

            panel.setLayout(new GridBagLayout());
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        }, 3000, 3000);
    }

    /**
     * 初始化组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            GridBagConstraints bagConstraints = new GridBagConstraints();

            bagConstraints.gridwidth = 0;
            bagConstraints.fill = GridBagConstraints.HORIZONTAL;
            bagConstraints.weightx = 1;
            bagConstraints.weighty = 0;
            pricePanel = new PricePanel();

            panel.add(pricePanel, bagConstraints);
            dataPanel = new DataPanel();
            bagConstraints.gridwidth = GridBagConstraints.REMAINDER;
            bagConstraints.fill = GridBagConstraints.BOTH;
            bagConstraints.weighty = 1;
            panel.add(dataPanel, bagConstraints);

            handicapPanel = new HandicapPanel();
            bagConstraints.gridwidth = GridBagConstraints.REMAINDER;
            bagConstraints.fill = GridBagConstraints.BOTH;
            bagConstraints.weighty = 1;
            panel.add(handicapPanel, bagConstraints);

            basicInfoPanel = new BasicInfoPanel();
            bagConstraints.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(basicInfoPanel, bagConstraints);

            panel.revalidate();
            panel.repaint();
        });
    }

    private void getData() {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                GetStockDataService stockDataService = new GetStockDataServiceImpl();

                return stockDataService.getNowTimeData(stockCode);
            }

            @Override
            protected void done() {
                try {
                    List stockNowTimeVOList = (List) get();

                    stockNowTimeVO = (StockNowTimeVO) stockNowTimeVOList.get(0);

                    setCurrentData(stockNowTimeVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    /**
     * 设置基本信息
     *
     * @param stockBasicInfoVO 基本信息
     */
    public void setBasicInfo(StockBasicInfoVO stockBasicInfoVO) {
        SwingUtilities.invokeLater(() -> {
            this.stockBasicInfoVO = stockBasicInfoVO;

            basicInfoPanel.setBasicInfo(stockBasicInfoVO);
        });
    }

    /**
     * 设置当前数据
     */
    private void setCurrentData(StockNowTimeVO stockNowTimeVO) {
        pricePanel.setData(stockNowTimeVO);
        dataPanel.setData(stockNowTimeVO);
        handicapPanel.setData(stockNowTimeVO);

        close = stockNowTimeVO.getClose();
    }

    /**
     * @return 成交量
     * @see GeneralPanel
     */
    public double getAmount() {
        return stockNowTimeVO.getAmount();
    }

    /**
     * @return 成交额
     * @see GeneralPanel
     */
    public double getVolume() {
        return stockNowTimeVO.getVolume();
    }

    public static double getClose() {
        return close;
    }

    /**
     * Created by song on 16-8-26.
     * <p>
     * 价格面板，包含当前价格、涨跌额（涨跌幅）、数据时间
     */
    private static class PricePanel extends JPanel {

        private JPanel panel;

        private JLabel price, inc_dec, incNum, incRate, time;

        PricePanel() {
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
                panel.setLayout(new GridLayout(2, 1));

                panel.revalidate();
            });
        }

        /**
         * 初始化组件
         */
        private void createUIComponents() {
            SwingUtilities.invokeLater(() -> {
                {
                    JPanel northPanel = new JPanel();

                    northPanel.setBackground(new Color(0xeeeeee));
                    northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

                    price = new JLabel(" -- ");
                    price.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                    price.setToolTipText("最新价格");
                    inc_dec = new JLabel();
                    incNum = new JLabel(" -- ");
                    incNum.setToolTipText("涨跌额");
                    incRate = new JLabel(" (--) ");
                    incRate.setToolTipText("涨跌幅");

                    northPanel.add(price);
                    northPanel.add(inc_dec);
                    northPanel.add(incNum);
                    northPanel.add(incRate);

                    panel.add(northPanel);
                }

                {
                    JPanel southPanel = new JPanel();

                    southPanel.setBackground(new Color(0xeeeeee));
                    southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

                    time = new JLabel("--年--月--日 --:--:--");

                    southPanel.add(time);

                    panel.add(southPanel);
                }

                panel.revalidate();
            });
        }

        void setData(StockNowTimeVO stockNowTimeVO) {
            SwingUtilities.invokeLater(() -> {
                double temp = stockNowTimeVO.getIncNum();
                Color color = ColorUtil.getTextColor(temp);
                ImageIcon icon = ColorUtil.getIcon(temp);

                price.setText(NumberUtil.transferUnit(stockNowTimeVO.getPrice()));
                inc_dec.setIcon(icon);
                incNum.setText(temp + "");
                incRate.setText("(" + stockNowTimeVO.getIncRate() + "%)");
                incNum.setForeground(color);
                incRate.setForeground(color);

                time.setText(getDate(stockNowTimeVO.getTime()));
            });
        }

        private String getDate(Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

            return dateFormat.format(date);
        }
    }

    /**
     * Created by song on 16-8-26.
     * <p>
     * 数据面板，包含
     * 今  开:	68.98	最  高:	70	    换手率:	1.37%	成交量:	3.62万手
     * 昨  收:	68.42	最  低:	68.51	市盈率TTM:	68.02	成交额:	2.50亿元
     * 振  幅:	2.18%	市净率:	20.87	每股收益:	0.267元	总市值:	32.87亿元
     */
    private class DataPanel extends JPanel {
        private JPanel panel;

        private MyLabel labelOpen, labelHigh, labelTurnOver, labelAmount,
                labelClose, labelLow, labelPe, labelVolume, labelAmplitude,
                labelPb;

        private MyLabel open, high, turnOver, amount, close, low,
                pe, volume, amplitude, pb;

        DataPanel() {
            panel = this;

            init();
            createUIComponents();
        }

        private void init() {
            SwingUtilities.invokeLater(() -> {
                panel.setBackground(new Color(0xeeeeee));

                panel.setLayout(new GridLayout(0, 2));

                panel.revalidate();
            });
        }

        private void createUIComponents() {
            SwingUtilities.invokeLater(() -> {
                labelOpen = new MyLabel("今   开");
                labelClose = new MyLabel("昨   收");
                labelHigh = new MyLabel("最   高");
                labelLow = new MyLabel("最   低");
                labelAmplitude = new MyLabel("振   幅");
                labelTurnOver = new MyLabel("换手率");
                labelAmount = new MyLabel("成交量");
                labelVolume = new MyLabel("成交额");
                labelPe = new MyLabel("市盈率");
                labelPb = new MyLabel("市净率");

                open = new MyLabel(" -- ");
                close = new MyLabel(" -- ");
                high = new MyLabel(" -- ");
                low = new MyLabel(" -- ");
                amplitude = new MyLabel(" -- ");
                turnOver = new MyLabel(" -- ");
                amount = new MyLabel(" -- ");
                volume = new MyLabel(" -- ");
                pe = new MyLabel(" -- ");
                pb = new MyLabel(" -- ");

                panel.add(labelOpen);
                panel.add(open);
                panel.add(labelClose);
                panel.add(close);
                panel.add(labelHigh);
                panel.add(high);
                panel.add(labelLow);
                panel.add(low);
                panel.add(labelAmplitude);
                panel.add(amplitude);
                panel.add(labelTurnOver);
                panel.add(turnOver);
                panel.add(labelAmount);
                panel.add(amount);
                panel.add(labelVolume);
                panel.add(volume);
                panel.add(labelPe);
                panel.add(pe);
                panel.add(labelPb);
                panel.add(pb);
            });
        }

        void setData(StockNowTimeVO stockNowTimeVO) {
            SwingUtilities.invokeLater(() -> {
                open.setText(stockNowTimeVO.getOpen() + "");
                close.setText(stockNowTimeVO.getClose() + "");
                high.setText(stockNowTimeVO.getHigh() + "");
                low.setText(stockNowTimeVO.getLow() + "");
                amplitude.setText(stockNowTimeVO.getAmplitude());
                // turnOver = (amount / outstanding) * 100%
                // outstanding --- 流通股
                amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getAmount()));
                volume.setText(NumberUtil.transferUnit(stockNowTimeVO.getVolume()));
                turnOver.setText(NumberUtil.transferUnit(
                        stockNowTimeVO.getAmount() / stockBasicInfoVO.getOutstanding() / 1e4 * 100) + "%");
                pe.setText(stockBasicInfoVO.getPe() + "");
                pb.setText(stockBasicInfoVO.getPb() + "");
            });
        }
    }
}


