package bl.vwap;
/**
 * @author ZhangYF
 *
 */
import bl.TimeUtil;
import blservice.vwap.VWAPService;
import service.MLForVWAPService;
import service.MLForVWAPServiceImpl;
import vo.MLForVWAPPriceVO;
import vo.VolumeVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VWAP implements VWAPService {


	private Map<String,List<Double>>  stockPnMap;
	private VWAPCore vwapCore;

	private VWAP(){
		stockPnMap = new HashMap<String,List<Double>>();
		vwapCore = new VWAPCore();
	}
	private static class SingletonHolder
	{
		private final static VWAP instance = new VWAP();
	}

	public static VWAP getInstance()
	{
		return SingletonHolder.instance;
	}

	public List<VolumeVO> predictVn(VWAP_Param param) throws Exception{
		//已交易比例阈值
		double volThre = 0.8;
		//时间阈值
		double timrThre = 220.0/240;

		//交易量概率密度
		List<Double> Pn;
		if(param.getTimeNode()==param.getStartTimeNode()|| !stockPnMap.containsKey(param.getStockid())){
			Pn=vwapCore.getStaticPn(param.getStockid());
			stockPnMap.put(param.getStockid(), Pn);
		}
		Pn=stockPnMap.get(param.getStockid());
		double gama = 0;
		for(int i = param.getTimeNode()-1;i<Pn.size();i++){
			gama += Pn.get(i);
		}
		if(gama<volThre && 1.0*param.getTimeNode()/TimeUtil.TimeSliceNum <timrThre){
			Pn = vwapCore.getDynamicPn(Pn,param);
			stockPnMap.put(param.getStockid(), Pn);
		}
		List<Integer> Vn = calcVn(Pn,param);
		return getVolumeVOList(Vn,TimeUtil.TimeSliceNum);
	}

	public List<VolumeVO> predictVn1(VWAP_Param param) throws Exception{

		//已交易比例阈值
		double volThre = 0.8;

		//时间阈值
		double timrThre = 220.0/240;

		//交易量概率密度
		List<Double> Pn;
        MLForVWAPService ml = new MLForVWAPServiceImpl();
        MLForVWAPPriceVO priceVO;
		if(param.getTimeNode()==1 || !stockPnMap.containsKey(param.getStockid())){
			//从机器学习处获得静态预测的Vn和Wn
			List<Integer> Vn = ml.getStaticVol(param.getStockid());
            priceVO = ml.getDynamicPrice(param.getStockid());
            List<Double> Wn = priceVO.getPriceList();

			Pn=initPn(Vn);
			stockPnMap.put(param.getStockid(), Pn);
		}
		
		Pn=stockPnMap.get(param.getStockid());


        double gama = 0;
        for(int i = param.getTimeNode()-1;i<Pn.size();i++){
            gama += Pn.get(i);
        }
        if(gama>=volThre || 1.0*param.getTimeNode()/TimeUtil.TimeSliceNum >=timrThre){

        }else if(param.getTimeNode()>1){
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
		List<Integer> Vn = calcVn(Pn,param);
		return  getVolumeVOList(Vn,TimeUtil.TimeSliceNum);
	}

    /**
     *
     * @param Pn
     * @param param
     * @return
     */
    private List<Integer> calcVn(List<Double> Pn,VWAP_Param param){
        List<Integer> Vn = new ArrayList<Integer>();
		double pLocal = 0;

		//深度系数 TODO
		double deep = 1.0;

		//用户设定的时间段内概率分布
		List<Double> plist = new ArrayList<Double>();
		for(int i=param.getTimeNode()-1;i<param.getEndTimeNode();i++){
			pLocal+=Pn.get(i);
		}
		for(int i=param.getTimeNode()-1;i<param.getEndTimeNode();i++){
			plist.add(Pn.get(i)/pLocal);
		}
		//将计算当前时间片的交易量
		int currentVol = Double.valueOf(param.getUserVol()*plist.get(0)*deep).intValue();
		Vn.add(currentVol);

        for(int i=1;i<plist.size();i++){
            int vi=Double.valueOf((param.getUserVol() - currentVol)*Pn.get(i)).intValue();
            Vn.add(vi);
        }
        return Vn;
    }


	/**
	 * 将预测的交易量加上时间片标记
	 * @param Vn
	 * @param currnetTimeNode
     * @return
     */
	private List<VolumeVO> getVolumeVOList(List<Integer> Vn,int currnetTimeNode){
		List<VolumeVO> volumeVOList = new ArrayList<VolumeVO>();
		for(int i=0;i<Vn.size();i++){
			String time = TimeUtil.timeNodeToDate(currnetTimeNode+i);
			VolumeVO volVO = new VolumeVO(time,Vn.get(i));
			volumeVOList.add(volVO);
		}

		return volumeVOList;
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
