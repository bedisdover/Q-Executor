package present.panel.stock.center;

import present.panel.loading.LoadingPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-7.
 * <p>
 * 中部面板
 */
public abstract class CenterPanel extends JComponent {

    protected void init() {
        SwingUtilities.invokeLater(() -> {
            this.setLayout(new BorderLayout());

            this.add(new LoadingPanel(), BorderLayout.CENTER);
        });
    }

    /**
     * 获取数据
     */
    public void getData() {

    }
}
