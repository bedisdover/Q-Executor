package view;

import data.GetInstance;
import util.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class MainFrame extends JFrame implements WindowListener{
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

        this.addWindowListener(this);

    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {
        System.out.println("这里是closing");
        try {
            ConnectionFactory.close();
            System.out.println("关闭了所有的connection");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public void windowClosed(WindowEvent e) {

    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {

    }
}
