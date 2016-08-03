package view;

import data.InitAllStocks;
import data.InstanceRunnable;
import util.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by 王栋 on 2016/8/1 0001.
 */
public class GetInstanceLabel extends JLabel implements MouseListener {

    private Component parent ;
     public GetInstanceLabel(Component parent){
         this.parent = parent;
         this.addMouseListener(this);
         ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/getInstance.png"));
         this.setIcon(imageIcon);
         this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());

     }

    public void mouseClicked(MouseEvent e) {
        //实现初始化
        Connection connection= ConnectionFactory.getInstance().makeConnection();
        ArrayList<Thread> list = new ArrayList<Thread>();
        InitAllStocks.init(connection);
        java.util.List<String> codes = InitAllStocks.getStockCodes(connection);
        int numbers = codes.size();
        int counts = numbers/200+1;
        JOptionPane.showMessageDialog(parent,"开始不断往云端更新实时数据","提示信息",JOptionPane.INFORMATION_MESSAGE);

        for (int i = 0; i < counts; i++) {
            ArrayList<String> tempList = new ArrayList<String>();
            if (i == counts - 1) {
                for (int j = i * 200; j < codes.size(); j++) {
                    tempList.add(codes.get(j));
                }

            } else {
                for (int j = i * 200; j < (i + 1) * 200; j++) {
                    tempList.add(codes.get(j));
                }
            }
            list.add(new Thread(new InstanceRunnable(tempList.iterator())));
        }

//        System.err.println("once more");
        for (Thread thread : list) {

            thread.start();
        }

        //关闭连接
        try {
            ConnectionFactory.getInstance().close(connection,null);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
//        System.err.print("我走到这里了！！！");
    }

    public void mousePressed(MouseEvent e) {
        ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/getInstance_press.png"));
        this.setIcon(imageIcon);
        this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }

    public void mouseReleased(MouseEvent e) {
        ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/getInstance.png"));
        this.setIcon(imageIcon);
        this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }

    public void mouseEntered(MouseEvent e) {
        ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/getInstance_enter.png"));
        this.setIcon(imageIcon);
        this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }

    public void mouseExited(MouseEvent e) {
        ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/getInstance.png"));
        this.setIcon(imageIcon);
        this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }
}
