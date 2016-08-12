package cn.edu.nju.software.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.nju.software.config.MsgInfo;
import cn.edu.nju.software.model.StockBasicInfo;
import cn.edu.nju.software.model.StockInfoByCom;
import cn.edu.nju.software.model.StockInfoByPer;
import cn.edu.nju.software.model.User;
import cn.edu.nju.software.po.StockInfoPO;
import cn.edu.nju.software.utils.CodeUtil;
import cn.edu.nju.software.utils.TimeUtil;
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


	/**
	 * 获取某个日期某支股票的交易信息
	 * @param codeNum 股票的code
	 * @param date 所要获取信息的时间日期
     * @return date下的所有股票交易信息
     */
	public List<StockInfoByPer> getPerStockInfo(String codeNum,Date date){
		if(TimeUtil.isSameDate(date)&&!TimeUtil.isOver6PM(date)){
			//没有超过当日的18点从网页上爬取
			return getPerDealByHTML(codeNum,TimeUtil.getDate(date));
		}
		else {
			//超过当日18点或者是历史数据直接读取URL里面的xls文件获取
			return getPerDealByURL(codeNum,TimeUtil.getDate(date));
		}
	}

	private List<StockInfoByPer> getPerDealByHTML(String code,String date){
		List<StockInfoByPer> stockInfoByPers = new ArrayList<StockInfoByPer>();
		String url = "";

		return null;
	}

	private List<StockInfoByPer> getPerDealByURL(String code,String date){
		List<StockInfoByPer> stockInfoByPers = new ArrayList<StockInfoByPer>();
		String url = "";
		return null;
	}
	/**
	 *	获取大单交易的数据
	 * @param codeNum 股票代码
	 * @param date 时间日期
     * @return 获取大单交易的对象列表 默认超过400手算是大单
     */
	public List<StockInfoByCom> getComStockInfo(String codeNum, Date date){
		//默认超过400手最大
		return getComStockInfo(codeNum,date,400);
	}

	/**
	 * 获取大胆交易的数据
	 * @param codeNum 股票代码
	 * @param date 时间日期
	 * @param maxNum 标准:指定多于多少手为大单子
     * @return 获取大单交易的对象列表
     */
	public List<StockInfoByCom> getComStockInfo(String codeNum,Date date,int maxNum){
		List<StockInfoByCom> stockInfoByComs = new ArrayList<StockInfoByCom>();
		String url = "http://vip.stock.finance.sina.com.cn/quotes_service/view/cn_bill_download.php?" +
				"symbol="+CodeUtil.getCode(codeNum)+"&num=60&page=1&sort=ticktime&asc=0&volume="+maxNum+"00"+"&amount=0&type=0&day="+date;


		return null;
	}


	//根据股票代码返回股票的基本信息
	public StockBasicInfo getBasicInfo(String codeNum){
		return stockBasicInfoDao.getStockBasicInfo(codeNum);

	}

	
}
