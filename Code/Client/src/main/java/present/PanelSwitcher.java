package present;

import present.panel.trade.TradePanel;

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

    /**
     * 交易面板
     */
    private TradePanel tradePanel = new TradePanel();

    private PanelSwitcher(JPanel container, JPanel current) {
        this.container = container;
        this.current = current;
    }

    PanelSwitcher(JPanel container) {
        this(container, null);
    }

    public void jump(JPanel to) {
        if(current.getClass() == to.getClass()) return;

        container.remove(current);
        current.setVisible(false);
        if(to instanceof TradePanel) {
            container.add(tradePanel);
            current = tradePanel;
            tradePanel.setVisible(true);
        }else {
            container.add(to);
            current = to;
            to.setVisible(true);
        }

        container.revalidate();
    }

    void setCurrent(JPanel current) {
        this.current = current;
    }
}
