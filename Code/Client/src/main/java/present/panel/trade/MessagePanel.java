package present.panel.trade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 交易面板的消息面板
 */
class MessagePanel extends JScrollPane {

    MessagePanel(int width, int height) {

        //表格表头
        Vector<String> header = new Vector<>();
        header.add("操作时间");
        header.add("类型");
        header.add("反馈结果");

        //表格数据
        Vector<String> vData = new Vector<>();
        DefaultTableModel tableModel = new DefaultTableModel(vData, header);
        Vector<String> test = new Vector<>();   //测试数据
        test.add("10:07:43");
        test.add("交易");
        test.add("下单成功，500单");
        for (int i = 0; i < 15; i++)
            tableModel.addRow(test);

        //表格
        JTable table = new JTable(tableModel) {
            private static final long serialVersionUID = 1L;
            //设表格不可编辑
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.setPreferredSize(new Dimension(width, height));
        this.setViewportView(table);
    }
}
