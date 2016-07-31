package cn.edu.nju.software.po;

public class StockInfoPO {
	 /**
    * Created by Tianqi on 16/8/1.
    * stockCode 股票代码
    * theDate 数据所属日期
    * close 今日收盘价
    * open 今日开盘价
    * volumn 成交量（手）
    * mount 成交额（亿元）
    * turnover 换手率
    * adjPrice 后复权价
    * pe 市盈率
    * pb 市净率
    * high:最高价
    * low:最低价
    */
	private String stockCode;
    private String date;
    private double close;
    private double open;
    private double volumn;
    private double mount;
    private double turnover;
    private double adjPrice;
    private double pe;
    private double pb;
    private double high;
	private double low;
	public StockInfoPO(String stockCode, String date, double close,
			double open, double volumn, double mount, double turnover,
			double adjPrice, double pe, double pb, double high, double low) {
		super();
		this.stockCode = stockCode;
		this.date = date;
		this.close = close;
		this.open = open;
		this.volumn = volumn;
		this.mount = mount;
		this.turnover = turnover;
		this.adjPrice = adjPrice;
		this.pe = pe;
		this.pb = pb;
		this.high = high;
		this.low = low;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getVolumn() {
		return volumn;
	}
	public void setVolumn(double volumn) {
		this.volumn = volumn;
	}
	public double getMount() {
		return mount;
	}
	public void setMount(double mount) {
		this.mount = mount;
	}
	public double getTurnover() {
		return turnover;
	}
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	public double getAdjPrice() {
		return adjPrice;
	}
	public void setAdjPrice(double adjPrice) {
		this.adjPrice = adjPrice;
	}
	public double getPe() {
		return pe;
	}
	public void setPe(double pe) {
		this.pe = pe;
	}
	public double getPb() {
		return pb;
	}
	public void setPb(double pb) {
		this.pb = pb;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
}
