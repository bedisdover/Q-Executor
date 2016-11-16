package present.panel.loading;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Y481L on 2016/9/10.
 * <p>
 * 计算交易策略的进度面板
 */
public class CalculatingPanel extends JPanel {

    private static final ImageIcon icon =
            new ImageIcon("src/main/resources/images/loading.gif");

    private JProgressBar bar;

    public CalculatingPanel(int width, int height) {
        JLabel loading = new JLabel(icon);
        loading.setPreferredSize(new Dimension(width, height >> 2));
        JPanel loadingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        loadingPanel.setOpaque(false);
        loadingPanel.add(loading);

        bar = new JProgressBar(0, 100);
        bar.setValue(0);
        bar.setStringPainted(true);
        bar.setPreferredSize(new Dimension(500, 30));
        JPanel barPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        barPanel.setOpaque(false);
        barPanel.add(bar);

        Box middle = Box.createVerticalBox();
        middle.setOpaque(false);
        middle.add(loadingPanel);
        middle.add(Box.createVerticalStrut(20));
        middle.add(barPanel);
        middle.add(Box.createVerticalGlue());

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0xecf0f1));
        JPanel up = new JPanel();
        up.setOpaque(false);
        up.setPreferredSize(new Dimension(width, height >> 2));
        this.add(up, BorderLayout.NORTH);
        JPanel down = new JPanel();
        down.setOpaque(false);
        down.setPreferredSize(new Dimension(width, height >> 2));
        this.add(down, BorderLayout.SOUTH);
        this.add(middle, BorderLayout.CENTER);
    }

    /**
     * 设置进程完成的百分比
     * @param percent 百分比
     */
    public void setProcess(int percent) {
        //让进度卡在99%
        if(percent >= 100) return;

        bar.setValue(percent);
        bar.setString(percent + "%");
    }
}
