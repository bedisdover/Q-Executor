package present;

import present.panel.home.TitlePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 软件主窗体
 */
public class MainFrame extends JFrame{

    private static final int PANEL_W = 900;

    private static final int PANEL_H = 600;

    public MainFrame() {

        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }
        catch (Exception e) {
            System.out.println("Look and feel Exception!");
        }

        this.addComponents();
        this.setAttributes();
    }

    private void setAttributes() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setFrameAtCenter();
    }

    private void addComponents() {
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        container.setLayout(new BorderLayout());
        JPanel current = new JPanel();
        container.add(current);
        PanelSwitcher switcher = new PanelSwitcher(container, current);
        TitlePanel title = new TitlePanel(switcher);
//        NavPanel nav = new NavPanel(switcher);
        this.add(title, BorderLayout.NORTH);
//        this.add(nav, BorderLayout.WEST);
        this.add(container, BorderLayout.CENTER);
    }

    private void setFrameAtCenter() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        Dimension frame = this.getPreferredSize();
        this.setLocation((screen.width - frame.width) >> 1,
                (screen.height - frame.height) >> 1);
    }
}
