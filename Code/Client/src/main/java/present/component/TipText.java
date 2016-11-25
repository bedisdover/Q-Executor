package present.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/29.
 *
 * 随着用户的输入带着提示功能的文本框
 */
public class TipText extends QTextField {

    /**
     * 字符串匹配器，用于产生与文本框中关键字相匹配的字符串
     */
    @FunctionalInterface
    public interface Matcher {
        Vector<String> getMatchString(String key);
    }

    /**
     * 下拉列表点击事件处理器
     */
    @FunctionalInterface
    public interface ListClickHandler {
        void handle(String text);
    }

    /**
     * 下拉列表项选择发生改变的事件处理器
     */
    public interface ItemChangedHandler {
        void handle(JTextField field, String text);
    }


    /**
     * 默认下拉提示列表点击监听器
     */
    private ListClickHandler clickHandler = (text) -> {};

    /**
     * 默认下拉提示列表焦点获得监听器
     */
    private ItemChangedHandler focusHandler = (field, text) -> {};

    /**
     * 默认字符串匹配器
     */
    private Matcher matcher = (key) -> new Vector<>();

    private JPopupMenu tips = new JPopupMenu();

    private Vector<String> mappedStrings = new Vector<>();

    private static final int PADDING = 20;

    /**
     * 下拉菜单一次显示的条目数量
     */
    private static final int ITEM_NUM = 5;

    /**
     * 当前显示的第一个菜单项字符串在vector中的下标
     */
    private int currentItem = 0;

    private int textW;

    private int textH;

    public TipText(String placeholder, int width, int height) {
        super(placeholder);
        this.textW = width;
        this.textH = height;
        this.setPreferredSize(new Dimension(width, height));
        this.setTextListener();
    }

    public TipText(int width, int height) {
        this("", width, height);
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public void setListClickHandler(ListClickHandler handler) {
        this.clickHandler = (t) -> {
            this.setText(t);
            handler.handle(t);
        };
    }

    public void setListFocusHandler(ItemChangedHandler handler) {
        this.focusHandler = handler;
    }

    public void hideTips() {
        tips.setVisible(false);
    }

    private void setTextListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                boolean isShift = code == KeyEvent.VK_SHIFT;
                boolean isUp = code == KeyEvent.VK_UP;
                boolean isDown = code == KeyEvent.VK_DOWN;
                boolean isLeft = code == KeyEvent.VK_LEFT;
                boolean isRight = code == KeyEvent.VK_RIGHT;
                if(isUp || isDown || isLeft || isRight || isShift) return;

                mappedStrings = matcher.getMatchString(getText());
                showTipList();
                requestFocus();
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

        tips.show(this, 0, textH);
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

        //设置点击事件监听器
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                clickHandler.handle(text);
            }
        });
        //设置选中状态改变监听器
        item.addChangeListener((e) -> focusHandler.handle(TipText.this, text));

        return item;
    }
}
