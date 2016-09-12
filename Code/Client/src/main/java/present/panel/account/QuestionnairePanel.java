package present.panel.account;

import present.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Y481L on 2016/9/10.
 *
 * 问卷面板
 */
public class QuestionnairePanel extends JScrollPane {

    private static final int PADDING = 40;

    private static final int QUESTION_W = 500;

    private static final int QUESTION_H = 36;

    private static final int ANSWER_W = 370;

    private static final int ANSWER_H = QUESTION_H;

    /**
     * 风险偏好值
     */
    public static double risk = 0.4;

    /**
     * 问卷得分与风险偏好值映射表
     */
    private static Map<Integer, Double> table = new HashMap<>();

    static {
        for(int i = 8; i <=11; ++i) table.put(i, 0.2);

        for(int i = 12; i <= 15; ++i) table.put(i, 0.3);

        for(int i = 16; i <= 22; ++i) table.put(i, 0.4);

        for(int i = 23; i <= 26; ++i) table.put(i, 0.5);

        for (int i = 27; i <= 30; ++i) table.put(i, 0.6);
    }

    QuestionnairePanel() {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);

        box.add(Box.createVerticalStrut(PADDING));

        //问题1
        JComboBox<String> option1 = new JComboBox<>();
        option1.addItem("避免亏损");
        option1.addItem("谨慎增值");
        option1.addItem("稳健增值");
        option1.addItem("显著增长");
        JPanel question1 = createQuestion(option1, ImageLoader.question1);
        box.add(question1);

        box.add(Box.createVerticalStrut(PADDING));

        //问题2
        JComboBox<String> option2 = new JComboBox<>();
        option2.addItem("不足一年");
        option2.addItem("1到5年");
        option2.addItem("5到10年");
        option2.addItem("10年以上");
        JPanel question2 = createQuestion(option2, ImageLoader.question2);
        box.add(question2);

        box.add(Box.createVerticalStrut(PADDING));

        //问题3
        JComboBox<String> option3 = new JComboBox<>();
        option3.addItem("现金流短期压力很大，有可能需要随时将投资变现弥补现金流");
        option3.addItem("现金流短期有一定压力，需要流动性较高的投资");
        option3.addItem("现金流长期有一定压力，需要一定的投资收益弥补现金流");
        option3.addItem("现金流长期充裕，几乎没有压力");
        JPanel question3 = createQuestion(option3, ImageLoader.question3);
        box.add(question3);

        box.add(Box.createVerticalStrut(PADDING));

        //问题4
        JComboBox<String> option4 = new JComboBox<>();
        option4.addItem("0到2种");
        option4.addItem("3到4种");
        option4.addItem("4到6种");
        option4.addItem("6种以上");
        JPanel question4 = createQuestion(
                option4, ImageLoader.question4_1, ImageLoader.question4_2
        );
        box.add(question4);

        box.add(Box.createVerticalStrut(PADDING));

        //问题5
        JComboBox<String> option5 = new JComboBox<>();
        option5.addItem("不到一年");
        option5.addItem("1到3年");
        option5.addItem("3到5年");
        option5.addItem("5年以上");
        JPanel question5 = createQuestion(
                option5, ImageLoader.question5
        );
        box.add(question5);

        box.add(Box.createVerticalStrut(PADDING));

        //问题6
        JComboBox<String> option6 = new JComboBox<>();
        option6.addItem("开始抛售");
        option6.addItem("持有量保持不变");
        option6.addItem("再次购入");
        JPanel question6 = createQuestion(
                option6, ImageLoader.question6_1, ImageLoader.question6_2
        );
        box.add(question6);

        box.add(Box.createVerticalStrut(PADDING));

        //问题7
        JComboBox<String> option7 = new JComboBox<>();
        option7.addItem("5%以下");
        option7.addItem("5%到10%");
        option7.addItem("10%到20%");
        option7.addItem("20%以上");
        JPanel question7 = createQuestion(
                option7, ImageLoader.question7_1, ImageLoader.question7_2
        );
        box.add(question7);

        box.add(Box.createVerticalStrut(PADDING << 1));

        //提交按钮
        JButton submit = new JButton("提交");
        submit.setPreferredSize(new Dimension(100, ANSWER_H));
        submit.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        submit.addActionListener((e) -> {
            int sum = (option1.getSelectedIndex() + 1) +
                    (option2.getSelectedIndex() + 1) +
                    (option3.getSelectedIndex() + 1) +
                    (option4.getSelectedIndex() + 1) +
                    (option5.getSelectedIndex() + 1) +
                    (option6.getSelectedIndex() + 1) * 2 +
                    (option7.getSelectedIndex() + 1);

            try {
                risk = table.get(sum);
            } catch (Exception ex) {
                ex.printStackTrace();
                risk = 0.4;
            }

            JOptionPane.showMessageDialog(QuestionnairePanel.this, "系统已保存您的风险偏好");
        });
        JPanel inner = new JPanel(new FlowLayout(
                FlowLayout.LEFT, PADDING, 0
        ));
        inner.setOpaque(false);
        inner.add(submit);
        JPanel outer = new JPanel();
        outer.setOpaque(false);
        outer.add(inner);
        box.add(outer);

        box.add(Box.createVerticalStrut(PADDING));

        this.setViewportView(box);
        this.getVerticalScrollBar().setUnitIncrement(200);
        this.getViewport().setBackground(AccountConst.BACKGROUND);
        this.setBackground(AccountConst.BACKGROUND);
    }


    private JPanel createQuestion(JComboBox<String> options, ImageIcon... questions) {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);

        for (ImageIcon icon : questions) {
            JLabel label = new JLabel(icon);
            label.setPreferredSize(new Dimension(
                    QUESTION_W, QUESTION_H
            ));
            JPanel p = new JPanel(new FlowLayout(
                    FlowLayout.LEFT, PADDING, 0
            ));
            p.setOpaque(false);
            p.add(label);
            box.add(p);
        }

        box.add(Box.createVerticalStrut(PADDING));

        options.setPreferredSize(new Dimension(
                ANSWER_W, ANSWER_H
        ));
        options.setFont(new Font("等线", Font.ITALIC, 13));
        JPanel p = new JPanel(new FlowLayout(
                FlowLayout.LEFT, PADDING, 0
        ));
        p.setOpaque(false);
        p.add(options);
        box.add(p);

        JPanel panel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 0, 0
        ));
        panel.setOpaque(false);
        panel.add(box);
        return panel;
    }
}
