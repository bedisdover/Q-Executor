import present.utils.ColorUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by song on 16-9-3.
 * 测试表格
 */
public class TableTest {

    public static void main(String args[]) {

        final Object rowData[][] = {
                {1.0, "买盘", 1.0},
                {-1.0, "卖盘", -1.0},
                {2.3, "中性盘", 0.0}};
        final String columnNames[] = {"#", "English", "Roman"};

        final JTable table = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        table.setDefaultRenderer(Object.class, new MyRenderer(1, 2));

        JFrame frame = new JFrame("Resizing Table");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setSize(300, 150);
        frame.setVisible(true);

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
            return ColorUtil.getTextColor((Double) temp);
        } else if (temp instanceof String) {
            double number;

            try {
                number = Double.parseDouble((String) temp);
                return ColorUtil.getTextColor(number);
            } catch (NumberFormatException e) {
                return ColorUtil.getTextColor((String) temp);
            }
        }

        return Color.BLACK;
    }
}

