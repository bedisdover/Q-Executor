package present.panel.introduce;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import present.MainFrame;
import present.PanelSwitcher;
import present.component.TextBlock;
import present.panel.trade.TradePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Y481L on 2016/8/29.
 *
 * 简介面板中的图像面板
 */
class ImagePanel extends JPanel {

    private static final int PADDING = MainFrame.PANEL_H >> 3;

    private static final int ICON_HEIGHT = 270;

    private static final int ICON_WIDTH = 200;

    private static final int BUTTON_W = 150;

    private static final int BUTTON_H = 48;

    ImagePanel(PanelSwitcher switcher) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);

        //logo
        box.add(Box.createVerticalStrut(PADDING >> 1));
        JPanel icon = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/main/resources/images/logo.png").getImage();
                int x = (MainFrame.PANEL_W - ICON_WIDTH) >> 1;
                int y = PADDING >> 1;
                g.drawImage(
                        img,
                        x, y, x + ICON_WIDTH, y + ICON_HEIGHT,
                        0, 0, img.getWidth(null), img.getHeight(null) + PADDING,
                        null
                );
            }
        };
        icon.setPreferredSize(new Dimension(MainFrame.PANEL_W, ICON_HEIGHT));
        icon.setOpaque(false);
        box.add(icon);

        //文本简介
        box.add(Box.createVerticalStrut(PADDING >> 1));
        TextBlock block = new TextBlock(
                3,
                "A股订单优化执行系统由XXX公司（我们公司名）独立自主研发，支持沪深两市A股市场在线交易，可以帮助用户合理安排大额股票交易。系统集自身之独创与众家之所长为一身，软件小巧，功能强大，技术分析专业、精妙，是投资者纵横股市的必备工具",
                new Font("宋体", Font.PLAIN, 20), Color.WHITE
        );
        block.setPreferredSize(new Dimension(MainFrame.PANEL_W, PADDING << 1));
        JPanel text = new JPanel(new BorderLayout());
        text.add(block, BorderLayout.NORTH);
        box.add(block);

        //按钮
        box.add(Box.createVerticalStrut(PADDING >> 2));

        JButton start = createBtn("开始体验", (e) -> switcher.jump(new TradePanel()));
        start.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        JPanel btnPane = new JPanel(new FlowLayout(FlowLayout.CENTER, BUTTON_H, 0));
        btnPane.setOpaque(false);
        btnPane.add(start);
        box.add(btnPane);

        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.NORTH);
        this.setPreferredSize(new Dimension(MainFrame.PANEL_W, MainFrame.PANEL_H));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                new ImageIcon("src/main/resources/images/city4.jpg").getImage(),
                0, 0, MainFrame.PANEL_W, MainFrame.PANEL_H, null
        );
    }

    private JButton createBtn(String text, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        btn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        btn.setFont(new Font("宋体", Font.PLAIN, 20));
        btn.addActionListener(listener);
        return btn;
    }
}