package present.component.chart;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Created by song on 16-9-9.
 * <p>
 * 自定义ChartPanel,添加鼠标按下和离开监听
 */
public class MyChartPanel extends ChartPanel {
    public MyChartPanel(JFreeChart chart) {
        super(chart);

        // 禁用鼠标缩放
        setMouseZoomable(false);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.jfree.chart.ChartPanel#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent event) {
        Insets insets = getInsets();
        int x = (int) ((event.getX() - insets.left) / this.getScaleX());
        int y = (int) ((event.getY() - insets.top) / this.getScaleY());

        this.setAnchor(new Point2D.Double(x, y));
        if (this.getChart() == null) {
            return;
        }
        this.getChart().setNotify(true);  // force a redraw
        // new entity code...
        Object[] listeners = this.getListeners(ChartMouseListener.class);
        if (listeners.length == 0) {
            return;
        }

        ChartEntity entity = null;
        if (this.getChartRenderingInfo() != null) {
            EntityCollection entities = this.getChartRenderingInfo().getEntityCollection();
            if (entities != null) {
                entity = entities.getEntity(x, y);
            }
        }

        ChartMouseEvent chartEvent = new ChartMouseEvent(getChart(), event, entity);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((MyChartMouseListener) listeners[i]).chartMousePressed(chartEvent);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see org.jfree.chart.ChartPanel#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        Insets insets = getInsets();
        int x = (int) ((event.getX() - insets.left) / this.getScaleX());
        int y = (int) ((event.getY() - insets.top) / this.getScaleY());

        this.setAnchor(new Point2D.Double(x, y));
        if (this.getChart() == null) {
            return;
        }
        this.getChart().setNotify(true);  // force a redraw
        // new entity code...
        Object[] listeners = this.getListeners(ChartMouseListener.class);
        if (listeners.length == 0) {
            return;
        }

        ChartEntity entity = null;
        if (this.getChartRenderingInfo() != null) {
            EntityCollection entities = this.getChartRenderingInfo().getEntityCollection();
            if (entities != null) {
                entity = entities.getEntity(x, y);
            }
        }
        ChartMouseEvent chartEvent = new ChartMouseEvent(getChart(), event, entity);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((MyChartMouseListener) listeners[i]).chartMouseReleased(chartEvent);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see org.jfree.chart.ChartPanel#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent event) {
        Insets insets = getInsets();
        int x = (int) ((event.getX() - insets.left) / this.getScaleX());
        int y = (int) ((event.getY() - insets.top) / this.getScaleY());

        this.setAnchor(new Point2D.Double(x, y));
        if (this.getChart() == null) {
            return;
        }
        this.getChart().setNotify(true);  // force a redraw
        // new entity code...
        Object[] listeners = this.getListeners(ChartMouseListener.class);
        if (listeners.length == 0) {
            return;
        }

        ChartEntity entity = null;
        if (this.getChartRenderingInfo() != null) {
            EntityCollection entities = this.getChartRenderingInfo().getEntityCollection();
            if (entities != null) {
                entity = entities.getEntity(x, y);
            }
        }

        ChartMouseEvent chartEvent = new ChartMouseEvent(getChart(), event, entity);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((MyChartMouseListener) listeners[i]).chartMouseExited(chartEvent);
        }
    }
}
