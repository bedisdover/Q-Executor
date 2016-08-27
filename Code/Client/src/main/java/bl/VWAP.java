package cn.edu.nju.software.service;
/**
 * @author ZhangYF
 *
 */
import cn.edu.nju.software.vo.MLForVWAPPriceVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VWAP implements  VWAPService{

	
	Map<String,List<Double>> stockPnMap ;
	
	public VWAP(){
		stockPnMap = new HashMap<String,List<Double>>();
		
	}

	public List<Integer> predictVn(VWAP_Param param) throws Exception{
		//交易量概率密度
		List<Double> Pn;
        MLForVWAPService ml = new MLForVWAPServiceImpl();
        MLForVWAPPriceVO priceVO;
		if(param.getTimeNode()==0 || !stockPnMap.containsKey(param.getStockid())){
			//从机器学习处获得静态预测的Vn和Wn
			List<Integer> Vn = ml.getStaticVol(param.getStockid());
            priceVO = ml.getDynamicPrice(param.getStockid());
            List<Double> Wn = priceVO.getPriceList();

			Pn=initPn(Vn);
			stockPnMap.put(param.getStockid(), Pn);
		}
		
		Pn=stockPnMap.get(param.getStockid()); 
		if(param.getTimeNode()>0){
            priceVO = ml.getDynamicPrice(param.getStockid());
            //判断预测的当前时间段是否有误差
            if(priceVO.getCurrentTime() == param.getTimeNode()){
                List<Double> wList = priceVO.getPriceList();
                double Wp = 0;
                try {
                    Wp = calcWp(Pn, wList);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("时间段分割不一致,预测出错");
                    return null;
                }
                List<Double> signal=calcSignal(wList, Wp);
                updatePn(Pn, signal, param);
            }

		}
		List<Integer> Vn = new ArrayList<Integer>();
		for(int i=0;i<Pn.size();i++){
			int vi=Double.valueOf(param.getUserVol()*Pn.get(i)).intValue();
			Vn.add(vi);
		}
		return Vn;
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
            if (wi > Wp) {
                si = 1;
            } else if (wi < Wp) {
                si = -1;
            } else {
                si = 0;
            }
            signal.add(si);
        }
		
		return signal;
	}
}
