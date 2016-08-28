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
public class MessagePanel extends JScrollPane {

    MessagePanel(int width, int height) {
//        JPanel line3 = new JPanel();//第三行
//        line3.setPreferredSize(new Dimension(800, 450));
//        line3.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
//        line3.setLayout(new BoxLayout(line3, BoxLayout.X_AXIS));
//
//        JPanel box11 = new JPanel();
//        box11.setLayout(new BoxLayout(box11, BoxLayout.X_AXIS));
//        box11.setPreferredSize(new Dimension(300, 150));
//        box11.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
//
//        JScrollPane scrollPane = new JScrollPane();


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

//        scrollPane.setOpaque(false);//将中间的viewport设置为透明
//        scrollPane.setColumnHeaderView(table.getTableHeader());
//
//        table.getTableHeader().setFont(new Font("微软雅黑", 0, 12));
//
//
//        scrollPane.getColumnHeader().setOpaque(false);//再取出头部，并设置为透明
//        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
//        render.setHorizontalAlignment(SwingConstants.CENTER);
//        render.setOpaque(true); //将渲染器设置为透明
//
//        table.setDefaultRenderer(Object.class, render);
//        table.setRowHeight(30);
//        JTableHeader headerr = table.getTableHeader();//获取头部
//
//        headerr.setPreferredSize(new Dimension(30, 26));
//
//        table.setPreferredScrollableViewportSize(new Dimension(280, 400));
//        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        table.setShowVerticalLines(true);
//        table.setShowHorizontalLines(true);
//        scrollPane.getViewport().add(table);
//        table.setFillsViewportHeight(true);
//        table.setFont(font);
//        table.setOpaque(false);
//        scrollPane.getViewport().setOpaque(false);
//
//        box11.add(scrollPane);
//        line3.add(box11);
    }
}
