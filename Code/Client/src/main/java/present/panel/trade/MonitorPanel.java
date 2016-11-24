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
class MonitorPanel extends JPanel {

    private static final Font font = new Font("微软雅黑", Font.PLAIN, 11);

    MonitorPanel(int width, int height) {

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
        table.getTableHeader().setFont(font);
        //添加表格到滚动面板
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(table);

        //将标签页和表格添加到监控面板
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));
        this.add(bar, BorderLayout.NORTH);
        this.add(pane, BorderLayout.CENTER);
    }
}
