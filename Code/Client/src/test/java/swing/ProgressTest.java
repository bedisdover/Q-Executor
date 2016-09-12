package swing;

import present.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by song on 16-9-11.
 */
public class ProgressTest extends JFrame {
    public static void main(String[] args) {
        new ProgressTest();
    }

    private ProgressTest() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(ImageLoader.baseBar, 0, 0, 340, 20, null);
                g.drawImage(ImageLoader.progressBar, 5, 3, 167, 14, null);
            }
        };
        panel.setLayout(null);

        JLabel label = new JLabel(new ImageIcon("src/main/resources/images/bookmarks.png"));
        label.setBounds(0, 0, 30, 15);
        panel.add(label);

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                label.setBounds(e.getX(), 0, 30, 15);

                panel.repaint();
            }
        });

        add(panel);

        setSize(new Dimension(400, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
