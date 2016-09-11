package present.component;

import present.PanelSwitcher;

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

    private JPanel container;

    private JComponent current;

    private int page = 0;

    CardsPanel(List<JPanel> cards) {
        this.cards = cards;
        int size = cards.size();
        int pageNum = 0;

        if(size % PAGE_CARD_NUM == 0) {
            pageNum = size / PAGE_CARD_NUM;
            if(pageNum <= 1) {

            } else {

            }
        } else {
            pageNum = (size / PAGE_CARD_NUM) + 1;
            if (pageNum <= 1) {

            } else {

            }
        }
    }

    /**
     * 创建只有前一页没有后一页的面板
     * @pre size > PAGE_CARD_NUM && page > 1
     * @param cards 卡片面板列表
     * @param size 卡片面板数量
     * @return JPanel
     */
    private JPanel createPrePanel(List<JPanel> cards, int size) {
        JPanel pre = createFlipPanel(Color.BLACK, Color.WHITE, new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                --page;
                JPanel to = page > 0 ?
                        createBothPanel(cards.subList(
                                page * PAGE_CARD_NUM, (page + 1) * PAGE_CARD_NUM
                        ), PAGE_CARD_NUM) :
                        createNextPanel(cards, size);
                current = PanelSwitcher.jump(
                        container, current, to
                );
            }
        });

//        JPanel center = cream
        return null;
    }

    /**
     * 创建只有后一页没有前一页的面板
     * @param cards 卡片面板列表
     * @param size 卡片面板数量
     * @return JPanel
     */
    private JPanel createNextPanel(List<JPanel> cards, int size) {
        return null;
    }

    /**
     * 创建既有前一页又有后一页的面板
     * @param cards 卡片面板列表
     * @param size 卡片面板数量
     * @return JPanel
     */
    private JPanel createBothPanel(List<JPanel> cards, int size) {
        return null;
    }

    /**
     * 创建翻页面板，当被点击时，实现翻页效果
     * @param enter 鼠标移进来时的效果
     * @param exit  鼠标移出去时的效果
     * @param adapter 鼠标点击事件监听器
     *                //TODO 将颜色换成图片
     * @return
     */
    private JPanel createFlipPanel
            (Color enter, Color exit, MouseAdapter adapter) {
        JPanel panel = new JPanel();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                adapter.mouseReleased(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                panel.setBackground(exit);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                panel.setBackground(enter);
            }
        });
        return panel;
    }

    /**
     * 创建多行卡片信息面板
     * @pre cards列表不为空
     * @param cards 卡片信息列表
     * @param size  卡片信息列表大小
     * @return 卡片面板
     */
    private JPanel createMutiLines(List<JPanel> cards, int size) {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);

        JPanel first = cards.get(0);

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
        return panel;
    }

    /**
     * 创建一行卡片面板
     * @pre size <= COLUMN && size > 0
     * @param cards 卡片面板列表
     * @param size 卡片面板数量
     * @return 卡片面板
     */
    private JPanel createLine(List<JPanel> cards, int size) {
        JPanel panel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, PADDING, PADDING
        ));
        panel.setOpaque(false);

        cards.forEach((p) -> panel.add(p));
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
                ROW * h + (ROW + 1) * PADDING
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

}
