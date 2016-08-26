package vo;

import java.util.Date;

/**
 * Created by song on 16-8-26.
 * <p>
 * 股票实时数据对象
 */
public class StockNowTimeVO {
    /**
     * 当前价格
     */
    private double price;
    /**
     * 收盘价
     */
    private double close;
    /**
     * 涨跌额
     */
    private double incNum;
    /**
     * 涨跌幅
     */
    private double incRate;
    /**
     * 开盘价
     */
    private double open;
    /**
     * 成交量
     */
    private double amount;
    /**
     * 成交额
     */
    private double volume;

    /**
     * 买一～买五报价
     */
    private double buy1price, buy2price, buy3Price, buy4Price, buy5Price;
    /**
     * 买一～买五申报量
     */
    private double buy1amount, buy2amount, buy3amount, buy4amount, buy5amount;

    /**
     * 卖一～卖五报价
     */
    private double sell1price, sell2price, sell3price, sell4price, sell5price;
    /**
     * 卖一～卖五申报量
     */
    private double sell1amount, sell2amount, sell3amount, sell4amount, sell5amount;

    /**
     * 当前时间，格式：‘yyyy-MM-dd hh：mm：ss’
     */
    private Date time;

    public StockNowTimeVO() {

    }

    public StockNowTimeVO(double price, double close, double incNum, double incRate,
                          double open, double amount, double volume, double buy1price,
                          double buy2Price, double buy3Price, double buy4Price,
                          double buy5Price, double buy1amount, double buy2amount,
                          double buy3amount, double buy4amount, double buy5amount,
                          double sell1price, double sell2price, double sell3price,
                          double sell4price, double sell5price, double sell1amount,
                          double sell2amount, double sell3amount, double sell4amount,
                          double sell5amount, Date time) {
        this.price = price;
        this.close = close;
        this.incNum = incNum;
        this.incRate = incRate;
        this.open = open;
        this.amount = amount;
        this.volume = volume;
        this.buy1price = buy1price;
        this.buy2price = buy2Price;
        this.buy3Price = buy3Price;
        this.buy4Price = buy4Price;
        this.buy5Price = buy5Price;
        this.buy1amount = buy1amount;
        this.buy2amount = buy2amount;
        this.buy3amount = buy3amount;
        this.buy4amount = buy4amount;
        this.buy5amount = buy5amount;
        this.sell1price = sell1price;
        this.sell2price = sell2price;
        this.sell3price = sell3price;
        this.sell4price = sell4price;
        this.sell5price = sell5price;
        this.sell1amount = sell1amount;
        this.sell2amount = sell2amount;
        this.sell3amount = sell3amount;
        this.sell4amount = sell4amount;
        this.sell5amount = sell5amount;
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getIncNum() {
        return incNum;
    }

    public void setIncNum(double incNum) {
        this.incNum = incNum;
    }

    public double getIncRate() {
        return incRate;
    }

    public void setIncRate(double incRate) {
        this.incRate = incRate;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getBuy1price() {
        return buy1price;
    }

    public void setBuy1price(double buy1price) {
        this.buy1price = buy1price;
    }

    public double getBuy2price() {
        return buy2price;
    }

    public void setBuy2price(double buy2price) {
        this.buy2price = buy2price;
    }

    public double getBuy3Price() {
        return buy3Price;
    }

    public void setBuy3Price(double buy3Price) {
        this.buy3Price = buy3Price;
    }

    public double getBuy4Price() {
        return buy4Price;
    }

    public void setBuy4Price(double buy4Price) {
        this.buy4Price = buy4Price;
    }

    public double getBuy5Price() {
        return buy5Price;
    }

    public void setBuy5Price(double buy5Price) {
        this.buy5Price = buy5Price;
    }

    public double getBuy1amount() {
        return buy1amount;
    }

    public void setBuy1amount(double buy1amount) {
        this.buy1amount = buy1amount;
    }

    public double getBuy2amount() {
        return buy2amount;
    }

    public void setBuy2amount(double buy2amount) {
        this.buy2amount = buy2amount;
    }

    public double getBuy3amount() {
        return buy3amount;
    }

    public void setBuy3amount(double buy3amount) {
        this.buy3amount = buy3amount;
    }

    public double getBuy4amount() {
        return buy4amount;
    }

    public void setBuy4amount(double buy4amount) {
        this.buy4amount = buy4amount;
    }

    public double getBuy5amount() {
        return buy5amount;
    }

    public void setBuy5amount(double buy5amount) {
        this.buy5amount = buy5amount;
    }

    public double getSell1price() {
        return sell1price;
    }

    public void setSell1price(double sell1price) {
        this.sell1price = sell1price;
    }

    public double getSell2price() {
        return sell2price;
    }

    public void setSell2price(double sell2price) {
        this.sell2price = sell2price;
    }

    public double getSell3price() {
        return sell3price;
    }

    public void setSell3price(double sell3price) {
        this.sell3price = sell3price;
    }

    public double getSell4price() {
        return sell4price;
    }

    public void setSell4price(double sell4price) {
        this.sell4price = sell4price;
    }

    public double getSell5price() {
        return sell5price;
    }

    public void setSell5price(double sell5price) {
        this.sell5price = sell5price;
    }

    public double getSell1amount() {
        return sell1amount;
    }

    public void setSell1amount(double sell1amount) {
        this.sell1amount = sell1amount;
    }

    public double getSell2amount() {
        return sell2amount;
    }

    public void setSell2amount(double sell2amount) {
        this.sell2amount = sell2amount;
    }

    public double getSell3amount() {
        return sell3amount;
    }

    public void setSell3amount(double sell3amount) {
        this.sell3amount = sell3amount;
    }

    public double getSell4amount() {
        return sell4amount;
    }

    public void setSell4amount(double sell4amount) {
        this.sell4amount = sell4amount;
    }

    public double getSell5amount() {
        return sell5amount;
    }

    public void setSell5amount(double sell5amount) {
        this.sell5amount = sell5amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
