package view;

import data.GetInstance;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class MainFrame extends JFrame {
    private InitStocksLabel initStocks;
    private GetInstanceLabel getInstances;

    public MainFrame(int width,int height){

//        this.setUndecorated(true);
        initStocks = new InitStocksLabel(this);
        getInstances = new GetInstanceLabel(this);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)dimension.getWidth();
        int y = (int)dimension.getHeight();
        this.setContentPane(new LoadingPanel());
        this.setBounds((x-width)/2,(y-height)/2,width,height);

        this.getContentPane().setLayout(null);
        this.getContentPane().add(initStocks);
        initStocks.setLocation(0,(this.getHeight()-initStocks.getHeight())/2);
        this.getContentPane().add(getInstances);
        getInstances.setLocation(0,(this.getHeight()-getInstances.getHeight())/2+100);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
