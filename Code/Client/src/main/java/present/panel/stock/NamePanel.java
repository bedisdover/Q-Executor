package present.panel.stock;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-8-26.
 * <p>
 * 名称面板
 */
class NamePanel extends JPanel {

    private JPanel panel;

    private JLabel name, code;

    private JButton portrait;

    NamePanel() {
        panel = this;

        init();
        createUIComponents();
    }

    /**
     * 初始化
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(new Color(0xeeeeee));
            panel.setPreferredSize(new Dimension(1, 50));
            panel.setLayout(new BorderLayout());

            panel.revalidate();
        });
    }

    /**
     * 初始化组件
     */
    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                JPanel westPanel = new JPanel();

                westPanel.setBackground(new Color(0xeeeeee));
                westPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));

                name = new JLabel("浦发银行");
                name.setFont(new Font("微软雅黑", Font.PLAIN, 20));

                code = new JLabel("(sh600000)");
                code.setFont(new Font("微软雅黑", Font.PLAIN, 16));

                westPanel.add(name);
                westPanel.add(code);

                panel.add(westPanel, BorderLayout.WEST);
            }
            {
                JPanel eastPanel = new JPanel();

                eastPanel.setBackground(new Color(0xeeeeee));
                eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

                portrait = new JButton("添加自选");
                portrait.setPreferredSize(new Dimension(80, 30));
                portrait.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));

                eastPanel.add(portrait);

                panel.add(eastPanel, BorderLayout.EAST);

                panel.revalidate();
            }
        });
    }
}
