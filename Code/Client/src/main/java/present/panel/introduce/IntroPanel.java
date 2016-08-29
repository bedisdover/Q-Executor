package present.panel.introduce;

import present.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 软件简介面板容器
 */
public class IntroPanel extends JPanel {

    public IntroPanel() {
        //标题面板
        ImagePanel header = new ImagePanel();
        header.setPreferredSize(new Dimension(1, MainFrame.PANEL_H << 1));  // “1”不发挥作用

        //滚动面板里面的view port
        Box inner = Box.createVerticalBox();
        inner.add(header);
        JScrollPane container = new JScrollPane(inner);

        this.setLayout(new BorderLayout());
        this.add(container, BorderLayout.CENTER);
    }
}
