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


    /**
     * toString 是为了测试用的
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeepStockVO{");
        sb.append("timeline='").append(timeline).append('\'');
        sb.append(", deepPrice=").append(deepPrice);
        sb.append('}');
        return sb.toString();
    }
}
