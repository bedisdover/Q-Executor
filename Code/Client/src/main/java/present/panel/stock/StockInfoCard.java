package present.panel.stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Y481L on 2016/9/11.
 *
 * 股票信息卡片面板
 */
class StockInfoCard extends JPanel {

    private static final Font font = new Font("微软雅黑", Font.PLAIN, 16);

    private static final Color KEY_COLOR = Color.WHITE;

    private static final Color VAL_COLOR = Color.WHITE;

    /**
     *
     * @param name 股票名称
     * @param code 股票代码
     * @param price 股票价格
     * @param change 股票涨跌幅
     * @param width 面板宽度
     * @param height 面板高度
     */
    StockInfoCard(String name, String code, String price,
                         double change, int width, int height) {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);
        box.add(Box.createVerticalStrut(height / 5));

        //股票名称
        box.add(createLine("股票名称 : ", name));
        //股票代码
        box.add(createLine("股票代码 : ", code));
        //股票价格
        box.add(createLine("股票价格 : ", price));
        //股票涨跌幅
        box.add(createLine("涨跌幅   : ", String.valueOf(change)));

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(width, height));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    void addListener(MouseAdapter adapter) {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                adapter.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBorder(BorderFactory.createLineBorder(Color.WHITE));
            }
        });
    }

    private JPanel createLine( String key, String val) {
        JLabel keyLabel = new JLabel(key);
        keyLabel.setFont(font);
        keyLabel.setForeground(KEY_COLOR);
        JLabel valLabel = new JLabel(val);
        valLabel.setFont(font);
        valLabel.setForeground(VAL_COLOR);
        JPanel panel = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 10, 0
        ));
        panel.add(keyLabel);
        panel.add(valLabel);
        panel.setOpaque(false);
        return panel;
    }

}
