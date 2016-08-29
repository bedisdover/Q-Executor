package present.panel.stock;

import present.MainFrame;
import present.PanelSwitcher;
import present.component.TextPlusBtn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 股票搜索面板
 */
public class SearchPanel extends JPanel {

    private static final int PADDING = 20;

    private static final int SEARCH_H = (MainFrame.PANEL_H >> 3) - PADDING;

    private static final int TABLE_H = MainFrame.PANEL_H - SEARCH_H - (PADDING << 1);

    public SearchPanel(PanelSwitcher switcher) {

        //搜索
        TextPlusBtn search = new TextPlusBtn(
                "输入股票名称或股票代码", MainFrame.PANEL_W >> 1, SEARCH_H
        );
        TextPlusBtn.Matcher matcher = new TextPlusBtn.Matcher() {
            @Override
            public Vector<String> getMatchString(String key) {
                Vector<String> v = new Vector<>();
                for(int i = 0; i < 15; ++i) {
                    v.addElement("test" + i);
                }
                return v;
            }

            @Override
            public void handleItemClicked(String text) {
                switcher.jump(new StockPanel("sh600008"));
            }
        };
        search.setMatcher(matcher);
        search.setBtnListener((e) -> switcher.jump(new StockPanel("sh600008")));
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(search);



        //滚动面板包含表格
        Box container = Box.createHorizontalBox();
        container.add(Box.createHorizontalStrut(PADDING));
        container.add(createSelfTable());
        container.add(Box.createHorizontalStrut(PADDING));
        container.add(createGeneralTable());
        container.add(Box.createHorizontalStrut(PADDING));
        container.add(createHotTable());
        container.add(Box.createHorizontalStrut(PADDING));

        //添加组件到主面板
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(MainFrame.PANEL_W >> 4));
        box.add(search);
        box.add(Box.createVerticalStrut(MainFrame.PANEL_W >> 4));
        box.add(container);
        box.add(Box.createVerticalStrut(PADDING));
        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);
    }

    /**
     * 创建总体股票表格
     * @return 总体股票表格
     */
    private JScrollPane createGeneralTable() {
        //表格表头
        Vector<String> header = new Vector<>(6);
        header.addElement("名称");
        header.addElement("最新价");
        header.addElement("涨跌额");
        header.addElement("涨跌幅");
        header.addElement("成交量/手");
        header.addElement("成交额/万");
        //表格数据
        Vector<String> test = new Vector<>(6);
        test.addElement("阿司匹林");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        Vector<String> data = new Vector<>();
        DefaultTableModel model = new DefaultTableModel(data, header);
        for (int i = 0; i < 16; ++i) model.addRow(test);
        //表格
        JTable general = createTable(model);

        JScrollPane pane = new JScrollPane(general);
        pane.setPreferredSize(new Dimension(
                MainFrame.PANEL_W >> 1, TABLE_H
        ));
        return pane;
    }

    /**
     * 创建自选股票表格
     * @return 自选股票表格
     */
    private Box createSelfTable() {
        //自选股
        Vector<String> header = new Vector<>(3);
        header.addElement("股票");
        header.addElement("价格");
        header.addElement("涨跌幅");
        Vector<String> data = new Vector<>();
        DefaultTableModel model = new DefaultTableModel(data, header);
        JTable self = createTable(model);

        JScrollPane pane = new JScrollPane(self);
        pane.setPreferredSize(new Dimension(
                (MainFrame.PANEL_W >> 2) - (PADDING << 1), TABLE_H - (PADDING << 1)
        ));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("自选股票");
        label.setPreferredSize(new Dimension(
                PADDING << 2, PADDING << 1
        ));
        panel.add(label);

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
                (MainFrame.PANEL_W >> 2) - (PADDING << 1), TABLE_H - (PADDING << 1)
        ));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("热门股票");
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
}
