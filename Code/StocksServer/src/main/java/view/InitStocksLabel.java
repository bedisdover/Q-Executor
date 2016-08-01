package view;

import data.InitAllStocks;
import org.omg.CORBA.PUBLIC_MEMBER;
import util.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;

/**
 * Created by 王栋 on 2016/8/1 0001.
 */
public class InitStocksLabel extends JLabel implements MouseListener{
    private Component parent;
    public InitStocksLabel(Component parent){
        this.parent = parent;
        this.addMouseListener(this);
        ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/initStocks.png"));
        this.setIcon(imageIcon);
        this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }

    public void mouseClicked(MouseEvent e) {
        //实现初始化
        Connection connection = ConnectionFactory.getInstance().makeConnection();
        boolean isExist = InitAllStocks.init(connection);
        if(isExist){
            JOptionPane.showMessageDialog(parent,"您已经初始化过所有股票列表的信息了","提示信息",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(parent,"恭喜你完成初始化所有股票列表信息","提示信息",JOptionPane.INFORMATION_MESSAGE);

        }
    }

    public void mousePressed(MouseEvent e) {
        ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/initStocks_press.png"));
        this.setIcon(imageIcon);
        this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }

    public void mouseReleased(MouseEvent e) {
        ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/initStocks.png"));
        this.setIcon(imageIcon);
        this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }

    public void mouseEntered(MouseEvent e) {
        ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/initStocks_enter.png"));
        this.setIcon(imageIcon);
        this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }

    public void mouseExited(MouseEvent e) {
        ImageIcon imageIcon = new ImageIcon(InitStocksLabel.class.getClassLoader().getResource("img/initStocks.png"));
        this.setIcon(imageIcon);
        this.setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    }
}
