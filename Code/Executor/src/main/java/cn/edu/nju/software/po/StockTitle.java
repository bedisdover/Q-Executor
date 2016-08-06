package cn.edu.nju.software.po;

public class StockTitle {
private String code;//股票代码
private String name;//股票名称
private String industry;//行业名称
private String city;//城市名称
private long timeTomarket;//股票的上市日期



public StockTitle(String code, String name, String industry, String city,
		long timeTomarket) {
	super();
	this.code = code;
	this.name = name;
	this.industry = industry;
	this.city = city;
	this.timeTomarket = timeTomarket;
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
public String getIndustry() {
	return industry;
}
public void setIndustry(String industry) {
	this.industry = industry;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public long getTimeTomarket() {
	return timeTomarket;
}
public void setTimeTomarket(long timeTomarket) {
	this.timeTomarket = timeTomarket;
}

}
