package present.panel.trade;

import bl.VWAP;
import bl.VWAP_Param;
import blservice.VWAPService;
import org.json.JSONException;
import org.json.JSONObject;
import present.component.TipText;
import present.utils.StockJsonInfo;
import util.JsonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 交易面板中的参数面板
 */
class ParamPanel extends JPanel {

    private int componentW;

    private int componentH;

    private static final int H_GAP = 10;

    private static final int V_GAP = 10;

    private static final Font font = new Font("宋体", Font.PLAIN, 12);

    //资金账户
    private Line account;

    //可用资金
    private Line money;

    //证券代码
    private Line code;

    //证券名称
    private Line name;

    //投资方向
    private Line operation;

    //数量（股）
    private Line quantity;

    //盘口类型
    private Line type;

    //下单金额
    private Line invest;

    private VWAPService vwap = new VWAP();

    ParamPanel(int width, int height, TradePanel parent) {
        this.componentW = ((width >> 1) - 3 * H_GAP) >> 1;
        this.componentH = (int)(this.componentW * 0.4);

        Box box = Box.createVerticalBox();

        Box line1 = Box.createHorizontalBox();
        //资金账户
        JLabel accountLabel = new JLabel("资金账户");
        JComboBox<String> accountValue = new JComboBox<>();
        accountValue.addItem("12345");
        accountValue.addItem("54321");
        account = new Line(accountLabel, accountValue);
        line1.add(account);
        //可用资金
        JLabel moneyLabel = new JLabel("可用资金");
        JTextField moneyVal = new JTextField();
        money = new Line(moneyLabel, moneyVal);
        line1.add(money);
        box.add(line1);

        Box line2 = Box.createHorizontalBox();
        //证券名称
        JLabel nameLabel = new JLabel("证券名称");
        JTextField nameVal = new JTextField();
        name = new Line(nameLabel, nameVal);
        name.getInput().setEnabled(false);
        line2.add(name);
        //证券代码
        JLabel codeLabel = new JLabel("证券代码");
        TipText codeText = new TipText(componentW << 1, componentH);
        codeText.setMatcher((key) -> {
            Vector<String> v = new Vector<>();
            List<JSONObject> list = JsonUtil.contains(
                    StockJsonInfo.JSON_KEYS, StockJsonInfo.JSON_PATH, key
            );
            for (JSONObject obj : list) {
                try {
                    v.addElement(
                            obj.getString(StockJsonInfo.KEY_CODE) + " " +
                            obj.getString(StockJsonInfo.KEY_NAME)
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return v;
        });
        codeText.setListHandler((text) -> {
            String[] s = text.split(" ");
            codeText.setText(s[0]);
            nameVal.setText(s[1]);
        });
        code = new Line(codeLabel, codeText);
        line2.add(code);
        box.add(line2);

        Box line3 = Box.createHorizontalBox();
        //投资方向
        JLabel operationLabel = new JLabel("投资方向");
        JComboBox<String> operationVal = new JComboBox<>();
        operationVal.addItem("买");
        operationVal.addItem("卖");
        operation = new Line(operationLabel, operationVal);
        line3.add(operation);
        //数量（股）
        JLabel quantityLabel = new JLabel("数量/股");
        JTextField quanVal = new JTextField();
        quantity = new Line(quantityLabel, quanVal);
        line3.add(quantity);
        box.add(line3);

        Box line4 = Box.createHorizontalBox();
        //盘口
        JLabel typeLabel = new JLabel("盘口");
        JComboBox<String> typeVal = new JComboBox<>();
        typeVal.addItem("自动盘口");
        typeVal.addItem("手动盘口");
        type = new Line(typeLabel, typeVal);
        line4.add(type);
        //下单金额
        JLabel investLabel = new JLabel("下单金额");
        JTextField investVal = new JTextField();
        invest = new Line(investLabel, investVal);
        line4.add(invest);
        box.add(line4);

//        west.add(this.getLineItem(start, startPanel));
//        //成交置信度
//        JLabel confidence = new JLabel("成交置信度");
//        JComboBox<String> confiVal = new JComboBox<>();
//        west.add(this.getLineItem(confidence, confiVal));
//        //回测开始
//        JLabel startDate = new JLabel("回测开始");
//        JTextField startDateVal = new JTextField();
//        Chooser chooser1 = Chooser.getInstance();
//        chooser1.register(startDateVal);
//        west.add(this.getLineItem(startDate, startDateVal));

//        //结束时间
//        JLabel end = new JLabel("结束时间");
//        JComboBox<String> endVal = new JComboBox<>();
//        east.add(this.getLineItem(end, endVal));
//        //参考周期（分）
//        JLabel reference = new JLabel("参考周期");
//        JTextField refVal = new JTextField();
//        east.add(this.getLineItem(reference, refVal));
//        //回测结束
//        JLabel endDate = new JLabel("回测结束");
//        JTextField endDataVal = new JTextField();
//        Chooser chooser2 = Chooser.getInstance();
//        chooser2.register(endDataVal);
//        east.add(this.getLineItem(endDate, endDataVal));


        box.add(createTimePanel("开始时间"));
        box.add(createTimePanel("结束时间"));

        JPanel line7 = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));

        JLabel mode = new JLabel("启动模式");
        mode.setPreferredSize(new Dimension(componentW, componentH));
        line7.add(mode);

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        JRadioButton trade = new JRadioButton("交易");
        trade.setPreferredSize(new Dimension(componentW, componentH));
        p.add(trade);
        JRadioButton test = new JRadioButton("回测");
        test.setPreferredSize(new Dimension(componentW, componentH));
        ButtonGroup group = new ButtonGroup();
        group.add(trade);
        group.add(test);
        p.add(test);
        line7.add(p);

        JButton trigger = new JButton("启动");
        trigger.setPreferredSize(new Dimension(componentW, componentH));
        trigger.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
//            int tradeNum = Integer.parseInt(quanVal.getText());
//            String code = codeText.getText();
//            try {
//                vwap.predictVn(new VWAP_Param(
//                       tradeNum, code, 0, 0, 0
//                ));
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
                parent.updateMsgPanel();
            }
        });
        line7.add(trigger);
        box.add(line7);

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(width, height));
    }

    private JPanel createTimePanel(String name) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));

        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setPreferredSize(new Dimension(componentW, componentH));
        panel.add(label);

        JComboBox<String> hour = new JComboBox<>();
        initHourBox(hour);
        hour.setFont(font);
        hour.setPreferredSize(new Dimension(componentW, componentH));
        panel.add(hour);

        JLabel split = new JLabel(":");
        split.setPreferredSize(new Dimension(H_GAP, V_GAP));
        split.setFont(new Font("黑体", Font.BOLD, 15));
        panel.add(split);

        JComboBox<String> minute = new JComboBox<>();
        initMinuteBox(minute);
        minute.setFont(font);
        minute.setPreferredSize(new Dimension(componentW, componentH));
        panel.add(minute);

        return panel;
    }

    private void initMinuteBox(JComboBox<String> box) {
        box.addItem("00");
        box.addItem("05");
        box.addItem("10");
        box.addItem("15");
        box.addItem("20");
        box.addItem("30");
        box.addItem("35");
        box.addItem("40");
        box.addItem("45");
        box.addItem("50");
        box.addItem("55");
    }

    private void initHourBox(JComboBox<String> box) {
        box.addItem("9");
        box.addItem("10");
        box.addItem("15");
        box.addItem("16");
    }

    private class Line extends JPanel {

        private JComponent input;

        /**
         * 创建包含一行组件的面板，左边为标签，右边为输入组件
         *
         * @param name 标签
         * @param input 输入组件
         */
        Line(JLabel name, JComponent input) {
            this.input = input;
            name.setPreferredSize(new Dimension(componentW - (H_GAP << 1), componentH));
            name.setFont(font);
            input.setPreferredSize(new Dimension(componentW + (H_GAP << 1), componentH));
            input.setFont(font);
            this.add(name);
            this.add(input);
        }

        JComponent getInput() {
            return input;
        }
    }

}
