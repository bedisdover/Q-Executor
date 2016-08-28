package present;

import javax.swing.*;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 界面跳转控制器
 */
public class PanelSwitcher {

    /**
     * 待跳转面板的默认容器
     */
    private JPanel container;

    /**
     * 当前显示面板
     */
    private JPanel current;

    PanelSwitcher(JPanel container, JPanel current) {
        this.container = container;
        this.current = current;
    }

    public void jump(JPanel to) {
        if(current.getClass() == to.getClass()) return;

        container.remove(current);
        container.add(to);
        current = to;
        container.revalidate();
    }
}
