package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by song on 16-9-9.
 * 测试滑块
 */
public class SliderTest extends JFrame {
    public static void main(String[] args) {
        new SliderTest();
    }

    private SliderTest() {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }
        catch (Exception e) {
            System.out.println("Look and feel Exception!");
        }

        setLayout(new FlowLayout());

        JSlider slider = new JSlider(0, 100);
//        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
//        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(10);
        slider.setSnapToTicks(true);
        add(slider);

        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("swing.SliderTest.mouseEntered");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("swing.SliderTest.mouseClicked");
            }
        });

        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
