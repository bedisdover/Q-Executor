package present.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-2.
 * <p>
 * 圆形进度条。
 */
public class CircleProgressBar extends JPanel {

    /**
     * 最小进度值。
     */
    private int minimumProgress;

    /**
     * 最大进度值。
     */
    private int maximumProgress;

    /**
     * 当前进度值。
     */
    private int progress;

    /**
     * 背景颜色。
     */
    private Color backgroundColor;

    /**
     * 前景颜色。
     */
    private Color foregroundColor;

    /**
     * 数字颜色。
     */
    private Color digitalColor;

    /**
     * 创建一个圆形进度条对象。
     */
    public CircleProgressBar() {
        this(0);
    }

    /**
     * 创建一个具有指定初始值的进度条
     *
     * @param progress 初始值
     */
    public CircleProgressBar(int progress) {
        this(0, 100);

        setProgress(progress);
    }

    /**
     * 创建一个具有最小值和最大值的进度条
     *
     * @param minimumProgress 最小进度值
     * @param maximumProgress 最大进度值
     */
    public CircleProgressBar(int minimumProgress, int maximumProgress) {
        this(minimumProgress, maximumProgress, 0, Color.LIGHT_GRAY, Color.ORANGE, Color.BLACK);
    }

    public CircleProgressBar(Color backgroundColor, Color foregroundColor, Color digitalColor) {
        this(0, 100, 0, backgroundColor, foregroundColor, digitalColor);
    }

    public CircleProgressBar(int minimumProgress, int maximumProgress, int progress,
                             Color backgroundColor, Color foregroundColor, Color digitalColor) {
        setMinimumProgress(minimumProgress);
        setMaximumProgress(maximumProgress);
        setBackgroundColor(backgroundColor);
        setForegroundColor(foregroundColor);
        setDigitalColor(digitalColor);
        setProgress(progress);

        setPreferredSize(new Dimension(200, 200));
    }

    /**
     * 绘制圆形进度条。
     *
     * @param g 画笔。
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2d = (Graphics2D) g;
        // 开启抗锯齿
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x, y, width, height;
        int fontSize;

        if (getWidth() >= getHeight()) {
            x = (getWidth() - getHeight()) / 2 + 25;
            y = 25;
            width = getHeight() - 50;
            height = getHeight() - 50;
            fontSize = getWidth() / 8;
        } else {
            x = 25;
            y = (getHeight() - getWidth()) / 2 + 25;
            width = getWidth() - 50;
            height = getWidth() - 50;
            fontSize = getHeight() / 8;
        }
        graphics2d.setStroke(new BasicStroke(20.0f));
        graphics2d.setColor(backgroundColor);
        graphics2d.drawArc(x, y, width, height, 0, 360);
        graphics2d.setColor(foregroundColor);
        graphics2d.drawArc(x, y, width, height, 90, -(int) (360 * ((progress * 1.0) / (getMaximumProgress() - getMinimumProgress()))));
        graphics2d.setFont(new Font("黑体", Font.BOLD, fontSize));
        FontMetrics fontMetrics = graphics2d.getFontMetrics();
        int digitalWidth = fontMetrics.stringWidth(progress + "%");
        int digitalAscent = fontMetrics.getAscent();
        graphics2d.setColor(digitalColor);
        graphics2d.drawString(progress + "%", getWidth() / 2 - digitalWidth / 2, getHeight() / 2 + digitalAscent / 2);
    }

    /**
     * 返回最小进度值。
     *
     * @return 最小进度值。
     */
    public int getMinimumProgress() {
        return minimumProgress;
    }

    /**
     * 设置最小进度值。
     *
     * @param minimumProgress 最小进度值。
     */
    public void setMinimumProgress(int minimumProgress) {
        if (minimumProgress <= getMaximumProgress()) {
            this.minimumProgress = minimumProgress;
        }
    }

    /**
     * 返回最大进度值。
     *
     * @return 最大进度值。
     */
    public int getMaximumProgress() {
        return maximumProgress;
    }

    /**
     * 设置最大进度值。
     *
     * @param maximumProgress 最大进度值。
     */
    public void setMaximumProgress(int maximumProgress) {
        if (maximumProgress >= getMinimumProgress()) {
            this.maximumProgress = maximumProgress;
        }
    }

    /**
     * 返回当前进度值。
     *
     * @return 当前进度值。
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 设置当前进度值。
     *
     * @param progress 当前进度值。
     */
    public void setProgress(int progress) {
        if (progress >= getMinimumProgress() && progress <= getMaximumProgress()) {
            this.progress = progress;
            this.repaint();
        }
    }

    /**
     * 设置渐变进度
     *
     * @param progress 当前进度值
     */
    public void setGradualProcess(int progress) {
        if (progress >= getMinimumProgress() && progress <= getMaximumProgress()) {
            Color temp;
            if (progress <= 50) {
                temp = new Color(255, 5 * progress, 0);
            } else {
                temp = new Color(255 - (progress - 50) * 5, 255, 0);
            }

            setForegroundColor(temp);
            setDigitalColor(temp);

            setProgress(progress);
        }
    }

    /**
     * 返回背景颜色。
     *
     * @return 背景颜色。
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * 设置背景颜色。
     *
     * @param backgroundColor 背景颜色。
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.repaint();
    }

    /**
     * 返回前景颜色。
     *
     * @return 前景颜色。
     */
    public Color getForegroundColor() {
        return foregroundColor;
    }

    /**
     * 设置前景颜色。
     *
     * @param foregroundColor 前景颜色。
     */
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
        this.repaint();
    }

    /**
     * 返回数字颜色。
     *
     * @return 数字颜色。
     */
    public Color getDigitalColor() {
        return digitalColor;
    }

    /**
     * 设置数字颜色。
     *
     * @param digitalColor 数字颜色。
     */
    public void setDigitalColor(Color digitalColor) {
        this.digitalColor = digitalColor;
        this.repaint();
    }

}
