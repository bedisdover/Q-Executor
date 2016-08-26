package vo;

import java.util.Date;

/**
 * Created by 王栋 on 2016/7/31 0031.
 * <p>
 * 股票基本信息对象
 */
public class StockBasicInfoVO {
    //股票的基本信息
    private String code;//股票代码
    private String name;//股票公司名称
    private String industry;//股票的类型
    private String area;//股票的公司创建地
    private float pe;//市盈率
    private float outstanding;//流通股本
    private float totals;//总股本(万)
    private float totalAssets;//总资产(万)
    private float liquidAssets;//流动资产
    private float fixedAssets;//固定资产
    private float reserved;//公积金
    private float reservedPerShare;//每股公积金
    private float eps;//每股收益
    private float bvps;//每股净资
    private float pb;//市净率
    private Date timeToMarket;//股票的上市日期

    public StockBasicInfoVO() {
    }

    public StockBasicInfoVO(String code, String name, String industry, String area, float pe, float outstanding,
                            float totals, float totalAssets, float liquidAssets, float fixedAssets,
                            float reserved, float reservedPerShare, float eps, float bvps, float pb,
                            Date timeToMarket) {
        this.code = code;
        this.name = name;
        this.industry = industry;
        this.area = area;
        this.pe = pe;
        this.outstanding = outstanding;
        this.totals = totals;
        this.totalAssets = totalAssets;
        this.liquidAssets = liquidAssets;
        this.fixedAssets = fixedAssets;
        this.reserved = reserved;
        this.reservedPerShare = reservedPerShare;
        this.eps = eps;
        this.bvps = bvps;
        this.pb = pb;
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

    public float getPe() {
        return pe;
    }

    public void setPe(float pe) {
        this.pe = pe;
    }

    public float getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(float outstanding) {
        this.outstanding = outstanding;
    }

    public float getTotals() {
        return totals;
    }

    public void setTotals(float totals) {
        this.totals = totals;
    }

    public float getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(float totalAssets) {
        this.totalAssets = totalAssets;
    }

    public float getLiquidAssets() {
        return liquidAssets;
    }

    public void setLiquidAssets(float liquidAssets) {
        this.liquidAssets = liquidAssets;
    }

    public float getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(float fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public float getReserved() {
        return reserved;
    }

    public void setReserved(float reserved) {
        this.reserved = reserved;
    }

    public float getReservedPerShare() {
        return reservedPerShare;
    }

    public void setReservedPerShare(float reservedPerShare) {
        this.reservedPerShare = reservedPerShare;
    }

    public float getEps() {
        return eps;
    }

    public void setEps(float eps) {
        this.eps = eps;
    }

    public float getBvps() {
        return bvps;
    }

    public void setBvps(float bvps) {
        this.bvps = bvps;
    }

    public float getPb() {
        return pb;
    }

    public void setPb(float pb) {
        this.pb = pb;
    }

    @Override
    public String toString() {
        return "StockBasicInfoVO{" + "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", industry='" + industry + '\'' +
                ", area='" + area + '\'' +
                ", pe=" + pe +
                ", outstanding=" + outstanding +
                ", totals=" + totals +
                ", totalAssets=" + totalAssets +
                ", liquidAssets=" + liquidAssets +
                ", fixedAssets=" + fixedAssets +
                ", reserved=" + reserved +
                ", reservedPerShare=" + reservedPerShare +
                ", eps=" + eps +
                ", bvps=" + bvps +
                ", pb=" + pb +
                ", timeToMarket=" + timeToMarket +
                '}';
    }
}
