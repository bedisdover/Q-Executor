package present.panel.account;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import present.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/11/24.
 *
 * 矩形框
 */
class VerticalBox extends JPanel {

    private Box box = Box.createVerticalBox();

    private static final int HEADER_W = 360;

    private static final int HEADER_H = 60;

    private static final int BUTTON_H = 40;

    private static final int BUTTON_GAP = 36;

    private static final int BORDER_SIZE = 1;

    private static final Font font = new Font("微软雅黑", Font.PLAIN, 16);

    private static final int LEFT_PADDING = 40;

    VerticalBox() {
        //头部文字
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(
                        ImageLoader.account_header, 0, 0,
                        HEADER_W, HEADER_H, null
                );
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(
                HEADER_W, HEADER_H
        ));
        box.add(Box.createVerticalStrut(AccountPanel.NAV_UP_H));
        box.add(header);

        this.setLayout(new FlowLayout(FlowLayout.LEFT, LEFT_PADDING, 0));
        this.add(box);
        this.setOpaque(false);
    }

    /**
     * 添加一个信息项
     * @param component 待添加的组件
     */
    void addItem(JComponent component) {
        component.setPreferredSize(new Dimension(
                HEADER_W - (BUTTON_GAP << 1) - BORDER_SIZE,
                BUTTON_H
        ));
        component.setFont(font);
        component.setOpaque(false);
        component.setForeground(Color.GRAY);
        component.setBorder(BorderFactory.createMatteBorder(
                0, 0, BORDER_SIZE, 0, Color.GRAY
        ));
        JPanel panel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, BUTTON_GAP, BUTTON_GAP >> 1
        ));
        panel.add(component);
        box.add(panel);
    }

    void addButton(JButton btn) {
        btn.setPreferredSize(new Dimension(
                HEADER_W - (BUTTON_GAP << 1) - BORDER_SIZE,
                BUTTON_H
        ));
        btn.setFont(font);
        btn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        JPanel panel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, BUTTON_GAP, BUTTON_GAP >> 1
        ));
        panel.add(btn);
        box.add(panel);
        box.add(Box.createHorizontalStrut(BUTTON_GAP >> 1));
    }
}
