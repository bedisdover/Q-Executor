package present.panel.trade;

import vo.VolumeVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 交易面板的消息面板
 */
class ResultPanel extends JScrollPane {

    private Vector<String> vData;

    private DefaultTableModel tableModel;

    private static final int ROW_HEIGHT = 30;

    ResultPanel(int width, int height) {

        //表格表头
        Vector<String> header = new Vector<>();
        header.add("操作时间");
        header.add("类型");
        header.add("反馈结果");

        //表格数据
        vData = new Vector<>();
        tableModel = new DefaultTableModel(vData, header);
        JTable table = new JTable(tableModel) {
            private static final long serialVersionUID = 1L;
            //设表格不可编辑
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        table.setForeground(Color.WHITE);
//        table.setBackground(Color.BLACK);
//        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
//            @Override
//            public Component getComponent(int n) {
//                JLabel label = new JLabel();
//                label.setBackground(Color.BLACK);
//                label.setForeground(Color.WHITE);
//                return label;
//            }
//        });
        table.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        table.setRowHeight(ROW_HEIGHT);

        this.setPreferredSize(new Dimension(width, height));
        this.setViewportView(table);
        this.getViewport().setOpaque(false);
    }

    void update(List<VolumeVO> result, String type) {
        vData.clear();
        for (VolumeVO vo : result) {
            Vector<String> data = new Vector<>();
            data.addElement(vo.getTime());
            data.addElement(type);
            data.addElement("建议交易" + vo.getVolume() + "股");
            tableModel.addRow(data);
        }
        tableModel.fireTableDataChanged();
    }
}
