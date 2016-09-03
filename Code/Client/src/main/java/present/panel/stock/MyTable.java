package present.panel.stock;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * Created by song on 2016/8/27
 * <p>
 * 表格类
 */
class MyTable extends JTable {

    private Object[][] data;

    private String[] columnNames;

    MyTable(Object[][] data, String[] columnNames) {
        super(data, columnNames);

        this.data = data;
        this.columnNames = columnNames;

        init();
    }

    MyTable(PriceSharePanel.MyTableModel model) {
        super(model);
    }

    private void init() {
        //行高
        setRowHeight(30);
        getTableHeader().setPreferredSize(new Dimension(getTableHeader().getWidth(), 40));
        //无法修改表头大小
        getTableHeader().setResizingAllowed(true);
        //无法拖动表头
        getTableHeader().setReorderingAllowed(false);
        //设置每列的宽度
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        {
            //设置table表头居中
            DefaultTableCellRenderer thr = new DefaultTableCellRenderer();
            thr.setHorizontalAlignment(SwingConstants.CENTER);
            getTableHeader().setDefaultRenderer(thr);
            //设置table内容居中
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            setDefaultRenderer(Object.class, tcr);
        }
        //设置table内容不可编辑
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
        //设置表头排序方式
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        setRowSorter(sorter);
    }

    JScrollPane createTable() {
        return new JScrollPane(this, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
