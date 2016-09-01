package cn.edu.nju.software.vo;

/**
 * Created by JiayiWu on 9/1/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class DeepStockVO {

    String timeline;
    double deepPrice;

    public DeepStockVO() {
    }

    public DeepStockVO(String timeline, double deepPrice) {
        this.timeline = timeline;
        this.deepPrice = deepPrice;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public double getDeepPrice() {
        return deepPrice;
    }

    public void setDeepPrice(double deepPrice) {
        this.deepPrice = deepPrice;
    }
}
