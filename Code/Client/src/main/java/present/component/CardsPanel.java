package present.component;

import present.PanelSwitcher;
import present.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by Y481L on 2016/9/9.
 *
 * 卡片面板，以行的形式展现
 */
public class CardsPanel extends JPanel {

    private static final int ROW = 1;

    private static final int COLUMN = 4;

    private static final int PAGE_CARD_NUM = ROW * COLUMN;

    private static final int PADDING = 20;

    private static final int BUTTON_W = 50;

    private static final int BUTTON_H = 200;

    private List<JPanel> cards;

    private int size;

    private JPanel container;

    private JComponent current;

    private Image tip;

    private int page = 0;

    /**
     *
     * @param cards 面板列表
     * @param emptyTip 提示信息
     */
    public CardsPanel(List<JPanel> cards, Image emptyTip) {
        this.cards = cards;
        size = cards.size();
        tip = emptyTip;

        current = size == 0 ? createEmptyPage() :
                (size <= PAGE_CARD_NUM ?
                        createNonePanel() : createNextPanel()
                );
        container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(current, BorderLayout.CENTER);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.add(container);
        this.setOpaque(false);
    }

    /**
     * 创建只有前一页没有后一页的面板
     * pre size > PAGE_CARD_NUM && page > 1
     * @return JPanel
     */
    private JPanel createPrePanel() {
        JPanel pre = createPreButton();
        JPanel center = createMutiLines(cards.subList(
                page * PAGE_CARD_NUM, size
        ));
        JPanel next = createTipButton();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(pre, BorderLayout.WEST);
        panel.add(center, BorderLayout.CENTER);
        panel.add(next, BorderLayout.EAST);
        return panel;
    }

    /**
     * 创建只有后一页没有前一页的面板
     * @return JPanel
     */
    private JPanel createNextPanel() {
        JPanel pre = createTipButton();
        int end = PAGE_CARD_NUM >= size ? size : PAGE_CARD_NUM;
        JPanel center = createMutiLines(cards.subList(0, end));
        JPanel next = createNextButton();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(pre, BorderLayout.WEST);
        panel.add(center, BorderLayout.CENTER);
        panel.add(next, BorderLayout.EAST);
        return panel;
    }

    /**
     * 创建既有前一页又有后一页的面板
     * @return JPanel
     */
    private JPanel createBothPanel() {
        JPanel pre = createPreButton();
        JPanel center = createMutiLines(cards.subList(
                page * PAGE_CARD_NUM, (page + 1) * PAGE_CARD_NUM
        ));
        JPanel next = createNextButton();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(pre, BorderLayout.WEST);
        panel.add(center, BorderLayout.CENTER);
        panel.add(next, BorderLayout.EAST);
        return panel;
    }

    /**
     * 创建既没有前一页又没有后一页的面板
     * @return JPanel
     */
    private JPanel createNonePanel() {
        JPanel pre = createTipButton();
        JPanel center = createMutiLines(cards);
        JPanel next = createTipButton();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(pre, BorderLayout.WEST);
        panel.add(center, BorderLayout.CENTER);
        panel.add(next, BorderLayout.EAST);
        return panel;
    }

    /**
     * 创建前一页按钮，用JPanel模拟按钮效果
     * @return JPanel
     */
    private JPanel createPreButton() {
        return createFlipPanel(ImageLoader.pre_enter, ImageLoader.pre_exit,
                new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseReleased(e);
                        --page;
                        JPanel to = page > 0 ?
                                createBothPanel() : createNextPanel();
                        current = PanelSwitcher.jump(
                                container, current, to
                        );
                    }
        });
    }

    /**
     * 创建后一页按钮，用JPanel模拟按钮效果
     * @return JPanel
     */
    private JPanel createNextButton() {
        return createFlipPanel(ImageLoader.next_enter, ImageLoader.next_exit,
                new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseReleased(e);
                        ++page;
                        JPanel to;
                        if ((page + 1) * PAGE_CARD_NUM < size) {
                            to = createBothPanel();
                        } else {
                            to = createPrePanel();
                        }
                        current = PanelSwitcher.jump(container, current, to);
                    }
        });
    }

    /**
     * 创建翻页面板，当被点击时，实现翻页效果
     * @param adapter 鼠标点击事件监听器
     * @return JPanel
     */
    private JPanel createFlipPanel( Image enter, Image exit, MouseAdapter adapter) {
        return new ButtonPanel(enter, exit, adapter);
    }

    private JPanel createTipButton() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(
                        tip, 0, 0, BUTTON_W, BUTTON_H, null
                );
            }
        };
        panel.setPreferredSize(
                new Dimension(BUTTON_W, BUTTON_H)
        );
        panel.setOpaque(false);
        return panel;
    }

    /**
     * 创建多行卡片信息面板
     * pre cards列表不为空
     * @param cards 卡片信息列表
     * @return 卡片面板
     */
    private JPanel createMutiLines(List<JPanel> cards) {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);

        JPanel first = cards.get(0);
        int size = cards.size();
        for(int i = 0; i < ROW; ++i) {
            int start = i * COLUMN;
            int end = (i + 1) * COLUMN;
            end = end >= size ? size : end;
            if(start >= size) {
                box.add(createEmptyLine(first.getPreferredSize()));
            } else {
                box.add(createLine(cards.subList(start, end), end - start));
            }
        }

        JPanel panel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 0, 0
        ));
        panel.setOpaque(false);
        panel.add(box);
        return panel;
    }

    /**
     * 创建多行空白卡片信息面板
     * pre cards列表不为空
     * @return 卡片面板
     */
    private JPanel createEmptyPage() {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);

        for(int i = 0; i < ROW; ++i) {
            box.add(createEmptyLine(
                    new Dimension(BUTTON_H, BUTTON_H)
            ));
        }

        JPanel panel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 0, 0
        ));
        panel.setOpaque(false);
        panel.add(box);
        return panel;
    }

    /**
     * 创建一行卡片面板
     * pre size <= COLUMN && size > 0
     * @param cards 卡片面板列表
     * @param size 要创建的一行卡片面板中信息卡片的数量
     * @return 卡片面板
     */
    private JPanel createLine(List<JPanel> cards, int size) {
        JPanel panel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, PADDING, PADDING
        ));
        panel.setOpaque(false);

        cards.forEach(panel::add);
        JPanel first = cards.get(0);
        for(int i = 0; i < COLUMN - size; ++i) {
            panel.add(createEmptyCard(first.getPreferredSize()));
        }

        return panel;
    }

    /**
     * 创建空白的行卡片面板
     * @param d 长与宽
     * @return 空白面板
     */
    private JPanel createEmptyLine(Dimension d) {
        JPanel panel = new JPanel();
        int w = d.width, h = d.height;
        panel.setPreferredSize(new Dimension(
                COLUMN * w + (COLUMN + 1) * PADDING,
                h + (PADDING << 1)
        ));
        panel.setOpaque(false);
        return panel;
    }

    /**
     * 创建一张空白卡片
     * @param d 长和宽
     * @return 空白卡片
     */
    private JPanel createEmptyCard(Dimension d) {
        JPanel card = new JPanel();
        card.setPreferredSize(d);
        card.setOpaque(false);
        return card;
    }

    private class ButtonPanel extends JPanel {
        private Image img;

        ButtonPanel(Image enter, Image exit, MouseAdapter adapter) {
            img = exit;
            this.setOpaque(false);
            this.setPreferredSize(new Dimension(
                    BUTTON_W, BUTTON_H
            ));

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    adapter.mouseReleased(e);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    setImage(exit);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    setImage(enter);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(
                    img, 0, 0, BUTTON_W, BUTTON_H, null
            );
        }

        void setImage(Image img) {
            this.img = img;
            this.repaint();
        }
    }

}
