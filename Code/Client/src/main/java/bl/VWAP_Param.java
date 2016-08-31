package bl;

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

	//总时间段数量
	private int timeSliceNum;

	//风险偏好
	private int riskPrefer;

	//是否在规定时间一定全部交易完
	private boolean tradeAll;

	public VWAP_Param(long userVol, String stockid, double delta, int timeNode, int timeSliceNum, int riskPrefer, boolean tradeAll) {
		this.userVol = userVol;
		this.stockid = stockid;
		this.delta = delta;
		this.timeNode = timeNode;
		this.timeSliceNum = timeSliceNum;
		this.riskPrefer = riskPrefer;
		this.tradeAll = tradeAll;
	}

	public VWAP_Param() {
	}

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

	public int getRiskPrefer() {
		return riskPrefer;
	}

	public void setRiskPrefer(int riskPrefer) {
		this.riskPrefer = riskPrefer;
	}

	public boolean isTradeAll() {
		return tradeAll;
	}

	public void setTradeAll(boolean tradeAll) {
		this.tradeAll = tradeAll;
	}
}
