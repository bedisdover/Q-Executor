package present.panel.trade;

import bl.TimeUtil;
import bl.stock.GetStockDataServiceImpl;
import bl.vwap.VWAP;
import bl.vwap.VWAP_Param;
import blservice.stock.GetStockDataService;
import org.json.JSONException;
import org.json.JSONObject;
import present.component.TipText;
import present.utils.StockJsonInfo;
import util.JsonUtil;
import util.NumberUtil;
import vo.StockNowTimeVO;
import vo.VolumeVO;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 交易面板中的参数面板
 */
class ParamPanel extends JPanel {

    //组件宽度
    private int componentW;

    //组件高度
    private int componentH;

    //组件水平间距
    private static final int H_GAP = 10;

    //组件垂直间距
    private static final int V_GAP = 10;

    //组件字体
    private static final Font font = new Font("宋体", Font.PLAIN, 12);

    //字符串切割符
    private static final String spliter = " : ";

    //股票名称文本框
    private TipText nameVal;

    //股票代码文本框
    private JTextField codeText = new JTextField();

    //交易数量文本框
    private JTextField quanVal = new JTextField();

    //操作类型
    private JComboBox<String> operationVal = new JComboBox<>();

    //开始时间输入框
    private TimePanel start;

    //结束时间输入框
    private TimePanel end;

    //启动计算按钮
    private JButton trigger;

    //终止计算按钮
    private JButton stop;

    //参数面板的父容器
    private TradePanel parent;

    ParamPanel(int width, int height, TradePanel parent) {
        this.parent = parent;
        this.componentW = (width - 5 * H_GAP) >> 2;
        this.componentH = (int)(this.componentW * 0.4);

        Box box = Box.createVerticalBox();
        box.setOpaque(false);
        box.add(Box.createVerticalStrut(componentH));
        box.add(createStockTypePanel());
        box.add(createStockNumPanel());
        box.add(start = new TimePanel("开始时间"));
        box.add(end = new TimePanel("结束时间"));
        box.add(createBtnPanel());

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(new Color(0x222222));
    }

    /**
     * 生成股票数量输入面板
     * @return JPanel
     */
    private JPanel createStockNumPanel() {
        //投资方向
        JLabel operationLabel = new JLabel("投资方向");
        operationVal = new JComboBox<>();
        operationVal.addItem("买");
        operationVal.addItem("卖");
        InputPair operation = new InputPair(operationLabel, operationVal);
        //数量（手）
        JLabel quantityLabel = new JLabel("数量/股");
        InputPair quantity = new InputPair(quantityLabel, quanVal);

        //数量（手）、下单金额
        JPanel amount = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        amount.setOpaque(false);
        amount.add(quantity);
        amount.add(operation);

        return amount;
    }

    /**
     * 生成股票类型输入面板
     * @return JPanel
     */
    private JPanel createStockTypePanel() {
        //股票代码
        JLabel codeLabel = new JLabel("股票代码");
        codeText.setEnabled(false);
        InputPair code = new InputPair(codeLabel, codeText);

        //股票名称
        JLabel nameLabel = new JLabel("股票名称");
        nameVal = new TipText(componentW << 1, componentH);
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
            parent.updateTimeSeriesPanel(s[1]);
        });
        nameVal.setListFocusHandler((field, text) -> {
            String[] s = text.split(spliter);
            field.setText(s[0]);
        });
        InputPair name = new InputPair(nameLabel, nameVal);

        //证券名称、证券代码
        JPanel stock = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        stock.setOpaque(false);
        stock.add(name);
        stock.add(code);
        return stock;
    }

    /**
     * 生成按钮面板
     */
    private JPanel createBtnPanel() {
        //启动计算
        trigger = new JButton("开始计算");
        trigger.setPreferredSize(new Dimension(componentW - H_GAP, componentH));
        trigger.addActionListener((e) -> {
            if (!checkComplete()) {
                return;
            }

            if (operationVal.getSelectedItem().equals("买")) {
                GetStockDataService service = new GetStockDataServiceImpl();
                double price;
                try {
                    List<StockNowTimeVO> data = service.getNowTimeData(codeText.getText());
                    price = data.get(0).getPrice();
                    price = NumberUtil.round(price);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ParamPanel.this, "网络异常");
                    return;
                }
                double money = Integer.parseInt(quanVal.getText()) * price;
                int isOK = JOptionPane.showConfirmDialog(ParamPanel.this, "此次交易共计需要" + money + "元，确定进行吗？");
                if (isOK != JOptionPane.OK_OPTION) {
                    return;
                }
            } else {
                int isOk = JOptionPane.showConfirmDialog(ParamPanel.this, "确认卖出吗");
                if (isOk != JOptionPane.OK_OPTION) {
                    return ;
                }
            }

            disableComponents();
            calculate();
            parent.generatingMsg();
        });

        //结束计算
        stop = new JButton("结束计算");
        stop.setPreferredSize(new Dimension(componentW - H_GAP, componentH));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setPreferredSize(new Dimension(componentW - H_GAP, componentH));

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setPreferredSize(new Dimension(H_GAP << 2, componentH));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.add(left);
        panel.add(trigger);
        panel.add(center);
        panel.add(stop);
        return panel;
    }

    private void disableComponents() {
        nameVal.setEnabled(false);
        quanVal.setEnabled(false);
        start.setEnabled(false);
        end.setEnabled(false);
        trigger.setEnabled(false);
        stop.setEnabled(false);
    }

    private void enableComponents() {
        nameVal.setEnabled(true);
        quanVal.setEnabled(true);
        start.setEnabled(true);
        end.setEnabled(true);
        trigger.setEnabled(true);
        stop.setEnabled(true);
    }

    private boolean checkComplete() {
        if (codeText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写股票名称");
            return false;
        }

        String quantity = quanVal.getText();
        if (quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写交易数量");
            return false;
        }

        try {
            int num = Integer.parseInt(quantity);
            if(num <= 0) {
                JOptionPane.showMessageDialog(this, "交易数量应该为正整数");
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "交易数量应该为正整数");
            return false;
        }

        return checkTime(start) && checkTime(end);
    }

    private boolean checkTime(TimePanel time) {
        String hour = time.getHour();
        String minute = time.getMinute();
        if (hour.isEmpty() || minute.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写时间");
            return false;
        }

        try {
            int hourInt = Integer.parseInt(hour);
            int minuteInt = Integer.parseInt(minute);
            if (!TimeUtil.isTimeValid(hourInt, minuteInt)) {
                JOptionPane.showMessageDialog(this, "请输入有效开始时间");
                return false;
            }
            if (!TimeUtil.isAtTradeTime(hourInt, minuteInt)) {
                JOptionPane.showMessageDialog(this, "输入时间不在交易时间内");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "请输入有效开始时间");
            return false;
        }
    }

    /**
     * 计算交易策略
     */
    private void calculate() {
        SwingWorker<List<VolumeVO>, Void> worker = new SwingWorker<List<VolumeVO>, Void>() {
            @Override
            protected List<VolumeVO> doInBackground() throws Exception {
                VWAP vwap = VWAP.getInstance();
                Calendar s = Calendar.getInstance();
                s.set(
                        Calendar.HOUR_OF_DAY,
                        Integer.parseInt(start.getHour())
                );
                s.set(
                        Calendar.MINUTE,
                        Integer.parseInt(start.getMinute())
                );
                Calendar e = Calendar.getInstance();
                e.set(
                        Calendar.HOUR_OF_DAY,
                        Integer.parseInt(end.getHour())
                );
                s.set(
                        Calendar.MINUTE,
                        Integer.parseInt(end.getMinute())
                );
                Calendar now = Calendar.getInstance();
                //TODO 应该获取当前时间
                now.set(2016, 9, 9, 10, 0);
                //TODO 确定delta的值
                VWAP_Param param = new VWAP_Param(
                        Long.parseLong(quanVal.getText()),
                        codeText.getText(),
                        0.4, TimeUtil.timeToNode(now),
                        TimeUtil.timeToNode(s),
                        TimeUtil.timeToNode(e)
                );
                try {
                    return vwap.predictVn(param);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    List<VolumeVO> result = get();
                    if(result == null) {
                        JOptionPane.showMessageDialog(ParamPanel.this, "网络异常");
                        enableComponents();
                        return;
                    }
                    parent.updateMsgPanel(result, String.valueOf(operationVal.getSelectedItem()));
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(ParamPanel.this, "网络异常");
                }
                enableComponents();
            }
        };

        worker.execute();
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
            name.setForeground(Color.WHITE);
            input.setPreferredSize(new Dimension(componentW + (H_GAP << 1), componentH));
            input.setFont(font);
            this.add(name);
            this.add(input);
            this.setOpaque(false);
        }
    }

    private class TimePanel extends JPanel {

        private JTextField hour;

        private JTextField minute;

        /**
         * @param name 标签名称
         */
        TimePanel(String name) {
            this.setLayout(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
            this.setOpaque(false);

            JLabel label = new JLabel(name);
            label.setFont(font);
            label.setForeground(Color.WHITE);
            label.setPreferredSize(new Dimension(componentW - (H_GAP << 1), componentH));
            this.add(label);

            hour = new JTextField();
            hour.setFont(font);
            hour.setPreferredSize(new Dimension(componentW + (H_GAP << 1), componentH));
            this.add(hour);

            JLabel split = new JLabel(":");
            split.setForeground(Color.white);
            split.setPreferredSize(new Dimension(H_GAP, V_GAP));
            split.setFont(new Font("黑体", Font.BOLD, 15));
            this.add(split);

            minute = new JTextField();
            minute.setFont(font);
            minute.setPreferredSize(new Dimension(componentW, componentH));
            this.add(minute);
        }

        String getHour() {
            return hour.getText();
        }

        String getMinute() {
            return minute.getText();
        }

        @Override
        public void setEnabled(boolean enabled) {
            hour.setEnabled(enabled);
            minute.setEnabled(enabled);
        }
    }
}
