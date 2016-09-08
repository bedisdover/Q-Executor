package present.panel.stock.center;

import bl.stock.GetStockDataServiceImpl;
import blservice.stock.GetStockDataService;
import present.panel.stock.MyTable;
import present.panel.stock.StockPanel;
import util.NumberUtil;
import vo.StockInfoByPrice;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

/**
 * Created by Y481L on 2016/8/27.
 * <p>
 * 分价面板
 */
public class PriceSharePanel extends CenterPanel {

    private PriceSharePanel panel;

    private String stockCode;

    private StockPanel stockPanel;

    public PriceSharePanel(String stockCode, StockPanel stockPanel) {
        panel = this;
        this.stockCode = stockCode;
        this.stockPanel = stockPanel;

        super.init();
        getData();
    }

    @Override
    public void getData() {
        SwingWorker<List<StockInfoByPrice>, Void> worker = new SwingWorker<List<StockInfoByPrice>, Void>() {
            @Override
            protected List<StockInfoByPrice> doInBackground() throws Exception {
                GetStockDataService stockDataService = new GetStockDataServiceImpl();

                return stockDataService.getStockInfoByPrice(stockCode);
            }

            @Override
            protected void done() {
                try {
                    List<StockInfoByPrice> stockInfoByPriceList = get();

                    injectData(stockInfoByPriceList);
                } catch (Exception e) {
                    stockPanel.displayError();
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    private void injectData(List<StockInfoByPrice> stockInfoByPriceList) {
        SwingUtilities.invokeLater(() -> {
            panel.removeAll();
            panel.add(createTable(stockInfoByPriceList), BorderLayout.CENTER);

            panel.revalidate();
            panel.repaint();
        });
    }

    private JScrollPane createTable(List<StockInfoByPrice> stockInfoByPriceList) {
        //字段名称
        String[] names = {"成交价", "成交量(手)", "占比", "占比图"};
        Object[][] data = new Object[stockInfoByPriceList.size()][names.length];
        //采用自定义数据模型

        StockInfoByPrice temp;
        for (int i = 0; i < stockInfoByPriceList.size(); i++) {
            temp = stockInfoByPriceList.get(i);

            if (temp.getPrice() == 0) {
                stockInfoByPriceList.remove(i);
                continue;
            }

            data[i] = new Object[]{
                    temp.getPrice(),
                    (int) temp.getTrunover(),
                    NumberUtil.transferUnit(temp.getPercent() * 100) + "%",
                    temp.getPercent() * 100
            };
        }

        MyTableModel model = new MyTableModel(names, data);
        MyTable table = new MyTable(model);
        //插入单元格元素，采用自定义元素
        new ProgressBarColumn(table, 3);
        table.getColumnModel().getColumn(3).setPreferredWidth(600);
        //无法修改表头大小
        table.getTableHeader().setResizingAllowed(false);
        //无法拖动表头
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setPreferredSize(new Dimension(800, 540));

        return scrollPane;
    }


    //自定义表格模型
    private class MyTableModel extends AbstractTableModel {
        //单元格元素类型
        private Class[] cellType = {String.class, String.class, String.class, JProgressBar.class};
        //表头
        private String title[];
        //模拟数据
        private Object data[][];

        MyTableModel(String[] columnNames, Object[][] data) {
            this.title = columnNames;
            this.data = data;
        }

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

        double getMax() {
            double max = 0;
            for (Object[] aData : data) {
                if ((double) aData[3] > max) {
                    max = (double) aData[3];
                }
            }

            return max;
        }
    }

    /**
     * 自定义ProgressBar 列
     */
    private class ProgressBarColumn extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        private JProgressBar bar;

        ProgressBarColumn(JTable table, int column) {
            init(table, column);
        }

        private void init(JTable table, int column) {
            SwingUtilities.invokeLater(() -> {
                bar = new JProgressBar();
                bar.setBackground(Color.WHITE);
                bar.setForeground(Color.RED);
                bar.setStringPainted(false);
                bar.setBorderPainted(true);
                bar.setPreferredSize(new Dimension(100, 20));
                MyTableModel tableModel = (MyTableModel) table.getModel();
                bar.setMaximum((int) (tableModel.getMax() * 100) + 10);

                TableColumnModel columnModel = table.getColumnModel();
                columnModel.getColumn(column).setCellRenderer(this);
                columnModel.getColumn(column).setCellEditor(this);
            });
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return null;
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            bar.setValue((int) ((double) value * 100));

            return bar;
        }
    }
}
