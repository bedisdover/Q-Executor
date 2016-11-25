package present.panel.trade;

import present.panel.loading.CalculatingPanel;
import present.utils.ImageLoader;
import vo.VolumeVO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TradePanel extends JPanel {

    //参数面板宽度
    private static final int PARAM_PANEL_W = 460;

    //参数面板高度
    private static final int PARAM_PANEL_H = 540;

//    //分时面板宽度
//    private static final int TIME_SERIES_W = MainFrame.PANEL_W - PARAM_PANEL_W;
//
//    //分时面板高度
//    private static final int TIME_SERIES_H = PARAM_PANEL_H;

    //结果面板宽度
    private static final int RESULT_PANEL_W = PARAM_PANEL_W;

//    //有分时面板时的高度
//    //结果面板高度
//    private static final int RESULT_PANEL_H = MainFrame.PANEL_H - PARAM_PANEL_H;

    //结果面板高度
    private static final int RESULT_PANEL_H = PARAM_PANEL_H;

    //计时面板宽度
    private static final int TIME_PANEL_W = 100;

    //结果面板
    private ResultPanel resultPanel = new ResultPanel(RESULT_PANEL_W, RESULT_PANEL_H);

//    //分时面板
//    private TimeSeriesPanel timeSeriesPanel = new TimeSeriesPanel();

//    //分时面板容器
//    private JPanel timeContainer = new JPanel(new BorderLayout());

    //结果面板容器
    private JPanel resultContainer = new JPanel(new BorderLayout());

    //加载效果显示面板
    private CalculatingPanel loading = new CalculatingPanel(RESULT_PANEL_W, RESULT_PANEL_H);

//    //空白分时面板
//    private JPanel empty_time = new JPanel() {
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            g.drawImage(
//                    ImageLoader.empty_time_series, 0, 0, this.getWidth(), this.getHeight(),
//                    0, 0,
//                    ImageLoader.empty_time_series.getWidth(null),
//                    ImageLoader.empty_time_series.getHeight(null),
//                    null
//            );
//        }
//    };

//    //分时面板显示的股票的股票代码
//    private String stockCode;

    //当前计算进度
    private int percent = 1;

    //刷新进度条线程
    private Timer task = null;

    //空白结果面板
    private JPanel empty_result = new JPanel() {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(
                    ImageLoader.empty_result, 0, 0, this.getWidth(), this.getHeight(),
                    0, 0,
                    ImageLoader.empty_result.getWidth(null),
                    ImageLoader.empty_result.getHeight(null),
                    null
            );
        }
    };


    //刷新结果剩余时间
    private int timeToUpdate = UPDATE_GAP;

    //刷新间隔
    private static final int UPDATE_GAP = 300;

    //刷新频率
    private static final int FREQUENCY = 1000;

    //时间相对于计时面板的横坐标
    private static final int TIME_X = 42;

    //时间相对于计时面板的纵坐标
    private static final int TIME_Y = 214;

    //参数面板和结果面板距离上边框的距离
    private static final int PANEL_Y = 100;

    //参数面板和结果面板之间的距离
    private static final int PANEL_GAP = 20;

    //计时面板
    private JPanel timerPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image img = ImageLoader.timer;
            g.drawImage(
                    img, 0, 0, img.getWidth(null), img.getHeight(null), null
            );
            g.setFont(new Font("等线", Font.ITALIC, 20));
            g.setColor(Color.GREEN);
            g.drawString(String.valueOf(timeToUpdate), TIME_X, TIME_Y);
        }
    };

    //计时线程
    private Timer timer = new Timer(FREQUENCY, (e) -> {
        --timeToUpdate;
        timerPanel.repaint();

        if(timeToUpdate <= 0) {
            update();
        }
    });

    //参数面板
    private ParamPanel param;

    public TradePanel() {
        //参数面板
        param = new ParamPanel(PARAM_PANEL_W, PARAM_PANEL_H, this);

//        //设置空白分时面板
//        empty_time.setPreferredSize(new Dimension(TIME_SERIES_W, TIME_SERIES_H));
//        timeContainer.setPreferredSize(new Dimension(TIME_SERIES_W, TIME_SERIES_H));
//        timeContainer.add(empty_time, BorderLayout.CENTER);

        //设置空白结果面板
        empty_result.setOpaque(false);
        empty_result.setPreferredSize(new Dimension(RESULT_PANEL_W, RESULT_PANEL_H));
        resultContainer.setPreferredSize(new Dimension(RESULT_PANEL_W, RESULT_PANEL_H));
        resultContainer.setOpaque(false);
        resultContainer.add(empty_result, BorderLayout.CENTER);

        //计时面板
        timerPanel.setPreferredSize(new Dimension(TIME_PANEL_W, RESULT_PANEL_H));

//        //将参数面板和分时面板添加到整个面板上方
//        Box up = Box.createHorizontalBox();
//        up.add(param);
//        up.add(timeContainer);

//        //将结果面板添加到整个面板下方
//        this.setLayout(new BorderLayout());
//        this.add(up, BorderLayout.NORTH);
//        this.add(resultContainer, BorderLayout.CENTER);

        this.setLayout(new FlowLayout(
                FlowLayout.CENTER, PANEL_GAP, PANEL_Y
        ));
        this.add(param);
        this.add(resultContainer);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                ImageLoader.trade_bg, 0, 0, this.getWidth(), this.getHeight(), null
        );
    }

    void stopUpdate() {
        timer.stop();
        timeToUpdate = UPDATE_GAP;
        timerPanel.setVisible(false);
        resultContainer.remove(timerPanel);
        resultContainer.revalidate();
    }

    void generatingResult() {
        jump(empty_result, resultPanel, loading);
        loading.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        start();
    }

    //剩余交易量
    private long remain;

    /**
     * 刷新结果面板
     * @param result 交易预测结果
     * @param type   交易类型
     */
    void updateResultPanel(List<VolumeVO> result, String type) {
        loading.setProcess(99);
        stop();

        SwingUtilities.invokeLater(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            if(result == null) {
                jump(resultPanel, loading, empty_result);
                param.stopUpdate();
                return ;
            }

            if(result.size() == 0) {
                jump(resultPanel, loading, empty_result);
                param.stopUpdate();
                return ;
            }

            //计算剩余交易量
            long sum = 0;
            for (VolumeVO vo : result) {
                sum += vo.getVolume();
            }
            remain = sum - result.get(0).getVolume();

            jump(empty_result, loading, resultPanel);
            this.resultPanel.update(result, type);
            //计时面板，显示下一次刷新剩余时间
            timerPanel.setVisible(true);
            resultContainer.add(timerPanel, BorderLayout.WEST);
            resultContainer.revalidate();
            //开始计时线程，显示还有多久再次刷新
            if(!timer.isRunning()) {
                timer.restart();
            }
        });
    }

    /**
     * 不明确从哪个面板跳转而来，跳到to面板
     * @param from1 目前可能显示的面板
     * @param from2 目前可能显示的面板
     * @param to    要跳转到的面板
     */
    private void jump(JComponent from1, JComponent from2, JComponent to) {
        from1.setVisible(false);
        resultContainer.remove(from1);
        from2.setVisible(false);
        resultContainer.remove(from2);
        to.setVisible(true);
        resultContainer.add(to, BorderLayout.CENTER);
        resultContainer.revalidate();
    }

//    void updateTimeSeriesPanel(String code) {
//        stockCode = code;
//        timeContainer.remove(empty_time);
//        timeContainer.add(timeSeriesPanel, BorderLayout.CENTER);
//        timeSeriesPanel.setStockCode(code);
//    }

    private void start() {
        loading.setProcess(0);
        percent = 1;
        task = new Timer(800, (e) -> {
            loading.setProcess(percent);
            percent += 1;
        });
        task.start();
    }

    private void stop() {
        task.stop();
    }

    /**
     * 刷新结果面板和分时面板
     */
    private void update() {
        //刷新分时面板
//        timeSeriesPanel.setStockCode(stockCode);

        //刷新结果面板
        generatingResult();
        param.calculate(remain);

        //重新开始计时
        timeToUpdate = UPDATE_GAP;
    }

}
