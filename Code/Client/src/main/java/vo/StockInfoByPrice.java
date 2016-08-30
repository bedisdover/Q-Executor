package vo;

/**
 * Created by Jiayiwu on 8/12/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class StockInfoByPrice implements Comparable {

    private double price;
    private double trunover;
    private double percent;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTrunover() {
        return trunover;
    }

    public void setTrunover(double trunover) {
        this.trunover = trunover;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public int compareTo(Object o) {
        StockInfoByPrice tem = (StockInfoByPrice) o;
        double temPrice = tem.getPrice();

        if ((price - temPrice) >= 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
