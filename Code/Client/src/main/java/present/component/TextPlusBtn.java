package present.component;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/28.
 * <p>
 * 组合组件，包含文本框框和按钮
 */
public class TextPlusBtn extends JPanel {

    private QTextField text;

    private JButton search;

    private Vector<String> mappedStrings = new Vector<>();

    private JPopupMenu tips = new JPopupMenu();

    /**
     * 默认字符串匹配器
     */
    private Matcher matcher = (key) -> new Vector<>();

    private ListHandler handler = (text) -> {};

    private int textW;

    private int textH;

    private static final int PADDING = 20;

    /**
     * 下拉菜单一次显示的条目数量
     */
    private static final int ITEM_NUM = 5;

    /**
     * 当前显示的第一个菜单项字符串在vector中的下标
     */
    private int currentItem = 0;

    /**
     * 字符串匹配器，用于产生与文本框中关键字相匹配的字符串
     */
    @FunctionalInterface
    public interface Matcher {
        Vector<String> getMatchString(String key);
    }

    /**
     * 下拉列表事件处理器
     */
    @FunctionalInterface
    public interface ListHandler {
        void handleItemClicked(String text);
    }

    public TextPlusBtn(String placeholder, int width, int height) {
        //搜索框长度：按钮长度 = 4 : 1
        textW = (int) (width * 0.8);
        textH = height;
        text = new QTextField(placeholder);
        text.setPreferredSize(new Dimension(textW, height));
        this.setTextListener();

        search = new JButton("搜索");
        search.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        int searchW = width - textW;
        search.setPreferredSize(new Dimension(searchW, height));

        this.add(text);
        this.add(search);
    }

    public void setBtnListener(ActionListener listener) {
        this.search.addActionListener(listener);
        AbstractAction action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tips.setVisible(false);
                listener.actionPerformed(e);
            }
        };
        text.getInputMap().put(KeyStroke.getKeyStroke('\n'), "search");
        text.getActionMap().put("search", action);
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public void setListHandler(ListHandler handler) {
        this.handler = (t) -> {
            text.setText(t);
            handler.handleItemClicked(t);
        };
    }

    public String getText() {
        return text.getText();
    }

    private void setTextListener() {
        this.text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                boolean isShift = code == KeyEvent.VK_SHIFT;
                boolean isUp = code == KeyEvent.VK_UP;
                boolean isDown = code == KeyEvent.VK_DOWN;
                boolean isLeft = code == KeyEvent.VK_LEFT;
                boolean isRight = code == KeyEvent.VK_RIGHT;
                if(isUp || isDown || isLeft || isRight || isShift) return;

                mappedStrings = matcher.getMatchString(text.getText());
                showTipList();
                text.requestFocus();
            }
        });
    }

    private void showTipList() {
        if(mappedStrings == null) return;

        //创建下拉提示菜单
        tips = new JPopupMenu();
        //按钮面板
        JPanel p = new JPanel(new FlowLayout(
                FlowLayout.CENTER, PADDING, PADDING >> 1
        ));
        //添加菜单项
        int num = mappedStrings.size();
        if (currentItem + ITEM_NUM >= num && currentItem > 0) { //到达菜单项尾页
            tips.setPreferredSize(new Dimension(textW, textH * (num - currentItem + 1)));
            for(int i = currentItem; i < num; ++i) {
                tips.add(createItem(mappedStrings.get(i)));
            }
            p.add(createJumpBtn("上一页", getPreListener()));
            tips.add(p);
        } else if(currentItem > 0){ //菜单中间
            tips.setPreferredSize(new Dimension(textW, textH * (ITEM_NUM + 1)));
            for(int i = currentItem; i < currentItem + ITEM_NUM; ++i) {
                tips.add(createItem(mappedStrings.get(i)));
            }
            p.add(createJumpBtn("上一页", getPreListener()));
            p.add(createJumpBtn("下一页", getNextListener()));
            tips.add(p);
        } else {    //菜单首页
            if (currentItem + ITEM_NUM >= num) {
                tips.setPreferredSize(new Dimension(textW, textH * (num - currentItem)));
                for(int i = currentItem; i < num; ++i) {
                    tips.add(createItem(mappedStrings.get(i)));
                }
            }else {
                tips.setPreferredSize(new Dimension(textW, textH * (ITEM_NUM + 1)));
                for(int i = currentItem; i < currentItem + ITEM_NUM; ++i) {
                    tips.add(createItem(mappedStrings.get(i)));
                }
                p.add(createJumpBtn("下一页", getNextListener()));
                tips.add(p);
            }
        }

        tips.show(text, 0, textH);
    }

    private ActionListener getPreListener() {
        return (e) -> {
            currentItem -= ITEM_NUM;
            showTipList();
        };
    }

    private ActionListener getNextListener() {
        return (e) -> {
            currentItem += ITEM_NUM;
            showTipList();
        };
    }

    private JButton createJumpBtn(String text, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setFocusable(false);
        btn.addActionListener(listener);
        return btn;
    }

    private JMenuItem createItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setPreferredSize(new Dimension(textW, textH));
        item.addActionListener((e) -> handler.handleItemClicked(text));
        return item;
    }

}
