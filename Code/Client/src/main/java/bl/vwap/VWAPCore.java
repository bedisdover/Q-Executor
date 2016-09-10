package bl.vwap;

import blservice.vwap.MLForVWAPService;
import vo.MLForVWAPPriceVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangYF on 2016/9/8.
 */
public class VWAPCore {

    private MLForVWAPService ml = null;

    public VWAPCore(){
        //TODO 获得机器学习接口
        ml = new MLForVWAPServiceImpl();
    }

    /**
     * 获得静态预测的所有时间段概率分布
     * @param stockid 股票代码
     * @return Pn
     * @throws Exception
     */
    public List<Double> getStaticPn(String stockid) throws Exception{
        //TODO 异常处理
        List<Integer> Vn = ml.getStaticVol(stockid);
        return initPn(Vn);
    }

    /**
     * 获得动态预测的所有时间段概率分布
     * @param pList
     * @param param
     * @return
     * @throws Exception
     */
    public List<Double> getDynamicPn(List<Double> pList,VWAP_Param param) throws Exception{
        //TODO 异常处理
        MLForVWAPPriceVO priceVO = ml.getDynamicPrice(param.getStockid());
        List<Double> wList = priceVO.getPriceList();
        double Wp = 0;
        try {
            Wp = calcWp(pList, wList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("时间段分割不一致,预测出错");
            return pList;
        }
        List<Double> signal=calcSignal(wList, Wp);
        pList = updatePn(pList, signal, param);
        return pList;
    }

    /**
     * 初始化Pn
     * @param vList
     * @return
     */
    private List<Double> initPn(List<Integer> vList){
        List<Double> pList = new ArrayList<Double>();
        double marketVol=0;
        for(int vi:vList){
            marketVol+=vi;
        }
        for(int vi:vList){
            double pi=vi*1.0/marketVol;
            pList.add(pi);
        }
        return pList;
    }

    /**
     * 对Pn进行迭代更新
     * @param pList
     * @param signal
     * @param param
     * @return
     */
    private List<Double> updatePn(List<Double> pList,List<Double> signal,VWAP_Param param){

        int n=param.getTimeNode();//当前为第n个时间段
        double delta=param.getDelta();
        double sn=signal.get(n);

        double Pk=pList.get(n);
        double sumPl=0;
        for(int i=n+1;i<pList.size();i++){
            sumPl+=pList.get(i);
        }

        //更新Pk(k=n)
        double pi=Pk*(1+delta*sn);
        pList.set(n, pi);

        //更新Pk,(k>=n+1)
        for(int i=n+1;i<pList.size();i++){
            pi=pList.get(i)*(1-( (1.0*delta*sn*Pk) /sumPl));
            pList.set(i, pi);
        }

        return pList;
    }

    /**
     * 计算W(p)
     * @param pList
     * @param wList
     * @return
     * @throws Exception
     */
    private double calcWp(List<Double> pList,List<Double> wList) throws Exception{

        double Wp=0;

        if(pList.size() != wList.size()){
            throw new Exception();
        }

        for(int i=0;i<pList.size();i++){
            Wp+=pList.get(i)*wList.get(i);
        }
        return Wp;
    }

    /**
     * 计算signal
     * @param wList
     * @param Wp
     * @return
     */
    private List<Double> calcSignal(List<Double> wList,double Wp){

        List<Double> signal = new ArrayList<Double>();
        double wi,si;
        for (Double aWList : wList) {
            wi = aWList;
            if (wi > Wp*1.05) {
                si = 1;
            } else if (wi < Wp*0.95) {
                si = -1;
            } else {
                si = 0;
            }
            signal.add(si);
        }

        return signal;
    }
}
