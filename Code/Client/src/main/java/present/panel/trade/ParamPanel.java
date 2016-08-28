package present.panel.trade;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Y481L on 2016/8/28.
 *
 * 交易面板中的参数面板
 */
public class ParamPanel extends JPanel {

    ParamPanel(int width, int height) {
        Font font = new Font("微软雅黑", Font.PLAIN, 11);

        JPanel show = new JPanel();//展示
        show.setPreferredSize(new Dimension(850, 500));
        show.setLayout(new BoxLayout(show, BoxLayout.Y_AXIS));
        show.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
        JPanel line2 = new JPanel();//第二行
        line2.setPreferredSize(new Dimension(850, 400));
        line2.setLayout(new BoxLayout(line2, BoxLayout.X_AXIS));


        JPanel box2 = new JPanel();
        box2.setPreferredSize(new Dimension(300, 400));
        box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));
        box2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        p1.setPreferredSize(new Dimension(295, 25));
        JLabel zjzh = new JLabel("  资金账户");
        zjzh.setFont(font);
        zjzh.setPreferredSize(new Dimension(70, 25));
        JComboBox zjzh2 = new JComboBox();
        zjzh2.addItem("12345");
        zjzh2.addItem("54321");
        zjzh2.setFocusable(false);
        zjzh2.setPreferredSize(new Dimension(100, 25));
        JLabel kyzj = new JLabel("  可用资金");
        kyzj.setFont(font);
        kyzj.setPreferredSize(new Dimension(70, 25));
        JLabel kyzj2 = new JLabel();
        kyzj2.setPreferredSize(new Dimension(70, 25));
        p1.add(zjzh);
        p1.add(zjzh2);
        p1.add(kyzj);
        p1.add(kyzj2);
        box2.add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        p2.setPreferredSize(new Dimension(295, 25));
        JLabel zqdm = new JLabel("  证券代码");
        zqdm.setFont(font);
        zqdm.setPreferredSize(new Dimension(70, 25));
        JTextField zqdm2 = new JTextField();
        zqdm2.setPreferredSize(new Dimension(70, 25));
        JLabel zqmc = new JLabel("  证券名称");
        zqmc.setFont(font);
        zqmc.setPreferredSize(new Dimension(70, 25));
        JTextField zqmc2 = new JTextField();
        zqmc2.setPreferredSize(new Dimension(70, 25));
        p2.add(zqdm);
        p2.add(zqdm2);
        p2.add(zqmc);
        p2.add(zqmc2);
        box2.add(p2);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.setPreferredSize(new Dimension(300, 25));
        JLabel tzfx = new JLabel("  投资方向");
        tzfx.setFont(font);
        tzfx.setPreferredSize(new Dimension(70, 25));
        JComboBox tzfx2 = new JComboBox();
        tzfx2.setFont(font);
        tzfx2.addItem("买");
        tzfx2.addItem("卖");
        tzfx2.setFocusable(false);
        tzfx2.setPreferredSize(new Dimension(100, 25));
        JLabel sl = new JLabel("   数量(股)");
        sl.setFont(font);
        sl.setPreferredSize(new Dimension(70, 25));
        JLabel sl2 = new JLabel();
        sl2.setPreferredSize(new Dimension(70, 25));
        p3.add(tzfx);
        p3.add(tzfx2);
        p3.add(sl);
        p3.add(sl2);
        box2.add(p3);

        JPanel p5 = new JPanel();
        p5.setLayout(new FlowLayout(FlowLayout.LEFT));
        p5.setPreferredSize(new Dimension(300, 25));
        JLabel xdje = new JLabel("  下单金额");
        xdje.setFont(font);
        xdje.setPreferredSize(new Dimension(70, 25));
        JLabel xdje2 = new JLabel();
        xdje2.setPreferredSize(new Dimension(70, 25));
        JLabel pk = new JLabel("   盘口");
        pk.setFont(font);
        pk.setPreferredSize(new Dimension(70, 25));
        JComboBox pk2 = new JComboBox();
        pk2.addItem("自动盘口");
        pk2.addItem("手动盘口");
        pk2.setFont(font);
        pk2.setFocusable(false);
        pk2.setPreferredSize(new Dimension(100, 25));
        p5.add(pk);
        p5.add(pk2);
        p5.add(xdje);
        p5.add(xdje2);
        box2.add(p5);

        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout(FlowLayout.LEFT));
        p4.setPreferredSize(new Dimension(300, 25));
        JLabel kssj = new JLabel("  开始时间");
        kssj.setFont(font);
        kssj.setPreferredSize(new Dimension(60, 25));
        JComboBox kssj2 = new JComboBox();
        kssj2.setFont(font);
        kssj2.setFocusable(false);
        kssj2.setPreferredSize(new Dimension(80, 25));
        JLabel jssj = new JLabel("  结束时间");
        jssj.setFont(font);
        jssj.setPreferredSize(new Dimension(60, 25));
        JComboBox jssj2 = new JComboBox();
        jssj2.setFont(font);
        jssj2.setFocusable(false);
        jssj2.setPreferredSize(new Dimension(80, 25));
        p4.add(kssj);
        p4.add(kssj2);
        p4.add(jssj);
        p4.add(jssj2);
        box2.add(p4);


        JPanel p6 = new JPanel();
        p6.setLayout(new FlowLayout(FlowLayout.LEFT));
        p6.setPreferredSize(new Dimension(300, 25));
        JLabel cjzx = new JLabel(" 成交置信度");
        cjzx.setFont(font);
        cjzx.setPreferredSize(new Dimension(60, 25));
        JComboBox cjzx2 = new JComboBox();
        cjzx2.setFont(font);
        cjzx2.setFocusable(false);
        cjzx2.setPreferredSize(new Dimension(80, 25));
        JLabel ckzq = new JLabel("参考周期/分");
        ckzq.setFont(font);
        ckzq.setPreferredSize(new Dimension(60, 25));
        JComboBox ckzq2 = new JComboBox();
        ckzq2.setFont(font);
        ckzq2.setFocusable(false);
        ckzq2.setPreferredSize(new Dimension(80, 25));
        p6.add(cjzx);
        p6.add(cjzx2);
        p6.add(ckzq);
        p6.add(ckzq2);
        box2.add(p6);

        JPanel p7 = new JPanel();
        p7.setLayout(new FlowLayout(FlowLayout.LEFT));
        p7.setPreferredSize(new Dimension(300, 25));
        JLabel hcks = new JLabel("  回测开始");
        hcks.setFont(new Font("微软雅黑", Font.PLAIN, 11));
        hcks.setPreferredSize(new Dimension(60, 25));

        Chooser ser1 = Chooser.getInstance();
        JTextField text1 = new JTextField();
        text1.setBounds(10, 10, 200, 30);

        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String[] now = df.format(day).split("-");
        text1.setText(now[0] + "-" + now[1] + "-" + now[2]);
        ser1.register(text1);

        Chooser ser2 = Chooser.getInstance();
        JTextField text2 = new JTextField();
        text2.setBounds(10, 10, 200, 30);
        text2.setText(now[0] + "-" + now[1] + "-" + now[2]);
        ser2.register(text2);


        JLabel hcjs = new JLabel("  回测结束");
        hcjs.setFont(new Font("微软雅黑", Font.PLAIN, 11));
        hcjs.setPreferredSize(new Dimension(60, 25));


        p7.add(hcks);
        p7.add(text1);
        p7.add(hcjs);
        p7.add(text2);
        box2.add(p7);

        JPanel p8 = new JPanel();
        p8.setLayout(new FlowLayout(FlowLayout.LEFT));
        p8.setPreferredSize(new Dimension(300, 25));
        JLabel qdms = new JLabel("    启动模式");
        qdms.setPreferredSize(new Dimension(70, 25));
        qdms.setFont(font);
        JRadioButton radioButton2 = new JRadioButton("交易");// 创建单选按钮
        radioButton2.setFont(font);
        p8.add(qdms);
        p8.add(radioButton2);// 应用单选按钮
        JRadioButton radioButton3 = new JRadioButton("回测");// 创建单选按钮
        radioButton3.setFont(font);
        p8.add(radioButton3);// 应用单选按钮
        JLabel none = new JLabel("                   ");
        none.setPreferredSize(new Dimension(40, 25));

        radioButton2.setFont(font);
        JButton btn1 = new JButton("启动");
        btn1.setFocusPainted(false);
        //btn1.setContentAreaFilled(false); //设置按钮透明
        btn1.setFont(font);
        p8.add(btn1);
        box2.add(p8);
        line2.add(box2);
        JPanel box3 = new JPanel();
        box3.setPreferredSize(new Dimension(500, 400));
        line2.add(box3);


        show.add(line2);
    }

    private void init() {

    }
}
