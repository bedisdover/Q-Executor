package present.panel.stock;

import present.charts.GeneralPie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Y481L on 2016/8/27.
 * <p>
 * 大单面板
 */
public class GeneralPanel extends JPanel {

    private JPanel panel;

    private JLabel label;

    private JRadioButton radio400, radio600, radio800, radio1000;

    private ButtonGroup buttonGroup;

    public GeneralPanel(String stockCode) {
        panel = this;

        init();
        createUIComponents();
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

                label = new MyLabel("成交量大于等于(≥):");

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
                JPanel centerPanel = new JPanel(new BorderLayout());

                centerPanel.setPreferredSize(new Dimension(1, 200));

                centerPanel.add(new GeneralPie().getPieChart(""), BorderLayout.WEST);

                Box labelBox = Box.createVerticalBox();

                labelBox.add(new MyLabel("大单成交量:18.56万股"));
                labelBox.add(new MyLabel("总成交量:441.34万股"));
                labelBox.add(new MyLabel("总成交量:441.34万股"));
                labelBox.add(new MyLabel("总成交量:441.34万股"));
                labelBox.add(new MyLabel("总成交量:441.34万股"));
                labelBox.add(new MyLabel("总成交量:441.34万股"));

                centerPanel.add(labelBox, BorderLayout.CENTER);

                panel.add(centerPanel, BorderLayout.CENTER);
            }

            {
                JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                southPanel.setPreferredSize(new Dimension(1, 300));

                southPanel.add(createTable());

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

    }

    private JScrollPane createTable() {
        Object[][] playerInfo = {
                {"阿呆", 66, 32, 98, false},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
                {"阿呆", 82, 69, 128, true},
        };

        //字段名称
        String[] Names = {"交易时间", "成交价", "成交量(手)", "成交额(万元)", "买卖盘性质"};

        return new MyTable(playerInfo, Names).createTable();
    }
}
