package present.panel.introduce;

import present.MainFrame;
import present.PanelSwitcher;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 软件简介面板容器
 */
public class IntroPanel extends JPanel {

    public IntroPanel(PanelSwitcher switcher) {
        //标题面板
        ImagePanel header = new ImagePanel(switcher);

//        //滚动面板里面的view port
//        Box inner = Box.createVerticalBox();
//        inner.add(header);
//        JScrollPane container = new JScrollPane(inner);

        this.setLayout(new BorderLayout());
        this.add(header, BorderLayout.CENTER);
    }
}
