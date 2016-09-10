package present.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by song on 16-9-10.
 * <p>
 * 整合进度条与label,为进度条添加刻度效果
 * 为进度条添加鼠标监听,并通知观察者获取变化
 */
public class MyProgressPanel extends JPanel {
    /**
     * 进度条长度
     */
    private final int TOTAL_LENGTH = 300;

    private JComponent observer;

    private JProgressBar progressBar;

    public MyProgressPanel(JComponent observer) {
        this.observer = observer;

        init();
        createUIComponents();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            setLayout(new GridLayout(2, 1));
            // 10为左右边距之和
            setPreferredSize(new Dimension(TOTAL_LENGTH + 10, 30));
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                JPanel northPanel = new JPanel();
                OverlayLayout overlayLayout = new OverlayLayout(northPanel);
                northPanel.setLayout(overlayLayout);

                JPanel progressPanel = new JPanel(new BorderLayout());

                progressBar = new JProgressBar();
                progressBar.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, new Color(0, 0, 0, 0)));
                progressPanel.add(progressBar);

                northPanel.add(progressPanel);
                northPanel.add(new CoverPanel());

                add(northPanel);
            }

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
        });
    }

    /**
     * 获取鼠标点击位置所在的值
     *
     * @param mouse_x 鼠标点击横坐标
     * @return 点击位置所在的值
     */
    private int getValueClicked(int mouse_x) {
        return (mouse_x - 5) / (TOTAL_LENGTH / 100);
    }

    public void setValue(int value) {
        progressBar.setValue(value);
    }

    public int getValue() {
        return progressBar.getValue();
    }

    /**
     * 遮罩面板,监听鼠标滑动显示游标
     */
    private class CoverPanel extends JPanel {
        CoverPanel() {
            init();

            addListener();
        }

        private void init() {
            SwingUtilities.invokeLater(() -> {
                setBackground(new Color(0, 0, 0, 0));
            });
        }

        private void addListener() {
            SwingUtilities.invokeLater(() -> {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println(e.getX());
                    }
                });

                addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        System.out.println(e.getX());
                    }
                });
            });
        }
    }
}


