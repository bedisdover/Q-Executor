package present.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Y481L on 2016/8/31.
 *
 * 超链接组件
 */
public class Link extends JLabel {

    /**
     * 默认点击事件处理器
     */
    private ClickHandler handler = () -> {};

    /**
     * 点击事件处理器
     */
    @FunctionalInterface
    public interface ClickHandler {
        void handle();
    }

    public Link() {
        this("");
    }

    public Link(String text) {
        this.setText(text);
        this.setForeground(Color.cyan);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setText("<html><u>" + text + "</u></html>");
                setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setText(text);
                setForeground(Color.CYAN);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                handler.handle();
            }
        });
    }

    public void setHandler(ClickHandler handler) {
        this.handler = handler;
    }
}
