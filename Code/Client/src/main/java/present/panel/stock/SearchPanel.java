package present.panel.stock;

import bl.GetStockDataServiceImpl;
import bl.SelfSelectServiceImpl;
import blservice.GetStockDataService;
import blservice.SelfSelectService;
import org.json.JSONException;
import org.json.JSONObject;
import present.MainFrame;
import present.PanelSwitcher;
import present.component.Link;
import present.component.TextPlusBtn;
import present.panel.account.LoginPanel;
import present.utils.StockJsonInfo;
import util.JsonUtil;
import vo.HotStockVO;
import vo.NowTimeSelectedStockInfoVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 股票搜索面板
 */
public class SearchPanel extends JPanel {

    private static final int PADDING = 20;

    private static final int SEARCH_H = 42;

    private static final int TABLE_H = 370;

    private static final int TABLE_W = 290;

    private static final Color TABLE_BG = new Color(0xf6f6f6);

    //字符串切割符
    private static final String spliter = "--";

    private SelfSelectService self = new SelfSelectServiceImpl();

    private GetStockDataService hotStocks = new GetStockDataServiceImpl();

    private PanelSwitcher switcher;

    private DefaultTableModel hotTableModel;

    private MyTable hotTable;

    public SearchPanel(PanelSwitcher switcher) {
        this.switcher = switcher;

        //搜索
        TextPlusBtn search = this.createSearchPanel();
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setOpaque(false);
        p.add(search);


        //滚动面板包含表格
        JPanel container = new JPanel(new FlowLayout(
                FlowLayout.CENTER, PADDING << 2, 0
        ));
        container.setOpaque(false);
        container.add(createSelfTable());
        container.add(createHotTable());
        getData();

        //添加组件到主面板
        Box box = Box.createVerticalBox();
        box.setOpaque(false);
        box.add(Box.createVerticalStrut(PADDING << 2));
        box.add(search);
        box.add(Box.createVerticalStrut(PADDING << 1));
        box.add(container);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.add(box, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.NORTH);
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(
//                new ImageIcon("src/main/resources/images/city4.jpg").getImage(),
//                0, 0, this.getWidth(), this.getHeight(), null
//        );
//    }

    private TextPlusBtn createSearchPanel() {
        TextPlusBtn search = new TextPlusBtn(
                "输入股票名称或股票代码", MainFrame.PANEL_W >> 1, SEARCH_H
        );
        search.setOpaque(false);
        //设置字符串匹配规则
        search.setMatcher((key) -> {
            Vector<String> v = new Vector<>();
            List<JSONObject> list = JsonUtil.contains(
                    StockJsonInfo.JSON_KEYS, StockJsonInfo.JSON_PATH, key
            );
            for (JSONObject obj : list) {
                try {
                    v.addElement(
                            obj.getString(StockJsonInfo.KEY_CODE) + spliter
                                    + obj.getString(StockJsonInfo.KEY_NAME) + spliter
                                    + obj.getString(StockJsonInfo.KEY_INDUSTRY)
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return v;
        });
        //设置下拉提示列表监听
        search.setListClickHandler((text) ->
                switcher.jump(new StockPanel(text.split(spliter)[0]))
        );
        search.setListFocusHandler((field, text) -> field.setText(text.split(spliter)[1]));
        //设置确定按钮监听
        search.setBtnListener((e) -> {
            try {
                StockPanel p = new StockPanel(JsonUtil.confirm(StockJsonInfo.KEY_NAME
                        , StockJsonInfo.KEY_CODE, search.getText(), StockJsonInfo.JSON_PATH));
                switcher.jump(p);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this, "名为 " + search.getText() + " " + "股票信息不存在"
                );
            }
        });
        return search;
    }

    /**
     * 创建自选股票表格
     * @return 自选股票表格
     */
    private JPanel createSelfTable() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("自选股票");
        panel.setPreferredSize(new Dimension(
                TABLE_W - (PADDING << 1), PADDING << 1
        ));

        Vector<String> header = new Vector<>(4);
        header.addElement("代码");
        header.addElement("股票");
        header.addElement("价格");
        header.addElement("涨跌幅");
        Vector<String> data = new Vector<>();
        DefaultTableModel model = new DefaultTableModel(data, header);
        try {
            if (LoginPanel.IS_LOGIN) {
                List<NowTimeSelectedStockInfoVO> list = self.getUserSelectedStock(
                        LoginPanel.LOGIN_USER, LoginPanel.LOGIN_PW
                );
                list.forEach((vo) -> {
                    Vector<String> row = new Vector<>();
                    row.addElement(vo.getGid());
                    row.addElement(vo.getName());
                    row.addElement(String.valueOf(vo.getNowPri()));
                    row.add(String.valueOf(vo.getIncrePer()));
                    model.addRow(row);
                });
                panel.add(label);
            } else {
                panel.add(createLoginTip());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "网络异常");
        }

        JTable self = new MyTable(model);
        self.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                jumpToStockPanel(self);
            }
        });

        JScrollPane pane = new JScrollPane(self);
        pane.setPreferredSize(new Dimension(
                TABLE_W - (PADDING << 2), TABLE_H - (PADDING << 1)
        ));

        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.SOUTH);
        container.add(pane, BorderLayout.CENTER);
        return wrapTable(container);
    }

    /**
     * 创建热门股票表格
     * @return 热门股票表格
     */
    private JPanel createHotTable() {
        Vector<String> header = new Vector<>(4);
        header.addElement("代码");
        header.addElement("股票");
        header.addElement("价格");
        header.addElement("涨跌额");
        Vector<String> data = new Vector<>();
        hotTableModel = new DefaultTableModel(data, header);

        hotTable = new MyTable(hotTableModel);
        hotTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                jumpToStockPanel(hotTable);
            }
        });

        JScrollPane pane = new JScrollPane(hotTable);
        pane.setPreferredSize(new Dimension(
                TABLE_W - (PADDING << 2), TABLE_H - (PADDING << 1)
        ));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("热门股票");
        panel.setPreferredSize(new Dimension(
                TABLE_W - (PADDING << 1), PADDING << 1
        ));
        panel.add(label);

        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.SOUTH);
        container.add(pane, BorderLayout.CENTER);
        return wrapTable(container);
    }

    private JPanel wrapTable(JPanel table) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(TABLE_BG);

        //北边空白面板
        JPanel north = new JPanel();
        north.setOpaque(false);
        north.setPreferredSize(new Dimension(TABLE_W, PADDING));
        container.add(north, BorderLayout.NORTH);

        //西边空白面板
        JPanel west = new JPanel();
        west.setOpaque(false);
        west.setPreferredSize(new Dimension(PADDING, TABLE_H - (PADDING << 1)));
        container.add(west, BorderLayout.WEST);

        //东边空白面板
        JPanel east = new JPanel();
        east.setOpaque(false);
        east.setPreferredSize(new Dimension(PADDING, TABLE_H - (PADDING << 1)));
        container.add(east, BorderLayout.EAST);

        //南边空白面板
        JPanel south = new JPanel();
        south.setOpaque(false);
        south.setPreferredSize(new Dimension(TABLE_W, PADDING));
        container.add(south, BorderLayout.SOUTH);

        //中间表格
        container.add(table, BorderLayout.CENTER);

        return container;
    }

    private JPanel createLoginTip() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel up = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        up.setOpaque(false);
        JLabel label1 = new JLabel("请先");
        up.add(label1);

        Link link = new Link("登录");
        link.setHandler(() -> switcher.jump(new LoginPanel(switcher)));
        up.add(link);

        panel.add(up);
        panel.setOpaque(false);
        return panel;
    }

    private void getData() {
        SwingWorker worker = new SwingWorker<List<HotStockVO>, Void>() {
            @Override
            protected List<HotStockVO> doInBackground() throws Exception {
                try {
                    return hotStocks.getHotStock();
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ArrayList<>();
                }
            }

            @Override
            protected void done() {
                super.done();
                try {
                    List<HotStockVO> hotDatas = get();
                    for (HotStockVO vo : hotDatas) {
                        Vector<String> v = new Vector<>(4);
                        v.addElement(vo.getCode());
                        v.addElement(vo.getName());
                        v.addElement(vo.getCurrentPrice());
                        v.addElement(String.valueOf(vo.getPchange()));
                        hotTableModel.addRow(v);
                    }
                    hotTable.setRenderer(new MyRenderer(1), 3);
                    hotTableModel.fireTableDataChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    private void jumpToStockPanel(JTable table) {
             int row = table.getSelectedRow();
             row = row < 0 ? 0 : row;
             switcher.jump(new StockPanel((String)table.getValueAt(row, 0)));
    }
}
