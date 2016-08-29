package present.panel.introduce;

import present.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/29.
 *
 * 简介面板中的图像面板
 */
class ImagePanel extends JPanel {

    ImagePanel() {
        this.setLayout(new BorderLayout());
        this.add(new JButton("click me"), BorderLayout.NORTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(
                new ImageIcon("src/main/resources/images/background.jpg").getImage(),
                0, 0, MainFrame.PANEL_W, MainFrame.PANEL_H, null
        );
    }
}
