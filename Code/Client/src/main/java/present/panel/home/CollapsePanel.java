package present.panel.home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/27.
 *
 * 折叠面板，内含可折叠按钮
 */
class CollapsePanel extends JPanel {

    private Box buttonBox;

    private JButton parent;

    private Vector<SubButton> buttons = new Vector<>();

    private int buttonNum = 1;

    private int btnW;

    private int btnH;

    private boolean isCollapse = true;

    private static final int PADDING = 6;

    /**
     *
     * @param parent 折叠面板所在父容器
     * @param btnW 折叠面板中的按钮宽度
     * @param btnH 折叠面板中的按钮高度
     * @param listener 折叠父按钮的点击事件处理器
     */
    CollapsePanel(JButton parent, int btnW, int btnH, ActionListener listener) {
        this.parent = parent;
        this.parent.setPreferredSize(new Dimension(btnW, btnH));
        this.btnW = btnW;
        this.btnH = btnH;
        this.parent.addActionListener((e) -> {
            listener.actionPerformed(e);
            toggle();
        });
        this.addComponents();
    }

    void addSubButton(JButton btn) {
        SubButton sub = new SubButton(btn);
        sub.setVisible(false);
        buttons.addElement(sub);
        buttonBox.add(sub);
        ++buttonNum;
    }

    private void addComponents() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(parent, BorderLayout.CENTER);

        buttonBox = Box.createVerticalBox();
        buttonBox.add(panel);

        this.setLayout(new BorderLayout());
        this.add(buttonBox, BorderLayout.CENTER);
    }

//    private JPanel wrapButton(JButton btn) {
//        Box box = Box.createHorizontalBox();
//        box.add(Box.createHorizontalStrut(PADDING));
//        box.add(btn);
//        box.add(Box.createHorizontalStrut(PADDING));
//
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(box, BorderLayout.CENTER);
//        return panel;
//    }

    /**
     * 当按钮处于折叠状态，将按钮展开
     * 当按钮处于展开状态，将按钮折叠
     */
    private void toggle() {
        if(isCollapse) {
            buttons.forEach((btn) -> btn.setVisible(true));
            this.setPreferredSize(new Dimension(btnW, (btnH + PADDING) * buttonNum));
        }else {
            buttons.forEach((btn) -> btn.setVisible(false));
            this.setPreferredSize(new Dimension(btnW, btnH));
        }
        isCollapse = !isCollapse;
    }

    private class SubButton extends JPanel {
        SubButton(JButton btn) {
            btn.setPreferredSize(new Dimension(btnW, btnH));
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(btn, BorderLayout.CENTER);

            Box box = Box.createVerticalBox();
            box.add(Box.createVerticalStrut(PADDING));
            box.add(panel);

            this.setLayout(new BorderLayout());
            this.add(box, BorderLayout.CENTER);
        }
    }
}
