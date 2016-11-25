package present.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Y481L on 2016/8/28.
 * <p>
 * 组合组件，包含文本框框和按钮
 */
public class TextPlusBtn extends JPanel {

    private TipText text;

    private JButton search;

    public TextPlusBtn(String placeholder, int width, int height) {
        //搜索框长度：按钮长度 = 4 : 1
        int textW = (int) (width * 0.8);
        text = new TipText(placeholder, textW, height);
        text.setPreferredSize(new Dimension(textW, height));

        search = new JButton("搜索");
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
                text.hideTips();
                listener.actionPerformed(e);
            }
        };
        text.getInputMap().put(KeyStroke.getKeyStroke('\n'), "search");
        text.getActionMap().put("search", action);
    }

    public void setMatcher(TipText.Matcher matcher) {
        text.setMatcher(matcher);
    }

    public void setListClickHandler(TipText.ListClickHandler handler) {
        text.setListClickHandler(handler);
    }

    public void setListFocusHandler(TipText.ItemChangedHandler handler) {
        text.setListFocusHandler(handler);
    }

    public String getText() {
        return text.getText();
    }

    public TipText getTextField() {
        return this.text;
    }

    public JButton getButton() {
        return this.search;
    }

}
