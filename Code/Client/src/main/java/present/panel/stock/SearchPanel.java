package present.panel.stock;

import bl.SelfSelectServiceImpl;
import blservice.SelfSelectService;
import org.json.JSONException;
import org.json.JSONObject;
import present.MainFrame;
import present.PanelSwitcher;
import present.component.Link;
import present.component.TextPlusBtn;
import present.panel.account.LoginPanel;
import present.utils.StockJsonInfo;
import util.JsonUtil;
import vo.NowTimeSelectedStockInfoVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 股票搜索面板
 */
public class SearchPanel extends JPanel {

    private static final int PADDING = 20;

    private static final int SEARCH_H = (MainFrame.PANEL_H >> 3) - PADDING;

    private static final int TABLE_H = MainFrame.PANEL_H - SEARCH_H - (PADDING << 3);

    private SelfSelectService self = new SelfSelectServiceImpl();

    private PanelSwitcher switcher;

    public SearchPanel(PanelSwitcher switcher) {
        this.switcher = switcher;

        //搜索
        TextPlusBtn search = new TextPlusBtn(
                "输入股票名称或股票代码", MainFrame.PANEL_W >> 1, SEARCH_H
        );
        search.setOpaque(false);
        //设置字符串匹配规则
        search.setMatcher((key) -> {
            Vector<String> v = new Vector<>();
            List<JSONObject> list = JsonUtil.contains(
                    StockJsonInfo.JSON_KEYS, StockJsonInfo.JSON_PATH, key
            );
            for (JSONObject obj : list) {
                try {
                    v.addElement(
                        obj.getString(StockJsonInfo.KEY_CODE) + "  "
                        + obj.getString(StockJsonInfo.KEY_NAME) + "  "
                        + obj.getString(StockJsonInfo.KEY_INDUSTRY)
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return v;
        });
        //设置下拉提示列表监听
        search.setListClickHandler((text) -> switcher.jump(new StockPanel(text.split("  ")[0])));
        search.setListFocusHandler((field, text) -> {
            field.setText(text.split("  ")[0]);
        });
        //设置确定按钮监听
        search.setBtnListener((e) -> {
            try {
                StockPanel p = new StockPanel(search.getText());
                switcher.jump(p);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this, "名为 " + search.getText() + " " + "股票信息不存在"
                );
            }
        });
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setOpaque(false);
        p.add(search);


        //滚动面板包含表格
        JPanel container = new JPanel(new FlowLayout(
                FlowLayout.CENTER, PADDING << 2, 0
        ));
        container.setOpaque(false);
        container.add(createSelfTable());
//        container.add(Box.createHorizontalStrut(PADDING));
//        container.add(createGeneralTable());
        container.add(createHotTable());

        //添加组件到主面板
        Box box = Box.createVerticalBox();
        box.setOpaque(false);
        box.add(Box.createVerticalStrut(MainFrame.PANEL_W >> 4));
        box.add(search);
        box.add(Box.createVerticalStrut(MainFrame.PANEL_W >> 4));
        box.add(container);
        box.add(Box.createVerticalStrut(PADDING));
        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                new ImageIcon("src/main/resources/images/city5.jpg").getImage(),
                0, 0, MainFrame.PANEL_W, MainFrame.PANEL_H, null
        );
    }

    //    /**
//     * 创建总体股票表格
//     * @return 总体股票表格
//     */
//    private JScrollPane createGeneralTable() {
//        //表格表头
//        Vector<String> header = new Vector<>(6);
//        header.addElement("名称");
//        header.addElement("最新价");
//        header.addElement("涨跌额");
//        header.addElement("涨跌幅");
//        header.addElement("成交量/手");
//        header.addElement("成交额/万");
//        //表格数据
//        Vector<String> test = new Vector<>(6);
//        test.addElement("阿司匹林");
//        test.addElement("   1");
//        test.addElement("   1");
//        test.addElement("   1");
//        test.addElement("   1");
//        test.addElement("   1");
//        Vector<String> data = new Vector<>();
//        DefaultTableModel model = new DefaultTableModel(data, header);
//        for (int i = 0; i < 16; ++i) model.addRow(test);
//        //表格
//        JTable general = createTable(model);
//
//        JScrollPane pane = new JScrollPane(general);
//        pane.setPreferredSize(new Dimension(
//                MainFrame.PANEL_W >> 1, TABLE_H
//        ));
//        return pane;
//    }

    /**
     * 创建自选股票表格
     * @return 自选股票表格
     */
    private Box createSelfTable() {
        //自选股
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(0x2c4cb1));
        JLabel label = new JLabel("自选股票");
        label.setForeground(Color.WHITE);
        label.setPreferredSize(new Dimension(
                PADDING * 6, PADDING << 1
        ));

        Vector<String> header = new Vector<>(3);
        header.addElement("股票");
        header.addElement("价格");
        header.addElement("涨跌幅");
        Vector<String> data = new Vector<>();
        DefaultTableModel model = new DefaultTableModel(data, header);
        try {
            if (LoginPanel.IS_LOGIN) {
                List<NowTimeSelectedStockInfoVO> list = self.getUserSelectedStock(
                        LoginPanel.LOGIN_USER, LoginPanel.LOGIN_PW
                );
                list.forEach((vo) -> {
                    Vector<String> row = new Vector<>();
                    row.addElement(vo.getName());
                    row.addElement(String.valueOf(vo.getNowPri()));
                    row.add(String.valueOf(vo.getIncrePer()));
                    model.addRow(row);
                });
                panel.add(label);
            } else {
                panel.add(createLoginTip());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "网络异常");
        }
        JTable self = createTable(model);

        JScrollPane pane = new JScrollPane(self);
        pane.setPreferredSize(new Dimension(
                (MainFrame.PANEL_W / 3) - (PADDING << 1), TABLE_H - (PADDING << 1)
        ));


        Box box = Box.createVerticalBox();
        box.add(pane);
        box.add(panel);
        return box;
    }

    /**
     * 创建热门股票表格
     * @return 热门股票表格
     */
    private Box createHotTable() {
        Vector<String> header = new Vector<>(4);
        header.addElement("股票");
        header.addElement("价格");
        header.addElement("涨跌额");
        header.addElement("涨跌幅");
        Vector<String> data = new Vector<>();
        DefaultTableModel model = new DefaultTableModel(data, header);
        JTable hot = createTable(model);

        JScrollPane pane = new JScrollPane(hot);
        pane.setPreferredSize(new Dimension(
                (MainFrame.PANEL_W / 3) - (PADDING << 1), TABLE_H - (PADDING << 1)
        ));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(0x2c4cb1));
        JLabel label = new JLabel("热门股票");
        label.setForeground(Color.WHITE);
        label.setPreferredSize(new Dimension(
                PADDING << 2, PADDING << 1
        ));
        panel.add(label);

        Box box = Box.createVerticalBox();
        box.add(pane);
        box.add(panel);
        return box;
    }

    private JTable createTable(DefaultTableModel model) {
        return new JTable(model) {
            private static final long serialVersionUID = 1L;
            //设置表格不可编辑
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JPanel createLoginTip() {
        JPanel panel = new JPanel();

        JPanel up = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        up.setOpaque(false);
        JLabel label1 = new JLabel("请先");
        label1.setForeground(Color.WHITE);
        up.add(label1);

        Link link = new Link("登录");
        link.setHandler(() -> switcher.jump(new LoginPanel(switcher)));
        up.add(link);

        JLabel label2 = new JLabel("再查看自选股票");
        label2.setForeground(Color.WHITE);
        JPanel down = new JPanel(new FlowLayout(FlowLayout.CENTER));
        down.setOpaque(false);
        down.add(label2);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(up);
        panel.add(down);
        panel.setOpaque(false);
        return panel;
    }
}
