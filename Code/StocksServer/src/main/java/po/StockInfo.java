package po;

/**
 * Created by 王栋 on 2016/7/31 0031.
 */
public class StockInfo {
    //股票的基本信息
    private String code;//股票代码
    private String name;//股票公司名称
    private String industry;//股票的类型
    private String area;//股票的公司创建地
    private Long timeToMarket;//股票的上市日期

    public StockInfo(String code, String name, String industry, String area, Long timeToMarket) {
        this.code = code;
        this.name = name;
        this.industry = industry;
        this.area = area;
        this.timeToMarket = timeToMarket;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getIndustry() {
        return industry;
    }

    public String getArea() {
        return area;
    }

    public Long getTimeToMarket() {
        return timeToMarket;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setTimeToMarket(Long timeToMarket) {
        this.timeToMarket = timeToMarket;
    }
}
