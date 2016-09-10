package present.charts;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.entity.ChartEntity;
import present.charts.util.MyChartMouseAdapter;
import present.charts.util.MyChartPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-10.
 * <p>
 * 装载chartPanel和相应的textPanel(用于显示鼠标划过的数据信息)
 */
class MyPanel extends JPanel {
    private JLabel labelText;

    private MyChartPanel chartPanel;

    private ChartVO chartVO;

    MyPanel(MyChartPanel chartPanel, ChartVO chartVO) {
        this.chartPanel = chartPanel;
        this.chartVO = chartVO;

        init();
        createUIComponents();
        addListeners();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> setLayout(new BorderLayout()));
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            JPanel textPanel = new JPanel();
            labelText = new JLabel(" ");
            labelText.setFont(new Font("微软雅黑", Font.PLAIN, 12));
            textPanel.add(labelText);

            add(textPanel, BorderLayout.NORTH);
            add(chartPanel, BorderLayout.CENTER);

            revalidate();
            repaint();
        });
    }

    private void addListeners() {
        chartPanel.addChartMouseListener(new MyChartMouseAdapter() {
            @Override
            public void chartMouseExited(ChartMouseEvent event) {
                SwingUtilities.invokeLater(() -> labelText.setText(" "));
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
                int xPos = event.getTrigger().getX();
                int yPos = event.getTrigger().getY();
                chartPanel.setHorizontalAxisTrace(true);
                chartPanel.setVerticalAxisTrace(true);
                ChartEntity chartEntity = chartPanel.getEntityForPoint(xPos, yPos);

                String[] info = chartEntity.toString().split(" ");
                try {
                    int item = Integer.parseInt(info[6].substring(0, info[6].length() - 1));

                    SwingUtilities.invokeLater(() -> labelText.setText(chartVO.getText(item)));
                } catch (IndexOutOfBoundsException e) {
                    // 鼠标划过非图线区域引起数组越界异常,无需处理
                }
            }
        });
    }
}
