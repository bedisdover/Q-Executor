package present.charts.util;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;

/**
 * Created by song on 16-9-9.
 * <p>
 * jFreeChart图表鼠标监听器
 */
public interface MyChartMouseListener extends ChartMouseListener {

    void chartMousePressed(ChartMouseEvent event);


    void chartMouseReleased(ChartMouseEvent event);


    void chartMouseExited(ChartMouseEvent event);
}
