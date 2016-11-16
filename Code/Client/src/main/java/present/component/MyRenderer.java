package present.component;

import present.utils.ColorUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MyRenderer extends DefaultTableCellRenderer {

    private static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    /**
     * 列号数组，可能有多个
     */
    private int[] columnList;

    /**
     * 创建基于指定列的渲染器, 对字体颜色进行渲染
     *
     * @param columnNum 列号, 0-base
     */
    public MyRenderer(int... columnNum) {
        this.columnList = columnNum;

        DEFAULT_RENDERER.setHorizontalAlignment(SwingConstants.CENTER);
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        if (!isTarget(column)) {
            renderer.setForeground(Color.BLACK);
        } else {
            renderer.setForeground(getColor(value));
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
     */
    private Color getColor(Object value) {
        if (value instanceof Double) {
            return ColorUtil.getTextColor((Double) value);
        } else if (value instanceof String) {
            return ColorUtil.getTextColor((String) value);
        }

        return Color.BLACK;
    }
}
