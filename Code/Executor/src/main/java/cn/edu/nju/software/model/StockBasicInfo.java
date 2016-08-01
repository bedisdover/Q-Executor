package cn.edu.nju.software.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
@Entity
@Table(name = "stock_basic_info")
public class StockBasicInfo {
    //股票的基本信息
    private String code;//股票代码
    private String name;//股票公司名称
    private String industry;//股票的类型
    private String area;//股票的公司创建地
    private Date timeToMarket;//股票的上市日期

    public StockBasicInfo(String code, String name, String industry, String area, Date timeToMarket) {
        this.code = code;
        this.name = name;
        this.industry = industry;
        this.area = area;
        this.timeToMarket = timeToMarket;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getTimeToMarket() {
        return timeToMarket;
    }

    public void setTimeToMarket(Date timeToMarket) {
        this.timeToMarket = timeToMarket;
    }
}
