package present.panel.trade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 交易面板的监控面板
 */
public class MonitorPanel extends JPanel {

    MonitorPanel(int width, int height) {
//        superMonitor.setFont(new Font("微软雅黑", 0, 10));
//        superMonitor.setFocusPainted(false);
//        subMonitor.setFont(new Font("微软雅黑", 0, 10));
//        subMonitor.setFocusPainted(false);
//        keepStock.setFont(new Font("微软雅黑", 0, 10));
//        keepStock.setFocusPainted(false);

        //标签页，用按钮模拟
        JButton superMonitor = new JButton("母单监控");
        JButton subMonitor = new JButton("子单监控");
        JButton keepStock = new JButton("账户持仓");
        JToolBar bar = new JToolBar();
        bar.setLayout(new FlowLayout(FlowLayout.LEFT));
        bar.add(superMonitor);
        bar.add(subMonitor);
        bar.add(keepStock);


        //表格表头
        Vector<String> header = new Vector<>();
        header.add("序号");
        header.add("交易时间");
        header.add("证券代码");
        header.add("证券名称");
        header.add("交易");
        header.add("委托数量");
        header.add("委托价格");
        header.add("合同编号");
        header.add("订单状态");
        header.add("成交数量");
        header.add("成交价格");
        //表格数据
        Vector<String> vData = new Vector<>();
        DefaultTableModel tableModel = new DefaultTableModel(vData, header);
        Vector<String> test = new Vector<>();
        test.add("12345");
        test.add("10:35:37");
        test.add("600536");
        test.add("中国石油");
        test.add("交易");
        test.add("600");
        test.add("3.77");
        test.add("54321");
        test.add("已审批");
        test.add("5000");
        test.add("6.35");
        for (int i = 0; i < 15; i++)
            tableModel.addRow(test);
        //表格
        JTable table = new JTable(tableModel) {
            private static final long serialVersionUID = 1L;
            //设置表格不可编辑
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //添加表格到滚动面板
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(table);

        //将标签页和表格添加到监控面板
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));
        this.add(bar, BorderLayout.NORTH);
        this.add(pane, BorderLayout.CENTER);
//        pane.setOpaque(false);//将中间的viewport设置为透明
//        pane.setColumnHeaderView(table.getTableHeader());
//
//        table.getTableHeader().setFont(new Font("微软雅黑", 0, 8));
//
//
//        pane.getColumnHeader().setOpaque(false);//再取出头部，并设置为透明
//
//        table.setDefaultRenderer(Object.class, render);
//        table.setRowHeight(30);
//        JTableHeader headerr2 = table.getTableHeader();//获取头部
//        headerr2.setPreferredSize(new Dimension(30, 26));
//
//        table.setPreferredScrollableViewportSize(new Dimension(500, 400));
//        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        table.setShowVerticalLines(true);
//        table.setShowHorizontalLines(true);
//        pane.getViewport().add(table);
//        table.setFillsViewportHeight(true);
//        table.setFont(new Font("微软雅黑", 0, 10));
//        table.setOpaque(false);
//        pane.setOpaque(false);
//        pane.getViewport().setOpaque(false);
    }
}
