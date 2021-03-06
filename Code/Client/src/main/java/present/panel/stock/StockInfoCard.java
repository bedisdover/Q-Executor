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

    private static final Font font = new Font("等线", Font.PLAIN, 16);

    private static final Color KEY_COLOR = Color.WHITE;

    private static final Color VAL_COLOR = Color.CYAN;

    private static final Color bg = new Color(0xff9700);

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
        box.add(createChangeCard(change));

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(bg);
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
                setBackground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBackground(bg);
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
                FlowLayout.CENTER, 10, 0
        ));
        panel.add(keyLabel);
        panel.add(valLabel);
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createChangeCard(double value) {
        JLabel keyLabel = new JLabel("涨跌幅  : ");
        keyLabel.setFont(font);
        keyLabel.setForeground(KEY_COLOR);
        JLabel valLabel = new JLabel(String.valueOf(value));
        valLabel.setFont(font);
        if(value < 0) {
            valLabel.setForeground(new Color(0x2CFF23));
        } else if(value == 0) {
            valLabel.setForeground(Color.BLACK);
        } else {
            valLabel.setForeground(new Color(0xFF0000));
        }
        JPanel panel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 10, 0
        ));
        panel.add(keyLabel);
        panel.add(valLabel);
        panel.setOpaque(false);
        return panel;
    }

}
