package present.panel.stock;

import util.NumberUtil;
import vo.StockNowTimeVO;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-8-26.
 * <p>
 * 盘口面板
 */
class HandicapPanel extends JPanel {

    private JPanel panel;

    private CenterPanel centerPanel;

    private SouthPanel southPanel;

    HandicapPanel() {
        panel = this;

        init();
        createUIComponents();
    }

    /**
     * 初始化
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout(0, 5));
            panel.setBackground(new Color(0xeeeeee));

            panel.revalidate();
        });
    }

    /**
     * 初始化组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                centerPanel = new CenterPanel();

                panel.add(centerPanel, BorderLayout.CENTER);
            }

            {
                southPanel = new SouthPanel();
                panel.add(southPanel, BorderLayout.SOUTH);
            }
        });
    }

    /**
     * 设置数据
     */
    void setData(StockNowTimeVO stockNowTimeVO) {
        SwingUtilities.invokeLater(() -> {
            centerPanel.setData(stockNowTimeVO);
            southPanel.setData(stockNowTimeVO);
        });
    }
}

class CenterPanel extends JPanel {
    private JPanel panel;

    private JLabel labelDepth, labelCommittee, labelCommission;

    private JLabel depth, committee, commission;

    CenterPanel() {
        panel = this;

        init();
        createUIComponents();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(new Color(0xeeeeee));
            panel.setLayout(new BorderLayout(10, 0));

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                Box westPanel = Box.createVerticalBox();

                labelDepth = new MyLabel("深度");
                labelCommittee = new MyLabel("委比");
                labelCommission = new MyLabel("委差");

                westPanel.add(labelDepth);
                westPanel.add(labelCommittee);
                westPanel.add(labelCommission);

                panel.add(westPanel, BorderLayout.WEST);
            }

            {
                Box eastPanel = Box.createVerticalBox();

                depth = new MyLabel(" -- ");
                commission = new MyLabel(" -- ");
                committee = new MyLabel(" -- ");

                eastPanel.add(depth);
                eastPanel.add(commission);
                eastPanel.add(committee);

                panel.add(eastPanel, BorderLayout.EAST);
            }
        });
    }

    void setData(StockNowTimeVO stockNowTimeVO) {
        SwingUtilities.invokeLater(() -> {
            double temp = stockNowTimeVO.getCommission();
            Color color = temp >= 0 ? Color.RED : Color.GREEN;

            depth.setText(stockNowTimeVO.getDepth());
            committee.setText(stockNowTimeVO.getCommittee());
            commission.setText(stockNowTimeVO.getCommission() + "");
            committee.setForeground(color);
            commission.setForeground(color);
        });
    }
}

class SouthPanel extends JPanel {

    private JPanel panel;

    private JLabel labelSell5, labelSell4, labelSell3, labelSell2, labelSell1, labelPrice,
            labelBuy1, labelBuy2, labelBuy3, labelBuy4, labelBuy5;

    private JLabel sell5price, sell5amount, sell4price, sell4amount, sell3price, sell3amount,
            sell2price, sell2amount, sell1price, sell1amount, price, amount, buy1price, buy1amount,
            buy2price, buy2amount, buy3price, buy3amount, buy4price, buy4amount, buy5price, buy5amount;

    SouthPanel() {
        panel = this;

        init();
        createUIComponents();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setLayout(new BorderLayout(20, 0));
            panel.setBackground(new Color(0xeeeeee));

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {

            {
                Box westBox = Box.createVerticalBox();

                labelSell5 = new MyLabel("卖五");
                labelSell4 = new MyLabel("卖四");
                labelSell3 = new MyLabel("卖三");
                labelSell2 = new MyLabel("卖二");
                labelSell1 = new MyLabel("卖一");
                labelPrice = new MyLabel("成交");
                labelBuy1 = new MyLabel("买一");
                labelBuy2 = new MyLabel("买二");
                labelBuy3 = new MyLabel("买三");
                labelBuy4 = new MyLabel("买四");
                labelBuy5 = new MyLabel("买五");

                westBox.add(labelSell5);
                westBox.add(labelSell4);
                westBox.add(labelSell3);
                westBox.add(labelSell2);
                westBox.add(labelSell1);
                westBox.add(labelPrice);
                westBox.add(labelBuy1);
                westBox.add(labelBuy2);
                westBox.add(labelBuy3);
                westBox.add(labelBuy4);
                westBox.add(labelBuy5);

                panel.add(westBox, BorderLayout.WEST);
            }

            {
                Box centerBox = Box.createVerticalBox();

                sell5price = new MyLabel(" -- ");
                sell4price = new MyLabel(" -- ");
                sell3price = new MyLabel(" -- ");
                sell2price = new MyLabel(" -- ");
                sell1price = new MyLabel(" -- ");
                price = new MyLabel(" -- ");
                buy1price = new MyLabel(" -- ");
                buy2price = new MyLabel(" -- ");
                buy3price = new MyLabel(" -- ");
                buy4price = new MyLabel(" -- ");
                buy5price = new MyLabel(" -- ");

                centerBox.add(sell5price);
                centerBox.add(sell4price);
                centerBox.add(sell3price);
                centerBox.add(sell2price);
                centerBox.add(sell1price);
                centerBox.add(price);
                centerBox.add(buy1price);
                centerBox.add(buy2price);
                centerBox.add(buy3price);
                centerBox.add(buy4price);
                centerBox.add(buy5price);

                panel.add(centerBox, BorderLayout.CENTER);
            }

            {
                Box eastBox = Box.createVerticalBox();

                sell5amount = new MyLabel(" -- ");
                sell4amount = new MyLabel(" -- ");
                sell3amount = new MyLabel(" -- ");
                sell2amount = new MyLabel(" -- ");
                sell1amount = new MyLabel(" -- ");
                amount = new MyLabel(" ");
                buy1amount = new MyLabel(" -- ");
                buy2amount = new MyLabel(" -- ");
                buy3amount = new MyLabel(" -- ");
                buy4amount = new MyLabel(" -- ");
                buy5amount = new MyLabel(" -- ");

                eastBox.add(sell5amount);
                eastBox.add(sell4amount);
                eastBox.add(sell3amount);
                eastBox.add(sell2amount);
                eastBox.add(sell1amount);
                eastBox.add(amount);
                eastBox.add(buy1amount);
                eastBox.add(buy2amount);
                eastBox.add(buy3amount);
                eastBox.add(buy4amount);
                eastBox.add(buy5amount);

                panel.add(eastBox, BorderLayout.EAST);
            }
        });
    }

    void setData(StockNowTimeVO stockNowTimeVO) {
        SwingUtilities.invokeLater(() -> {
            sell1amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getSell1amount()));
            sell2amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getSell2amount()));
            sell3amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getSell3amount()));
            sell4amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getSell4amount()));
            sell5amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getSell5amount()));
            buy1amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getBuy1amount()));
            buy2amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getBuy2amount()));
            buy3amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getBuy3amount()));
            buy4amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getBuy4amount()));
            buy5amount.setText(NumberUtil.transferUnit(stockNowTimeVO.getBuy5amount()));
            sell1price.setText(stockNowTimeVO.getSell1price() + "");
            sell2price.setText(stockNowTimeVO.getSell2price() + "");
            sell3price.setText(stockNowTimeVO.getSell3price() + "");
            sell4price.setText(stockNowTimeVO.getSell4price() + "");
            sell5price.setText(stockNowTimeVO.getSell5price() + "");
            price.setText(stockNowTimeVO.getPrice() + "");
            buy1price.setText(stockNowTimeVO.getBuy1price() + "");
            buy2price.setText(stockNowTimeVO.getBuy2price() + "");
            buy3price.setText(stockNowTimeVO.getBuy3price() + "");
            buy4price.setText(stockNowTimeVO.getBuy4price() + "");
            buy5price.setText(stockNowTimeVO.getBuy5price() + "");
        });
    }
}
