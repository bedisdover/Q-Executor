package present.panel.stock;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-8-26.
 * <p>
 * 盘口面板
 */
class HandicapPanel extends JPanel {

    private JPanel panel;


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
                CenterPanel centerPanel = new CenterPanel();

                panel.add(centerPanel, BorderLayout.CENTER);
            }

            {
                SouthPanel southPanel = new SouthPanel();
                panel.add(southPanel, BorderLayout.SOUTH);
            }
        });
    }
}

class CenterPanel extends JPanel {
    private JPanel panel;

    private JLabel labelCommittee, labelCommission;

    private JLabel committee, commission;

    CenterPanel() {
        panel = this;

        init();
        createUIComponents();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(new Color(0xccccccc));
            panel.setLayout(new BorderLayout(10, 0));

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                Box westPanel = Box.createVerticalBox();

                labelCommittee = new MyLabel("委比");
                labelCommission = new MyLabel("委差");

                westPanel.add(labelCommittee);
                westPanel.add(labelCommission);

                panel.add(westPanel, BorderLayout.WEST);
            }

            {
                Box eastPanel = Box.createVerticalBox();

                commission = new MyLabel("35462");
                committee = new MyLabel("57.81%");

                eastPanel.add(commission);
                eastPanel.add(committee);

                panel.add(eastPanel, BorderLayout.EAST);
            }
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
            panel.setBackground(new Color(0xcccccc));

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

                sell5price = new MyLabel("164.47");
                sell4price = new MyLabel("167.74");
                sell3price = new MyLabel("165.23");
                sell2price = new MyLabel("167.4");
                sell1price = new MyLabel("167.38");
                price = new MyLabel("167.36");
                buy1price = new MyLabel("167.36");
                buy2price = new MyLabel("167.36");
                buy3price = new MyLabel("167.36");
                buy4price = new MyLabel("167.36");
                buy5price = new MyLabel("167.36");

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

                sell5amount = new MyLabel("7");
                sell4amount = new MyLabel("7");
                sell3amount = new MyLabel("7");
                sell2amount = new MyLabel("7");
                sell1amount = new MyLabel("7");
                amount = new MyLabel(" ");
                buy1amount = new MyLabel("14");
                buy2amount = new MyLabel("14");
                buy3amount = new MyLabel("14");
                buy4amount = new MyLabel("14");
                buy5amount = new MyLabel("14");

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
}
