package present.component.chart;

import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.ValueTick;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import present.utils.ColorUtil;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

/**
 * Created by song on 16-9-12.
 * <p>
 * 自定义纵轴
 * 选定一个基点数字，这个基点数字用灰色字显示，大于基点的数字用红色字显示，小于基点的数字用绿色字显示。
 */
public class MyNumberAxis extends NumberAxis {

    /**
     * 基准值
     */
    private double baseValue;

    public MyNumberAxis(double baseValue) {
        super();

        this.baseValue = baseValue;
    }

    @Override
    protected AxisState drawTickMarksAndLabels(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge) {
        AxisState state = new AxisState(cursor);
        if (this.isAxisLineVisible()) {
            this.drawAxisLine(g2, cursor, dataArea, edge);
        }

        List ticks = this.refreshTicks(g2, state, dataArea, edge);
        state.setTicks(ticks);
        g2.setFont(this.getTickLabelFont());
        Iterator iterator = ticks.iterator();

        while (true) {
            ValueTick used;
            do {
                if (!iterator.hasNext()) {
                    double used1 = 0.0D;
                    if (this.isTickLabelsVisible()) {
                        if (edge == RectangleEdge.LEFT) {
                            used1 += this.findMaximumTickLabelWidth(ticks, g2, plotArea, this.isVerticalTickLabels());
                            state.cursorLeft(used1);
                        } else if (edge == RectangleEdge.RIGHT) {
                            used1 = this.findMaximumTickLabelWidth(ticks, g2, plotArea, this.isVerticalTickLabels());
                            state.cursorRight(used1);
                        } else if (edge == RectangleEdge.TOP) {
                            used1 = this.findMaximumTickLabelHeight(ticks, g2, plotArea, this.isVerticalTickLabels());
                            state.cursorUp(used1);
                        } else if (edge == RectangleEdge.BOTTOM) {
                            used1 = this.findMaximumTickLabelHeight(ticks, g2, plotArea, this.isVerticalTickLabels());
                            state.cursorDown(used1);
                        }
                    }

                    return state;
                }

                used = (ValueTick) iterator.next();
                if (this.isTickLabelsVisible()) {
                    g2.setPaint(ColorUtil.getColorByComparing(used.getValue(), baseValue));
                    float[] ol = this.calculateAnchorPoint(used, cursor, dataArea, edge);
                    TextUtilities.drawRotatedString(used.getText(), g2, ol[0], ol[1], used.getTextAnchor(), used.getAngle(), used.getRotationAnchor());
                }
            }
            while ((!this.isTickMarksVisible() || !used.getTickType().equals(TickType.MAJOR)) && (!this.isMinorTickMarksVisible() || !used.getTickType().equals(TickType.MINOR)));

            double ol1 = used.getTickType().equals(TickType.MINOR) ? (double) this.getMinorTickMarkOutsideLength() : (double) this.getTickMarkOutsideLength();
            double il = used.getTickType().equals(TickType.MINOR) ? (double) this.getMinorTickMarkInsideLength() : (double) this.getTickMarkInsideLength();
            float xx = (float) this.valueToJava2D(used.getValue(), dataArea, edge);
            Line2D.Double mark = null;
            g2.setStroke(this.getTickMarkStroke());
            g2.setPaint(this.getTickMarkPaint());
            if (edge == RectangleEdge.LEFT) {
                mark = new Line2D.Double(cursor - ol1, (double) xx, cursor + il, (double) xx);
            } else if (edge == RectangleEdge.RIGHT) {
                mark = new Line2D.Double(cursor + ol1, (double) xx, cursor - il, (double) xx);
            } else if (edge == RectangleEdge.TOP) {
                mark = new Line2D.Double((double) xx, cursor - ol1, (double) xx, cursor + il);
            } else if (edge == RectangleEdge.BOTTOM) {
                mark = new Line2D.Double((double) xx, cursor + ol1, (double) xx, cursor - il);
            }

            g2.draw(mark);
        }
    }

}
