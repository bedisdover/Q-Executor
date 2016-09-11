package present.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by song on 16-9-10.
 * <p>
 * 整合进度条与label,为进度条添加书签效果
 * 为进度条添加鼠标监听,并通知观察者获取变化
 */
public class MyProgressPanel extends JPanel {
    /**
     * 进度条长度
     */
    private final int TOTAL_LENGTH = 300;

    /**
     * 进度条最大值
     * 设为10000,精度比默认100高
     */
    private final int MAX_VALUE = 10000;

    /**
     * 书签宽度
     */
    private final int MARK_WIDTH = 15;

    /**
     * 书签高度
     */
    private final int MARK_HEIGHT = 18;

    private ProgressListener observer;

    private JProgressBar progressBar;

    public MyProgressPanel(ProgressListener observer) {
        this.observer = observer;

        init();
        createUIComponents();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            setLayout(new GridLayout(2, 1));
            // 10为左右边距之和
            setPreferredSize(new Dimension(TOTAL_LENGTH + 10, 40));
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                JPanel northPanel = new JPanel();
                OverlayLayout overlayLayout = new OverlayLayout(northPanel);
                northPanel.setLayout(overlayLayout);

                JPanel progressPanel = new JPanel(new BorderLayout());
                progressPanel.setBorder(BorderFactory.createMatteBorder(10, 5, 0, 5, new Color(0, 0, 0, 0)));

                progressBar = new JProgressBar(0, MAX_VALUE);
                progressPanel.add(progressBar);

                northPanel.add(progressPanel);

                CoverPanel coverPanel = new CoverPanel();
                // 左右与progressPanel对齐
                coverPanel.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, new Color(0, 0, 0, 0)));
                northPanel.add(coverPanel);

                add(northPanel);
            }

            {
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
            }
        });
    }

    /**
     * 设置进度条位置(占总长度的比例)
     */
    public void setPercent(double percent) {
        progressBar.setValue((int) (percent * MAX_VALUE));
    }

    /**
     * 获得鼠标所在位置占总长度的比例
     * @param mouse_x 鼠标所在位置(以左边为起点)
     */
    private double getMousePercent(int mouse_x) {
        return (double) mouse_x / TOTAL_LENGTH;
    }

    /**
     * 遮罩面板,监听鼠标滑动显示游标
     */
    private class CoverPanel extends JPanel {

        private CoverPanel panel;

        /**
         * 标记鼠标划过的位置
         */
        private JLabel mark;

        CoverPanel() {
            panel = this;
            init();

            addListener();
        }

        private void init() {
            SwingUtilities.invokeLater(() -> {
                panel.setLayout(null);
                panel.setBackground(new Color(0, 0, 0, 0));

                mark = new JLabel(new ImageIcon("src/main/resources/images/bookmarks.png"));
                mark.setBounds(0, 0, MARK_WIDTH, MARK_HEIGHT);

                panel.add(mark);
                panel.repaint();
            });
        }

        private void addListener() {
            SwingUtilities.invokeLater(() -> {
                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }
                });

                panel.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        setMarkLocation(e.getX());
                    }
                });
            });
        }

        /**
         * 设置标记位置
         * @param mouse_x 鼠标所在位置
         */
        private void setMarkLocation(int mouse_x) {
            SwingUtilities.invokeLater(() -> {
                //FIXME 无法刷新
                mark.setBounds(mouse_x, 0, MARK_WIDTH, MARK_HEIGHT);
                mark.setToolTipText(observer.getToolTipText(getMousePercent(mouse_x)));

                observer.valueChanged(getMousePercent(mouse_x));
                panel.repaint();
            });
        }
    }
}
