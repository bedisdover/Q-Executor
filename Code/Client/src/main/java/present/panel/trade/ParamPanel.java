package present.panel.trade;

import bl.VWAP;
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
import java.util.List;
import java.util.Vector;

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

    //字符串切割符
    private static final String spliter = " : ";

    private VWAPService vwap = new VWAP();

    ParamPanel(int width, int height, TradePanel parent) {
        this.componentW = ((width >> 1) - 3 * H_GAP) >> 1;
        this.componentH = (int)(this.componentW * 0.4);

        Box box = Box.createVerticalBox();

        //证券代码
        JLabel codeLabel = new JLabel("证券代码");
        JTextField codeText = new JTextField();
        codeText.setEnabled(false);
        InputPair code = new InputPair(codeLabel, codeText);
        //证券名称
        JLabel nameLabel = new JLabel("证券名称");
        TipText nameVal = new TipText(componentW << 1, componentH);
        nameVal.setMatcher((key) -> {
            Vector<String> v = new Vector<>();
            List<JSONObject> list = JsonUtil.contains(
                    StockJsonInfo.JSON_KEYS, StockJsonInfo.JSON_PATH, key
            );
            for (JSONObject obj : list) {
                try {
                    v.addElement(
                            obj.getString(StockJsonInfo.KEY_NAME) + spliter +
                                    obj.getString(StockJsonInfo.KEY_CODE)
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return v;
        });
        nameVal.setListClickHandler((text) -> {
            String[] s = text.split(spliter);
            nameVal.setText(s[0]);
            codeText.setText(s[1]);
        });
        nameVal.setListFocusHandler((field, text) -> {
            String[] s = text.split(spliter);
            field.setText(s[0]);
        });
        InputPair name = new InputPair(nameLabel, nameVal);
        //证券名称、证券代码
        JPanel stock = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        stock.add(name);
        stock.add(code);
        box.add(stock);

        //数量（手）
        JLabel quantityLabel = new JLabel("数量/手");
        JTextField quanVal = new JTextField();
        InputPair quantity = new InputPair(quantityLabel, quanVal);
        //下单金额
        JLabel investLabel = new JLabel("下单金额");
        JTextField investVal = new JTextField();
        investVal.setEnabled(false);
        InputPair invest = new InputPair(investLabel, investVal);
        //数量（手）、下单金额
        JPanel amount = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        amount.add(quantity);
        amount.add(invest);
        box.add(amount);

        box.add(createTimePanel("开始时间"));
        box.add(createTimePanel("结束时间"));

        //投资方向
        JLabel operationLabel = new JLabel("投资方向");
        JComboBox<String> operationVal = new JComboBox<>();
        operationVal.addItem("买");
        operationVal.addItem("卖");
        InputPair operation = new InputPair(operationLabel, operationVal);
        //开始交易
        JButton trigger = new JButton("开始交易");
        trigger.setPreferredSize(new Dimension(componentW, componentH));
        trigger.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                parent.updateMsgPanel();
            }
        });
        JPanel start = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        start.add(operation);
        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(H_GAP, componentH));
        start.add(empty);
        start.add(trigger);
        box.add(start);

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(width, height));
    }

    private JPanel createTimePanel(String name) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));

        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setPreferredSize(new Dimension(componentW - (H_GAP << 1), componentH));
        panel.add(label);

        JComboBox<String> hour = new JComboBox<>();
        initHourBox(hour);
        hour.setFont(font);
        hour.setPreferredSize(new Dimension(componentW + (H_GAP << 1), componentH));
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

    private class InputPair extends JPanel {
        /**
         * 创建包含一行组件的面板，左边为标签，右边为输入组件
         *
         * @param name 标签
         * @param input 输入组件
         */
        InputPair(JLabel name, JComponent input) {
            name.setPreferredSize(new Dimension(componentW - (H_GAP << 1), componentH));
            name.setFont(font);
            input.setPreferredSize(new Dimension(componentW + (H_GAP << 1), componentH));
            input.setFont(font);
            this.add(name);
            this.add(input);
        }
    }
}
