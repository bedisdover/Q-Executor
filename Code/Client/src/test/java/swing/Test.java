package swing;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

//测试窗体
public class Test extends JFrame {

    private Test() {
        init();
    }

    //初始化界面
    private void init() {
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("测试自定义JTable");
        //采用自定义数据模型
        MyTableModel model = new MyTableModel();
        JTable table = new JTable(model);
        //插入单元格元素，采用自定义元素
        new ProgressBarColumn(table, 3);
        table.setRowHeight(30);

        JScrollPane jsp = new JScrollPane(table);
        this.getContentPane().add(jsp, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Test();
    }
}

//自定义表格模型
class MyTableModel extends AbstractTableModel {
    //单元格元素类型
    private Class[] cellType = {String.class, String.class, String.class, JProgressBar.class};
    //表头
    private String title[] = {"成交价(元)", "成交量(股)", "占比", "占比图"};
    //模拟数据
    private Object data[][] = {
            {"1", "", "", 0},
            {"2", "", "", 60},
            {"3", "", "", 25}
    };

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cellType[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        return title[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return title.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        return data[r][c];
    }

    @Override
    public void setValueAt(Object value, int r, int c) {
        data[r][c] = value;
        this.fireTableCellUpdated(r, c);
    }
}

/**
 * 自定义ProgressBar 列
 */
class ProgressBarColumn extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
    private JProgressBar bar;

    ProgressBarColumn(JTable table, int column) {
        super();
        bar = new JProgressBar();
        bar.setMaximum(100);
        bar.setBackground(Color.RED);
        bar.setForeground(Color.GREEN);
        bar.setStringPainted(true);
        bar.setBorderPainted(false);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    @Override
    public Component getTableCellRendererComponent(JTable arg0, Object value,
                                                   boolean arg2, boolean arg3, int arg4, int arg5) {
        bar.setValue(Integer.parseInt(value.toString()));
        return bar;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return null;
    }
}
