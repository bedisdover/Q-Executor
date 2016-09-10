package present.panel.stock.center;

import bl.stock.GetStockDataServiceImpl;
import blservice.stock.GetStockDataService;
import present.panel.stock.MyTable;
import present.panel.stock.StockPanel;
import present.panel.stock.west.CurrentDataPanel;
import present.utils.ColorUtil;
import util.NumberUtil;
import vo.StockInfoByPrice;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Y481L on 2016/8/27.
 * <p>
 * 分价面板
 */
public class PriceSharePanel extends CenterPanel {

    private PriceSharePanel panel;

    private String stockCode;

    private StockPanel stockPanel;

    private JScrollPane scrollPane;

    public PriceSharePanel(String stockCode, StockPanel stockPanel) {
        panel = this;
        this.stockCode = stockCode;
        this.stockPanel = stockPanel;

        super.init();
        getData();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 10);
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
            if (scrollPane == null) {
                panel.removeAll();
            } else {
                panel.remove(scrollPane);
            }

//            panel.setLayout(new GridBagLayout());
//
//            GridBagConstraints constraints = new GridBagConstraints();
//
//            constraints.gridwidth = 1;
//            constraints.weightx = 1;
//            constraints.weighty = 1;
//            panel.add(new JPanel(), constraints);
//
//            constraints.gridwidth = 5;
//            constraints.weightx = 1;
//            constraints.weighty = 1;
//            panel.add(createTable(stockInfoByPriceList), constraints);
//
//            constraints.gridwidth = 0;
//            constraints.weightx = 1;
//            constraints.weighty = 0;
//            panel.add(new JPanel(), constraints);
//
//            constraints.gridwidth = 0;
//            constraints.weightx = 0;
//            constraints.weighty = 1;
//            panel.add(new JPanel(), constraints);
            panel.add(createTable(stockInfoByPriceList), BorderLayout.WEST);

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

            // 倒序显示数据
            data[data.length - i - 1] = new Object[]{
                    temp.getPrice(),
                    (int) temp.getTrunover(),
                    NumberUtil.transferUnit(temp.getPercent() * 100) + "%",
                    temp.getPercent() * 100
            };
        }

        MyTableModel model = new MyTableModel(names, data);
        MyTable table = new MyTable(model);
        //插入单元格元素，采用自定义元素
        new ProgressBarColumn(table, 3, CurrentDataPanel.getClose());
        table.getColumnModel().getColumn(3).setPreferredWidth(600);
        //无法修改表头大小
        table.getTableHeader().setResizingAllowed(false);
        //无法拖动表头
        table.getTableHeader().setReorderingAllowed(false);

        scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setPreferredSize(new Dimension(800, 500));

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

        /**
         * 上一个成交日的收盘价
         */
        private double close;

        ProgressBarColumn(JTable table, int column, double close) {
            this.close = close;

            init(table, column);
        }

        private void init(JTable table, int column) {
            SwingUtilities.invokeLater(() -> {
                bar = new JProgressBar();
                bar.setBackground(Color.WHITE);
                bar.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 5, Color.WHITE));
                bar.setStringPainted(false);
                bar.setBorderPainted(true);
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

            // 获取当前行的价格
            double temp = (double) table.getValueAt(row, 0);

            bar.setForeground(ColorUtil.getColorByComparing(temp, close));

            return bar;
        }
    }
}
