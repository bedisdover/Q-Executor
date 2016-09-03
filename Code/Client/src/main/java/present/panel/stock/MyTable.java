package present.panel.stock;

import present.utils.ColorUtil;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Created by song on 2016/8/27
 * <p>
 * 表格类
 */
class MyTable extends JTable {

    /**
     * 表格数据
     */
    private Object[][] data;

    /**
     * 列名
     */
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

class MyRenderer implements TableCellRenderer {

    private static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    private JTable table;

    /**
     * 列号数组，可能有多个
     */
    private int[] columnList;

    /**
     * 创建基于指定列的渲染器, 对字体颜色进行渲染
     *
     * @param columnNum 列号, 0-base
     */
    MyRenderer(int... columnNum) {
        this.columnList = columnNum;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        this.table = table;

        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        if (!isTarget(column)) {
            renderer.setForeground(Color.BLACK);
        } else {
            renderer.setForeground(getColor(row, column));
        }

        return renderer;
    }

    /**
     * 判断指定列是否为渲染目标
     *
     * @param column 列号
     */
    private boolean isTarget(int column) {
        for (int temp : columnList) {
            if (column == temp) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取渲染色
     *
     * @param row    行号
     * @param column 列号
     */
    private Color getColor(int row, int column) {
        Object temp = table.getValueAt(row, column);

        if (temp instanceof Double) {
            return ColorUtil.getTextColor((Double) table.getValueAt(row, column));
        } else if(temp instanceof String) {
            return ColorUtil.getTextColor((String) table.getValueAt(row, column));
        }

        return Color.BLACK;
    }
}

