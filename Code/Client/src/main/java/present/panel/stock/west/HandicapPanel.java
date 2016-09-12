package present.panel.stock.west;

import present.component.MyLabel;
import present.utils.ColorUtil;
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
            panel.setLayout(new BorderLayout());
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

                panel.add(centerPanel, BorderLayout.NORTH);
            }

            {
                southPanel = new SouthPanel();
                panel.add(southPanel, BorderLayout.CENTER);
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
            panel.setLayout(new GridLayout(0, 2));

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            labelDepth = new MyLabel("深度");
            labelCommittee = new MyLabel("委比");
            labelCommission = new MyLabel("委差");

            depth = new MyLabel(" -- ");
            commission = new MyLabel(" -- ");
            committee = new MyLabel(" -- ");

            panel.add(labelDepth);
            panel.add(depth);
            panel.add(labelCommittee);
            panel.add(committee);
            panel.add(labelCommission);
            panel.add(commission);
        });
    }

    void setData(StockNowTimeVO stockNowTimeVO) {
        SwingUtilities.invokeLater(() -> {
            Color color = ColorUtil.getTextColor(stockNowTimeVO.getCommission());

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
            panel.setLayout(new GridLayout(0, 3));
            panel.setBackground(new Color(0xeeeeee));

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
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

            sell5price.setForeground(ColorUtil.INC_COLOR);
            sell4price.setForeground(ColorUtil.INC_COLOR);
            sell3price.setForeground(ColorUtil.INC_COLOR);
            sell2price.setForeground(ColorUtil.INC_COLOR);
            sell1price.setForeground(ColorUtil.INC_COLOR);
            buy1price.setForeground(ColorUtil.DEC_COLOR);
            buy2price.setForeground(ColorUtil.DEC_COLOR);
            buy3price.setForeground(ColorUtil.DEC_COLOR);
            buy4price.setForeground(ColorUtil.DEC_COLOR);
            buy5price.setForeground(ColorUtil.DEC_COLOR);

            panel.add(labelSell5);
            panel.add(sell5price);
            panel.add(sell5amount);
            panel.add(labelSell4);
            panel.add(sell4price);
            panel.add(sell4amount);
            panel.add(labelSell3);
            panel.add(sell3price);
            panel.add(sell3amount);
            panel.add(labelSell2);
            panel.add(sell2price);
            panel.add(sell2amount);
            panel.add(labelSell1);
            panel.add(sell1price);
            panel.add(sell1amount);
            panel.add(labelPrice);
            panel.add(price);
            panel.add(amount);
            panel.add(labelBuy1);
            panel.add(buy1price);
            panel.add(buy1amount);
            panel.add(labelBuy2);
            panel.add(buy2price);
            panel.add(buy2amount);
            panel.add(labelBuy3);
            panel.add(buy3price);
            panel.add(buy3amount);
            panel.add(labelBuy4);
            panel.add(buy4price);
            panel.add(buy4amount);
            panel.add(labelBuy5);
            panel.add(buy5price);
            panel.add(buy5amount);
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
