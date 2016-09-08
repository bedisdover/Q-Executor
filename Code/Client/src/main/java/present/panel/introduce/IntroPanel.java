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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                new ImageIcon("src/main/resources/images/introduce.jpg").getImage(),
                0, 0, this.getWidth(), this.getHeight(), null
        );
    }
}
