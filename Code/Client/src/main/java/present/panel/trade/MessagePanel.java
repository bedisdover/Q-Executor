package present.panel.trade;

import vo.VolumeVO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 交易面板的消息面板
 */
class MessagePanel extends JScrollPane {

    private Vector<String> header;

    private Vector<String> vData;

    private DefaultTableModel tableModel;

    //表格
    JTable table;

    MessagePanel(int width, int height) {

        //表格表头
        header = new Vector<>();
        header.add("操作时间");
        header.add("类型");
        header.add("反馈结果");

        //表格数据
        vData = new Vector<>();
        tableModel = new DefaultTableModel(vData, header);
//        Vector<String> test = new Vector<>();   //测试数据
//        test.add("10:07:43");
//        test.add("交易");
//        test.add("下单成功，500单");
//        for (int i = 0; i < 15; i++)
//            tableModel.addRow(test);

        table = new JTable(tableModel) {
            private static final long serialVersionUID = 1L;
            //设表格不可编辑
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setForeground(Color.WHITE);
        table.setBackground(Color.BLACK);
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getComponent(int n) {
                Component comp = new JLabel();
                comp.setBackground(Color.BLACK);
                return comp;
            }
        });

        this.setPreferredSize(new Dimension(width, height));
        this.setViewportView(table);
    }

    public void update(List<VolumeVO> result, String type) {
        vData.clear();
        for (VolumeVO vo : result) {
            Vector<String> data = new Vector<>();
            data.addElement(vo.getTime());
            data.addElement(type);
            data.addElement("交易了" + vo.getVolume() + "股");
            tableModel.addRow(data);
            System.out.println(vo.getTime());
            System.out.println(vo.getVolume());
        }
        System.out.println(result.size());
        tableModel.fireTableDataChanged();
    }
}
