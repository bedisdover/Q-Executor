package vo;

/**
 * Created by 王栋 on 2016/8/31 0031.
 */
public class HotStockVO implements Comparable{

    /**
     * 股票代码
     */
    private String code;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 最近一个交易日的涨跌额
     */
    private double pchange;

    /**
     * 当前的价格
     */
    private String currentPrice;
    /**
     * 上榜原因
     */
    private String reason;
    /**
     * 时间 一般就是为最近一个交易日
     */
    private String date;



    public HotStockVO(String code, String name, double pchange, String reason, String date) {
        this.code = code;
        this.name = name;
        this.pchange = pchange;
        this.reason = reason;
        this.date = date;
        this.currentPrice = "--";
    }

    public HotStockVO(String code, String name, double pchange, String currentPrice, String reason, String date) {
        this.code = code;
        this.name = name;
        this.pchange = pchange;
        this.currentPrice = currentPrice;
        this.reason = reason;
        this.date = date;
    }

    public HotStockVO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPchange() {
        return pchange;
    }

    public void setPchange(double pchange) {
        this.pchange = pchange;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotStockVO that = (HotStockVO) o;

        return code != null ? code.equals(that.code) : that.code == null;

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    public void addReason(String anotherReason){
        reason+=(";"+anotherReason);
    }

    @Override
    public int compareTo(Object o) {
        if (((HotStockVO)o).getReason().length()>this.getReason().length()){
            return 1;
        }else if (((HotStockVO)o).getReason().length()==this.getReason().length()){
            return 0;
        }else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "HotStockVO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", pchange=" + pchange +
                ", currentPrice='" + currentPrice + '\'' +
                ", reason='" + reason + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
