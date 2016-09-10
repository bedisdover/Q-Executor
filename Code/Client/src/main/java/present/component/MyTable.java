package present.component;

import present.utils.ColorUtil;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 * Created by song on 2016/8/27
 * <p>
 * 表格类
 */
public class MyTable extends JTable {

    /**
     * 表格数据
     */
    private Object[][] data;

    /**
     * 列名
     */
    private String[] columnNames;

    private TableModel model;

    public MyTable(Object[][] data, String[] columnNames) {
        super(data, columnNames);

        this.data = data;
        this.columnNames = columnNames;

        model = new DefaultTableModel(data, columnNames);

        init();
    }

    public MyTable(AbstractTableModel model) {
        super(model);
        this.model = model;

        init();
    }

    private void init() {
        //行高
        setRowHeight(30);
        JTableHeader header = getTableHeader();
        header.setPreferredSize(new Dimension(getTableHeader().getWidth(), 40));
        //无法修改表头大小
        header.setResizingAllowed(false);
        //无法拖动表头
        header.setReorderingAllowed(false);
        //设置每列的宽度
//        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        {
            //设置table表头居中
            DefaultTableCellRenderer thr = new DefaultTableCellRenderer();
            thr.setHorizontalAlignment(SwingConstants.CENTER);
            header.setDefaultRenderer(thr);
        }

        //设置表头排序方式
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        setRowSorter(sorter);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void setRenderer(TableCellRenderer renderer) {
        this.setRenderer(renderer, columnNames.length);
    }

    public void setRenderer(TableCellRenderer renderer, int columnNum) {
        setDefaultRenderer(Object.class, renderer);

        for (int i = 0; i < columnNum; i++) {
            getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }


    public JScrollPane createTable() {
        return new JScrollPane(this, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}

