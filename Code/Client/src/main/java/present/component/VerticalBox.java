package present.component;

import present.panel.account.AccountConst;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/9/10.
 *
 * 信息项垂直排列的面板
 * 现在用于登录、注册等面板，将来可参数化作为通用组件
 */
public class VerticalBox extends JPanel {

    private Box box = Box.createVerticalBox();

    protected VerticalBox() {
        box.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, AccountConst.LEFT_PADDING, 0));
        this.add(box);
        this.setOpaque(false);
    }

    /**
     * 添加间隔
     * @param height 间隔
     */
    protected void addStrut(int height) {
        box.add(Box.createVerticalStrut(height));
    }

    /**
     * 添加一个信息项
     * @param component 待添加的族健脑
     */
    protected void addItem(JComponent component) {
        component.setPreferredSize(new Dimension(
                AccountConst.BUTTON_W, AccountConst.BUTTON_H
        ));
        component.setFont(AccountConst.font);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.add(component);
        box.add(panel);
    }

    /**
     * 添加自定义的一行信息项
     * @param panel 待添加面板
     */
    protected void addLine(JPanel panel) {
        box.add(panel);
    }
}
