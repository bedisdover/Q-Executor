package cn.edu.nju.software.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.model.StockBasicInfo;
import cn.edu.nju.software.model.StockInfoByCom;
import cn.edu.nju.software.model.StockInfoByPer;
import cn.edu.nju.software.model.User;
import cn.edu.nju.software.po.StockInfoPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class StockInfoDao {
	@Resource
	BaseDao baseDao;
	@Resource
	StockBasicInfoDao stockBasicInfoDao;
public List<StockInfoPO> findByCode(String stockCode){
    List<StockInfoPO> stockInfo = ( List<StockInfoPO>)baseDao.findByProperty(StockInfoPO.class,"stockCode",stockCode);
    if(null!=stockInfo&&stockInfo.size()>=1)
        return stockInfo;
    else
        return null;
}



public List<StockInfoPO> filterByTime(List<StockInfoPO> info,Date start,Date end){
		if(info.size()>=1&&end.after(start)){
			List<StockInfoPO> result=new ArrayList<StockInfoPO>();
			for(int i=0;i<info.size();i++){
				Date comp=info.get(i).getDate();
				if((comp.after(start)||comp.equals(start))&&(end.after(comp)||end.equals(comp))){
					result.add(info.get(i));
				}
			}
			
			return result;
		}
		else
		return null;
	}

	//---------------------------------------------栋栋写这里-------------------------------------------------------



	public List<StockInfoByPer> getPerStockInfo(String codeNum,Date date){

		return null;
	}
	public List<StockInfoByCom> getComStockInfo(String codeNum, Date date){
		return null;
	}


	//根据股票代码返回股票的基本信息
	public StockBasicInfo getBasicInfo(String codeNum){
		return stockBasicInfoDao.getStockBasicInfo(codeNum);

	}

	
}
