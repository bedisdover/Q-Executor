package present.panel.account;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Y481L on 2016/9/10.
 *
 * 问卷面板
 */
public class RiskSettingPanel extends JScrollPane {

    private static final int PADDING = 40;

    private static final int QUESTION_W = 500;

    private static final int QUESTION_H = 36;

    private static final int ANSWER_W = 440;

    private static final int ANSWER_H = QUESTION_H;

    /*===================问题默认选项，保存用户之前所选==========================*/

    private static int Q1_SELECT = 0;

    private static int Q2_SELECT = 0;

    private static int Q3_SELECT = 0;

    private static int Q4_SELECT = 0;

    private static int Q5_SELECT = 0;

    private static int Q6_SELECT = 0;

    private static int Q7_SELECT = 0;

    /*===============================================*/

    private static final Font font = new Font("微软雅黑", Font.PLAIN, 15);

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

    RiskSettingPanel() {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);

        box.add(Box.createVerticalStrut(PADDING));

        //问题1
        JComboBox<String> option1 = new JComboBox<>();
        option1.addItem("避免亏损");
        option1.addItem("谨慎增值");
        option1.addItem("稳健增值");
        option1.addItem("显著增长");
        option1.setSelectedIndex(Q1_SELECT);
        JPanel question1 = createQuestion(
                option1, "1、贵司的投资目标是什么？"
        );
        box.add(question1);

        box.add(Box.createVerticalStrut(PADDING));

        //问题2
        JComboBox<String> option2 = new JComboBox<>();
        option2.addItem("不足一年");
        option2.addItem("1到5年");
        option2.addItem("5到10年");
        option2.addItem("10年以上");
        option2.setSelectedIndex(Q2_SELECT);
        JPanel question2 = createQuestion(
                option2, "2、贵公司投资研究团队人员的平均投资年限为"
        );
        box.add(question2);

        box.add(Box.createVerticalStrut(PADDING));

        //问题3
        JComboBox<String> option3 = new JComboBox<>();
        option3.addItem("现金流短期压力很大，有可能需要随时将投资变现弥补现金流");
        option3.addItem("现金流短期有一定压力，需要流动性较高的投资");
        option3.addItem("现金流长期有一定压力，需要一定的投资收益弥补现金流");
        option3.addItem("现金流长期充裕，几乎没有压力");
        option3.setSelectedIndex(Q3_SELECT);
        JPanel question3 = createQuestion(
                option3, "3、贵公司面临的现金流压力如何？"
        );
        box.add(question3);

        box.add(Box.createVerticalStrut(PADDING));

        //问题4
        JComboBox<String> option4 = new JComboBox<>();
        option4.addItem("0到2种");
        option4.addItem("3到4种");
        option4.addItem("4到6种");
        option4.addItem("6种以上");
        option4.setSelectedIndex(Q4_SELECT);
        JPanel question4 = createQuestion(
                option4, "4、股票、债券、基金、外汇、商品、专户理财、股指期货和融资融券，这八类投资工具贵司深入研究过几种"
        );
        box.add(question4);

        box.add(Box.createVerticalStrut(PADDING));

        //问题5
        JComboBox<String> option5 = new JComboBox<>();
        option5.addItem("不到一年");
        option5.addItem("1到3年");
        option5.addItem("3到5年");
        option5.addItem("5年以上");
        option5.setSelectedIndex(Q5_SELECT);
        JPanel question5 = createQuestion(
                option5, "5、贵司有多少年的证券投资经验？"
        );
        box.add(question5);

        box.add(Box.createVerticalStrut(PADDING));

        //问题6
        JComboBox<String> option6 = new JComboBox<>();
        option6.addItem("开始抛售");
        option6.addItem("持有量保持不变");
        option6.addItem("再次购入");
        option6.setSelectedIndex(Q6_SELECT);
        JPanel question6 = createQuestion(
                option6, "6、若购入的股票对比购入时上涨20%，贵司将作出如何反应？"
        );
        box.add(question6);

        box.add(Box.createVerticalStrut(PADDING));

        //问题7
        JComboBox<String> option7 = new JComboBox<>();
        option7.addItem("5%以下");
        option7.addItem("5%到10%");
        option7.addItem("10%到20%");
        option7.addItem("20%以上");
        option7.setSelectedIndex(Q7_SELECT);
        JPanel question7 = createQuestion(
                option7, "7、贵公司目前投资于风险资产的比例已经达到了资产的"
        );
        box.add(question7);

        box.add(Box.createVerticalStrut(PADDING << 1));

        //提交按钮
        JButton submit = new JButton("提交");
        submit.setPreferredSize(new Dimension(100, ANSWER_H));
        submit.setFont(font);
        submit.addActionListener((e) -> {
            Q1_SELECT = option1.getSelectedIndex();
            Q2_SELECT = option2.getSelectedIndex();
            Q3_SELECT = option3.getSelectedIndex();
            Q4_SELECT = option4.getSelectedIndex();
            Q5_SELECT = option5.getSelectedIndex();
            Q6_SELECT = option6.getSelectedIndex();
            Q7_SELECT = option7.getSelectedIndex();
            int sum=(Q1_SELECT + 1) +
                    (Q2_SELECT + 1) +
                    (Q3_SELECT + 1) +
                    (Q4_SELECT + 1) +
                    (Q5_SELECT + 1) +
                    (Q6_SELECT + 1) * 2 +
                    (Q7_SELECT + 1);

            try {
                risk = table.get(sum);
            } catch (Exception ex) {
                ex.printStackTrace();
                risk = 0.4;
            }

            JOptionPane.showMessageDialog(RiskSettingPanel.this, "系统已保存您的风险偏好");
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
        this.getVerticalScrollBar().setUnitIncrement(50);
        this.getViewport().setOpaque(false);
        this.setOpaque(false);
    }


    private JPanel createQuestion(JComboBox<String> options, String question) {
        Box box = Box.createVerticalBox();
        box.setOpaque(false);

        //问题
        int line = 25;
        int length = question.length();
        Box qb = Box.createVerticalBox();
        qb.setOpaque(false);
        for(int i = 0; i < length; i += line) {
            JLabel q = new JLabel(question.substring(
                    i, (i + line >= length) ? length : i + line
            ));
            q.setOpaque(false);
            q.setPreferredSize(new Dimension(
                    QUESTION_W, QUESTION_H
            ));
            q.setFont(font);
            JPanel qp = new JPanel(new FlowLayout(
                    FlowLayout.LEFT, PADDING, 0
            ));
            qp.setOpaque(false);
            qp.add(q);
            qb.add(qp);
        }

        box.add(qb);

        box.add(Box.createVerticalStrut(PADDING));

        //答案
        options.setPreferredSize(new Dimension(
                ANSWER_W, ANSWER_H
        ));
        options.setFont(font);
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
