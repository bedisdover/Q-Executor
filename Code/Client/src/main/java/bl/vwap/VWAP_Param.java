package bl.vwap;

/**
 * @author ZhangYF
 *
 */
public class VWAP_Param {

	//用户交易量
	private long userVol;
	
	//股票代码
	private String stockid;
	
	//风险偏好
	private double delta;

	//当前时间段
	private int timeNode;
	//开始时间片
	private int startTimeNode;
	//结束时间片
	private int endTimeNode;

	public VWAP_Param(long userVol, String stockid, double delta, int timeNode, int startTimeNode, int endTimeNode) {
		this.userVol = userVol;
		this.stockid = stockid;
		this.delta = delta;
		this.timeNode = timeNode;
		this.startTimeNode = startTimeNode;
		this.endTimeNode = endTimeNode;
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

	public int getStartTimeNode() {
		return startTimeNode;
	}

	public void setStartTimeNode(int startTimeNode) {
		this.startTimeNode = startTimeNode;
	}

	public int getEndTimeNode() {
		return endTimeNode;
	}

	public void setEndTimeNode(int endTimeNode) {
		this.endTimeNode = endTimeNode;
	}
}
