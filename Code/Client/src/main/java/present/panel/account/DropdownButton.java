package present.panel.account;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 下拉按钮
 */
public class DropdownButton extends JPanel {

    private JButton button;

    private Vector<JMenuItem> menuItems;

    private int panelW;

    private int panelH;

    public DropdownButton(JButton btn, Vector<JMenuItem> menuItems) {
        this.button = btn;
        this.menuItems = menuItems;
        Dimension d = btn.getPreferredSize();
        this.panelW = d.width;
        this.panelH = d.height;
        this.init();
    }

    private void init() {
        JPopupMenu menu = new JPopupMenu();
        menuItems.forEach((item) -> {
            item.setPreferredSize(new Dimension(panelW, panelH));
            menu.add(item);
        });
        button.addActionListener((e) -> menu.show(this, 0, panelH));

        this.setLayout(new BorderLayout());
        this.add(button, BorderLayout.CENTER);
    }

}
