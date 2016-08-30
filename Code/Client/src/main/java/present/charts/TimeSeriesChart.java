package present.charts;

import vo.StockTimeSeriesVO;

import javax.swing.*;
import java.util.List;

/**
 * Created by song on 16-8-29.
 * <p>
 * 分时图
 */
public class TimeSeriesChart {
    public JPanel getChart() {
        return new JPanel();
    }

    /**
     * 注入数据
     * 为了避免阻塞，TimeSeriesPanel中加载数据结束后，通过此方法注入数据
     *
     * @param timeSeriesVOList 分数数据
     */
    public void injectData(List<StockTimeSeriesVO> timeSeriesVOList) {
        System.out.println(timeSeriesVOList);
    }
}
