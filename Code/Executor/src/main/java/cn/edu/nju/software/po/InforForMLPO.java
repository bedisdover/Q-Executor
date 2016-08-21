package cn.edu.nju.software.po;

/**
 * Created by LiuXing on 2016/8/18.
 * 用于机器学习动态预测价格的基础数据类
 */
public class InforForMLPO {

    /*
    就不把当前时间片作为这个类的成员变量了
    所有出现这个类的地方，必然会调用StockMLService的getDynamicInforML(String stockID,int numOfStock,int currentTime)
    此方法包换了时间片信息
     */

    private StockForMLPO firstDay;   //前三天的时间片
    private StockForMLPO secondDay; //前两天的时间片
    private StockForMLPO thirdDay;  //前一天的时间片
    private StockForMLPO firstTime; //同一天的前三个时间片
    private StockForMLPO secondTime;//同一天的前二个时间片
    private StockForMLPO thirdTime; //同一天的前一个时间片
    private StockForMLPO currentTime;//当前时间片数据

    public InforForMLPO(StockForMLPO firstDay, StockForMLPO secondDay, StockForMLPO thirdDay, StockForMLPO firstTime, StockForMLPO secondTime, StockForMLPO thirdTime, StockForMLPO currentTime) {
        this.firstDay = firstDay;
        this.secondDay = secondDay;
        this.thirdDay = thirdDay;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
        this.thirdTime = thirdTime;
        this.currentTime=currentTime;
    }

    public StockForMLPO getFirstDay() {
        return firstDay;
    }

    public StockForMLPO getSecondDay() {
        return secondDay;
    }

    public StockForMLPO getThirdDay() {
        return thirdDay;
    }

    public StockForMLPO getFirstTime() {
        return firstTime;
    }

    public StockForMLPO getSecondTime() {
        return secondTime;
    }

    public StockForMLPO getThirdTime() {
        return thirdTime;
    }

    public void setFirstDay(StockForMLPO firstDay) {
        this.firstDay = firstDay;
    }

    public void setSecondDay(StockForMLPO secondDay) {
        this.secondDay = secondDay;
    }

    public void setThirdDay(StockForMLPO thirdDay) {
        this.thirdDay = thirdDay;
    }

    public void setFirstTime(StockForMLPO firstTime) {
        this.firstTime = firstTime;
    }

    public void setSecondTime(StockForMLPO secondTime) {
        this.secondTime = secondTime;
    }

    public void setThirdTime(StockForMLPO thirdTime) {
        this.thirdTime = thirdTime;
    }

    public void setCurrentTime(StockForMLPO currentTime) {
        this.currentTime = currentTime;
    }

    public StockForMLPO getCurrentTime() {
        return currentTime;
    }
}
