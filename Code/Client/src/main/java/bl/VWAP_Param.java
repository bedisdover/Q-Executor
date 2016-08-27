package cn.edu.nju.software.service;

/**
 * @author ZhangYF
 *
 */
public class VWAP_Param {

	//用户交易量
	private long userVol;
	
	//股票代码
	private String stockid;
	
	//
	private double delta;

	//当前时间段
	private int timeNode;

	public VWAP_Param(long userVol, String stockid, double delta, int timeNode, int timeSliceNum) {
		this.userVol = userVol;
		this.stockid = stockid;
		this.delta = delta;
		this.timeNode = timeNode;
		this.timeSliceNum = timeSliceNum;
	}

	public VWAP_Param() {
	}

	//总时间段数量
	private int timeSliceNum;

	public long getUserVol() {
		return userVol;
	}

	public void setUserVol(long userVol) {
		this.userVol = userVol;
	}

	public String getStockid() {
		return stockid;
	}

	public void setStockid(String stockid) {
		this.stockid = stockid;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public int getTimeNode() {
		return timeNode;
	}

	public void setTimeNode(int timeNode) {
		this.timeNode = timeNode;
	}

	public int getTimeSliceNum() {
		return timeSliceNum;
	}

	public void setTimeSliceNum(int timeSliceNum) {
		this.timeSliceNum = timeSliceNum;
	}
	
	
}
