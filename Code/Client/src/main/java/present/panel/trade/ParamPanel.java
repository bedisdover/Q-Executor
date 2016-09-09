package present.panel.trade;

import bl.GetStockDataServiceImpl;
import bl.TimeUtil;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    //策略生成按钮
    private JButton trigger = new JButton("生成交易策略");

    //股票代码文本框
    private JTextField codeText = new JTextField();

    //交易数量文本框
    private JTextField quanVal = new JTextField();

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
        box.add(new TimePanel("开始时间"));
        box.add(new TimePanel("结束时间"));

        //投资方向
        JLabel operationLabel = new JLabel("投资方向");
        JComboBox<String> operationVal = new JComboBox<>();
        operationVal.addItem("买");
        operationVal.addItem("卖");
        InputPair operation = new InputPair(operationLabel, operationVal);
        this.initStartBtn();
        JPanel start = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, 0));
        start.setOpaque(false);
        start.add(operation);
        JPanel empty = new JPanel();
        empty.setOpaque(false);
        empty.setPreferredSize(new Dimension(H_GAP, componentH));
        start.add(empty);
        start.add(trigger);
        box.add(start);

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
    }

    /**
     * 生成股票数量输入面板
     * @return JPanel
     */
    private JPanel createStockNumPanel() {
        //下单金额
        JLabel investLabel = new JLabel("下单金额");
        JTextField investVal = new JTextField();
        investVal.setEnabled(false);
        InputPair invest = new InputPair(investLabel, investVal);
        //数量（手）
        JLabel quantityLabel = new JLabel("数量/股");
        InputPair quantity = new InputPair(quantityLabel, quanVal);
        GetStockDataService service = new GetStockDataServiceImpl();
        quanVal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String text = quanVal.getText();
                try {
                    int num = Integer.parseInt(text);
                    String code = codeText.getText();
                    if (code.isEmpty()) {
                        JOptionPane.showMessageDialog(ParamPanel.this, "请输入股票名称");
                    }else {
                        List<StockNowTimeVO> data = service.getNowTimeData(code);
                        investVal.setText(String.valueOf(
                                NumberUtil.round(data.get(0).getPrice() * num
                                )));
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ParamPanel.this, "交易数量应该为整数");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ParamPanel.this, "网络异常");
                }
            }
        });

        //数量（手）、下单金额
        JPanel amount = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, 0));
        amount.setOpaque(false);
        amount.add(quantity);
        amount.add(invest);

        return amount;
    }



    /**
     * 生成股票类型输入面板
     * @return JPanel
     */
    private JPanel createStockTypePanel() {
        //证券代码
        JLabel codeLabel = new JLabel("证券代码");
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
        JPanel stock = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, 0));
        stock.setOpaque(false);
        stock.add(name);
        stock.add(code);
        return stock;
    }

    /**
     * 初始化“策略生成”按钮
     */
    private void initStartBtn() {
        //开始交易
        trigger.setPreferredSize(new Dimension(componentW, componentH));
        trigger.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                VWAP vwap = VWAP.getInstance();
                Calendar start = Calendar.getInstance();
                start.set(0, 0, 0, 10, 0);
                Calendar end = Calendar.getInstance();
                end.set(0, 0, 0, 11, 0);
                Calendar now = Calendar.getInstance();
                now.set(2016, 9, 9, 10, 0);
                VWAP_Param param = new VWAP_Param(
                        Long.parseLong(quanVal.getText()),
                        codeText.getText(),
                        0.4, TimeUtil.timeToNode(now),
                        TimeUtil.timeToNode(start),
                        TimeUtil.timeToNode(end)
                );
                try {
                    List<VolumeVO> result = vwap.predictVn(param);
                    parent.updateMsgPanel(result);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
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
        /**
         * @param name 标签名称
         */
        TimePanel(String name) {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, H_GAP, 0));
            this.setOpaque(false);

            JLabel label = new JLabel(name);
            label.setFont(font);
            label.setForeground(Color.WHITE);
            label.setPreferredSize(new Dimension(componentW - (H_GAP << 1), componentH));
            this.add(label);

            JTextField hour = new JTextField();
            hour.setFont(font);
            hour.setPreferredSize(new Dimension(componentW + (H_GAP << 1), componentH));
            this.add(hour);

            JLabel split = new JLabel(":");
            split.setForeground(Color.white);
            split.setPreferredSize(new Dimension(H_GAP, V_GAP));
            split.setFont(new Font("黑体", Font.BOLD, 15));
            this.add(split);

            JTextField minute = new JTextField();
            minute.setFont(font);
            minute.setPreferredSize(new Dimension(componentW, componentH));
            this.add(minute);
        }
    }
}
