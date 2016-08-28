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
        search.setBtnListener((e) -> {
            switcher.jump(new StockPanel("sh600008"));
        });
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(search);

        //表格表头
        Vector<String> header = new Vector<>(12);
        header.addElement("名称");
        header.addElement("最新价");
        header.addElement("涨跌额");
        header.addElement("涨跌幅");
        header.addElement("买入");
        header.addElement("卖出");
        header.addElement("昨收");
        header.addElement("今开");
        header.addElement("最高");
        header.addElement("最低");
        header.addElement("成交量/手");
        header.addElement("成交额/万");
        //表格数据
        Vector<String> test = new Vector<>(12);
        test.addElement("阿司匹林");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        test.addElement("   1");
        Vector<String> data = new Vector<>();
        DefaultTableModel model = new DefaultTableModel(data, header);
        for (int i = 0; i < 16; ++i) model.addRow(test);
        //表格
        JTable table = new JTable(model) {
            private static final long serialVersionUID = 1L;
            //设置表格不可编辑
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //滚动面板包含表格
        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(
                (int)(MainFrame.PANEL_W * 0.8), TABLE_H
        ));
        Box container = Box.createHorizontalBox();
        container.add(Box.createHorizontalStrut((int)(MainFrame.PANEL_W * 0.1)));
        container.add(pane);
        container.add(Box.createHorizontalStrut((int)(MainFrame.PANEL_W * 0.1)));

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
}
