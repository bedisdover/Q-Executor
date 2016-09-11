package bl.vwap;
/**
 * @author ZhangYF
 *
 */

import bl.TimeUtil;
import blservice.vwap.VWAPService;
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

		if(param.getTimeNode()<param.getStartTimeNode()||param.getTimeNode()>=param.getEndTimeNode()){
			param.setTimeNode(param.getStartTimeNode());
		}
		System.out.println("start:"+param.getStartTimeNode());
		System.out.println("end:"+param.getEndTimeNode());
		//交易量概率密度
		List<Double> Pn;
		if(param.getTimeNode()==param.getStartTimeNode()|| !stockPnMap.containsKey(param.getStockid())){
			Pn=vwapCore.getStaticPn(param.getStockid());
			stockPnMap.put(param.getStockid(), Pn);
		}else{
			Pn=stockPnMap.get(param.getStockid());

			if(param.getTimeNode()!=25){
				double gama = 0;
				for(int i = param.getTimeNode()-1;i<Pn.size();i++){
					gama += Pn.get(i);
				}
				if(gama<volThre && 1.0*param.getTimeNode()/TimeUtil.TimeSliceNum <timrThre){
					Pn = vwapCore.getDynamicPn(Pn,param);
					stockPnMap.put(param.getStockid(), Pn);
				}
			}

		}

		List<Long> Vn = calcVn(Pn,param);
		return getVolumeVOList(Vn,param.getTimeNode());
	}

    /**
     *
     * @param Pn
     * @param param
     * @return
     */
    private List<Long> calcVn(List<Double> Pn,VWAP_Param param){
        List<Long> Vn = new ArrayList<Long>();
		double pLocal = 0;

		//深度系数 TODO
		double deep = 1.0;

		//用户设定的时间段内概率分布
		List<Double> plist = new ArrayList<Double>();
		if(param.getTimeNode()<0){
			param.setTimeNode(param.getStartTimeNode());
		}
		for(int i=param.getTimeNode()-1;i<param.getEndTimeNode()-1;i++){
			pLocal+=Pn.get(i);
		}
		for(int i=param.getTimeNode()-1;i<param.getEndTimeNode()-1;i++){
			plist.add(Pn.get(i)/pLocal);
		}

		long volSum=0;
		//将计算当前时间片的交易量
		long currentVol = Double.valueOf(param.getUserVol()*plist.get(0)*deep).intValue();
		Vn.add(currentVol);
		volSum+=currentVol;

        for(int i=1;i<plist.size()-1;i++){
            long vi=Double.valueOf(param.getUserVol()*plist.get(i)).intValue();
			volSum+=vi;
            Vn.add(vi);
        }
		if(plist.size()>1){
			Vn.add(param.getUserVol()-volSum);
		}

		System.out.println(plist);
		System.out.println(Vn);
		int sumv=0;
		for(long v:Vn){
			sumv+=v;
		}
		System.out.println(sumv);
        return Vn;
    }


	/**
	 * 将预测的交易量加上时间片标记
	 * @param Vn
	 * @param currnetTimeNode
     * @return
     */
	private List<VolumeVO> getVolumeVOList(List<Long> Vn, int currnetTimeNode){
		List<VolumeVO> volumeVOList = new ArrayList<VolumeVO>();
		for(int i=0;i<Vn.size();i++){
			String time = TimeUtil.timeNodeToDate(currnetTimeNode+i);
			VolumeVO volVO = new VolumeVO(time,Vn.get(i));
			volumeVOList.add(volVO);
		}

		return volumeVOList;
	}

	

}
