package swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-10.
 *
 * 测试为进度条添加刻度
 */
public class ProgressBarTest extends JFrame {
    public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }
        catch (Exception e) {
            System.out.println("Look and feel Exception!");
        }

        new ProgressBarTest();
    }

    private ProgressBarTest() {
        setLayout(new GridLayout(2, 1));

        JPanel progressPanel = new JPanel(new BorderLayout());
        JProgressBar progressBar = new JProgressBar();
        progressBar.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, new Color(0, 0, 0, 0)));
        progressBar.setValue(50);
        progressPanel.add(progressBar);
        add(progressPanel);

        JPanel labelPanel = new JPanel(new GridLayout(1, 2));

        Box westBox = Box.createHorizontalBox();
        westBox.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, new Color(0, 0, 0, 0)));
        westBox.add(new JLabel("9:30"));
        westBox.add(Box.createGlue());
        westBox.add(new JLabel("11:30"));

        Box eastBox = Box.createHorizontalBox();
        eastBox.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, new Color(0, 0, 0, 0)));
        eastBox.add(new JLabel("13:00"));
        eastBox.add(Box.createGlue());
        eastBox.add(new JLabel("15:00"));


        labelPanel.add(westBox);
        labelPanel.add(eastBox);

        add(labelPanel);

        setSize(new Dimension(400, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
