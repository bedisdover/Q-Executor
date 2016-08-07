package cn.edu.nju.software.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
@Entity
@Table(name = "StocksInfo")
public class StockBasicInfo {
    //股票的基本信息
    private String code;//股票代码
    private String name;//股票公司名称
    private String industry;//股票的类型
    private String area;//股票的公司创建地
    private Long timeToMarket;//股票的上市日期

    public StockBasicInfo(String code, String name, String industry, String area, Long timeToMarket) {
        this.code = code;
        this.name = name;
        this.industry = industry;
        this.area = area;
        this.timeToMarket = timeToMarket;
    }

    public StockBasicInfo() {
    }

    @Id
    @Column(length=8)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(length=20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(length=15)
    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
    @Column(length=10)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getTimeToMarket() {
        return timeToMarket;
    }

    public void setTimeToMarket(Long timeToMarket) {
        this.timeToMarket = timeToMarket;
    }

    //写这个是为了方便测试输出
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StockBasicInfo{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", industry='").append(industry).append('\'');
        sb.append(", area='").append(area).append('\'');
        sb.append(", timeToMarket=").append(timeToMarket);
        sb.append('}');
        return sb.toString();
    }
}
