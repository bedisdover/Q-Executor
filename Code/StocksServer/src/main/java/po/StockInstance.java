package po;

/**
 * Created by 王栋 on 2016/8/1 0001.
 */
public class StockInstance {
    private Float open;
    private Float close;
    private Float current;
    private Float todayMax;
    private Float todayMin;

    private Long dealNumber;//成交的股数 应该是以百股为单位
    private Float dealMoney;//成交金额 应该是以万元为单位
    private int buy1Number;

    private Float buy1;
    private int buy2Number;
    private Float buy2;
    private int buy3Number;
    private Float buy3;
    private int buy4Number;
    private Float buy4;
    private int buy5Number;
    private Float buy5;
;
    private int sell1Number;
    private Float sell1;
    private int sell2Number;
    private Float sell2;
    private int sell3Number;
    private Float sell3;
    private int sell4Number;
    private Float sell4;
    private int sell5Number;
    private Float sell5;


    private Long time;//当前的事件 精确到毫秒 Long类型
    private String code;//股票的代码

    public StockInstance() {
    }

    public StockInstance(Float open, Float close, Float current, Float todayMax, Float todayMin,
                         Long dealNumber, Float dealMoney, int buy1Number, Float buy1, int buy2Number,
                         Float buy2, int buy3Number, Float buy3, int buy4Number, Float buy4, int buy5Number,
                         Float buy5, int sell1Number, Float sell1, int sell2Number, Float sell2, int sell3Number,
                         Float sell3, int sell4Number, Float sell4, int sell5Number, Float sell5,Long time,String code) {
        this.open = open;
        this.close = close;
        this.current = current;
        this.todayMax = todayMax;
        this.todayMin = todayMin;
        this.dealNumber = dealNumber;
        this.dealMoney = dealMoney;
        this.buy1Number = buy1Number;
        this.buy1 = buy1;
        this.buy2Number = buy2Number;
        this.buy2 = buy2;
        this.buy3Number = buy3Number;
        this.buy3 = buy3;
        this.buy4Number = buy4Number;
        this.buy4 = buy4;
        this.buy5Number = buy5Number;
        this.buy5 = buy5;
        this.sell1Number = sell1Number;
        this.sell1 = sell1;
        this.sell2Number = sell2Number;
        this.sell2 = sell2;
        this.sell3Number = sell3Number;
        this.sell3 = sell3;
        this.sell4Number = sell4Number;
        this.sell4 = sell4;
        this.sell5Number = sell5Number;
        this.sell5 = sell5;
        this.time = time;
        this.code = code;
    }

    public Float getOpen() {
        return open;
    }

    public void setOpen(Float open) {
        this.open = open;
    }

    public Float getClose() {
        return close;
    }

    public void setClose(Float close) {
        this.close = close;
    }

    public Float getCurrent() {
        return current;
    }

    public void setCurrent(Float current) {
        this.current = current;
    }

    public Float getTodayMax() {
        return todayMax;
    }

    public void setTodayMax(Float todayMax) {
        this.todayMax = todayMax;
    }

    public Float getTodayMin() {
        return todayMin;
    }

    public void setTodayMin(Float todayMin) {
        this.todayMin = todayMin;
    }

    public Long getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(Long dealNumber) {
        this.dealNumber = dealNumber;
    }

    public Float getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(Float dealMoney) {
        this.dealMoney = dealMoney;
    }

    public int getBuy1Number() {
        return buy1Number;
    }

    public void setBuy1Number(int buy1Number) {
        this.buy1Number = buy1Number;
    }

    public Float getBuy1() {
        return buy1;
    }

    public void setBuy1(Float buy1) {
        this.buy1 = buy1;
    }

    public int getBuy2Number() {
        return buy2Number;
    }

    public void setBuy2Number(int buy2Number) {
        this.buy2Number = buy2Number;
    }

    public Float getBuy2() {
        return buy2;
    }

    public void setBuy2(Float buy2) {
        this.buy2 = buy2;
    }

    public int getBuy3Number() {
        return buy3Number;
    }

    public void setBuy3Number(int buy3Number) {
        this.buy3Number = buy3Number;
    }

    public Float getBuy3() {
        return buy3;
    }

    public void setBuy3(Float buy3) {
        this.buy3 = buy3;
    }

    public int getBuy4Number() {
        return buy4Number;
    }

    public void setBuy4Number(int buy4Number) {
        this.buy4Number = buy4Number;
    }

    public Float getBuy4() {
        return buy4;
    }

    public void setBuy4(Float buy4) {
        this.buy4 = buy4;
    }

    public int getBuy5Number() {
        return buy5Number;
    }

    public void setBuy5Number(int buy5Number) {
        this.buy5Number = buy5Number;
    }

    public Float getBuy5() {
        return buy5;
    }

    public void setBuy5(Float buy5) {
        this.buy5 = buy5;
    }

    public int getSell1Number() {
        return sell1Number;
    }

    public void setSell1Number(int sell1Number) {
        this.sell1Number = sell1Number;
    }

    public Float getSell1() {
        return sell1;
    }

    public void setSell1(Float sell1) {
        this.sell1 = sell1;
    }

    public int getSell2Number() {
        return sell2Number;
    }

    public void setSell2Number(int sell2Number) {
        this.sell2Number = sell2Number;
    }

    public Float getSell2() {
        return sell2;
    }

    public void setSell2(Float sell2) {
        this.sell2 = sell2;
    }

    public int getSell3Number() {
        return sell3Number;
    }

    public void setSell3Number(int sell3Number) {
        this.sell3Number = sell3Number;
    }

    public Float getSell3() {
        return sell3;
    }

    public void setSell3(Float sell3) {
        this.sell3 = sell3;
    }

    public int getSell4Number() {
        return sell4Number;
    }

    public void setSell4Number(int sell4Number) {
        this.sell4Number = sell4Number;
    }

    public Float getSell4() {
        return sell4;
    }

    public void setSell4(Float sell4) {
        this.sell4 = sell4;
    }

    public int getSell5Number() {
        return sell5Number;
    }

    public void setSell5Number(int sell5Number) {
        this.sell5Number = sell5Number;
    }

    public Float getSell5() {
        return sell5;
    }

    public void setSell5(Float sell5) {
        this.sell5 = sell5;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
