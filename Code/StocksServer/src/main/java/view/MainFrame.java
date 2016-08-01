package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class MainFrame extends JFrame {
    private JLabel initStocks;
    private JLabel getInstances;

    public MainFrame(int width,int height){

        this.setUndecorated(true);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLayout(null);
        int x = (int)dimension.getWidth();
        int y = (int)dimension.getHeight();
        this.setBounds((x-width)/2,(y-height)/2,width,height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
