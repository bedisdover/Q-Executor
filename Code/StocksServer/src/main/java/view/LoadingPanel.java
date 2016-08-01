package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 王栋 on 2016/7/21 0021.
 */
public class LoadingPanel extends JPanel {

    public LoadingPanel(){

        this.setBackground(new Color(102,206,255));

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = new ImageIcon(LoadingPanel.class.getClassLoader().getResource("img/cat.gif"));
        Image image = imageIcon.getImage();

        g.drawImage(image,(this.getWidth()-imageIcon.getIconWidth())/2,
                (this.getHeight()-imageIcon.getIconHeight())/2,this);

        repaint();
    }

}
