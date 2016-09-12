package present.charts;

import org.jfree.data.Range;
import present.utils.ColorUtil;
import util.NumberUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 16-9-7.
 *
 * 纵轴面板
 */
class TickPanel extends JPanel {
    private Font font = new Font("微软雅黑", Font.PLAIN, 10);
    private static int tickNum = 10;

    private double base;

    TickPanel(Range range, double base) {
        this.base = base;

        init();
        createUIComponents(range);
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            setLayout(new GridLayout(0, 1));
        });
    }

    private void createUIComponents(Range range) {
        SwingUtilities.invokeLater(() -> {
            List<Double> ticks = getRange(range);

            for (double tick : ticks) {
                JLabel label = new JLabel();

                label.setForeground(ColorUtil.getColorByComparing(tick, base));

                label.setText(NumberUtil.round(tick) + "");
                label.setFont(font);

                add(label);
            }

            revalidate();
        });
    }

    private List<Double> getRange(Range range) {
        List<Double> ticks = new ArrayList<>();

        double lower = range.getLowerBound();
        double upper = range.getUpperBound();
        double tickUnit = (upper - lower) / (tickNum - 1);

        for (int i = tickNum - 1; i >= 0; i--) {
            ticks.add(NumberUtil.round(lower + i * tickUnit));
        }

        return ticks;
    }
}

