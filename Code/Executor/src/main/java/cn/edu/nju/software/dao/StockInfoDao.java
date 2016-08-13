package cn.edu.nju.software.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.nju.software.model.StockBasicInfo;
import cn.edu.nju.software.model.StockInfoByCom;
import cn.edu.nju.software.model.StockInfoByPer;
import cn.edu.nju.software.po.StockInfoPO;
import cn.edu.nju.software.utils.StockUtil;
import cn.edu.nju.software.utils.TimeUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
//		if(TimeUtil.isSameDate(date)&&!TimeUtil.isOver6PM(date)){
			//没有超过当日的18点从网页上爬取
			return getPerDealByHTML(codeNum,TimeUtil.getDate(date));
//		}
//		else {
//			//超过当日18点或者是历史数据直接读取URL里面的xls文件获取
//			return getPerDealByURL(codeNum,TimeUtil.getDate(date));
//		}
	}

	/**
	 * 对于当日的数据如果是在18点之前需要爬取网页获取交易信息
	 * @param code
	 * @param date
     * @return
     */
	private List<StockInfoByPer> getPerDealByHTML(String code,String date){
		List<StockInfoByPer> stockInfoByPers = new ArrayList<StockInfoByPer>();
		String urlFirst = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_Transactions.getAllPageTime?date="+date+"&symbol="+StockUtil.getCode(code);
		String urlNext = "http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradedetail.php?symbol="+StockUtil.getCode(code)+"&date="+date+"&page=";
		int pages = getPages(urlFirst);
		System.out.println(pages);

		for (int i = 1 ; i <= pages ; i++){
			addToStockInfoByPer(urlNext+i,stockInfoByPers);
		}


		return stockInfoByPers;
	}

	private void addToStockInfoByPer(String url,List<StockInfoByPer> stockInfoByPers){

		try {
			Document document = Jsoup.connect(url).get();
			Element element = document.getElementById("datatbl");
			Elements elements = element.getElementsByTag("tr");

			for(int i = 1 ; i<elements.size() ; i++){
				String[] infos = elements.get(i).text().split(" ");
				//String time, double price, double change_price, double vloume, double totalNum, int type
				StockInfoByPer stockInfoByPer = new StockInfoByPer(infos[0],Double.parseDouble(infos[1]),
						StockUtil.getChangePrice(infos[3]),Double.parseDouble(infos[4].replaceAll(",","")),
						Double.parseDouble(infos[5].replaceAll(",","")),StockUtil.getType(infos[6]));
				stockInfoByPers.add(stockInfoByPer);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}
	private int getPages(String url){
		int pages = 0 ;
		try {
			URL ur = new URL(url);
			InputStreamReader reader = new InputStreamReader(ur.openStream(),"GBK");
			BufferedReader bufferedReader = new BufferedReader(reader);
			pages = bufferedReader.readLine().split(",").length;
			pages = (pages-1)/3;
			bufferedReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return pages;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return pages;
		} catch (IOException e) {
			e.printStackTrace();
			return pages;
		}

		return pages;
	}

	private List<StockInfoByPer> getPerDealByURL(String code,String date){
		//然后我发现原来那个xls文件竟然balabala有问题
		List<StockInfoByPer> stockInfoByPers = new ArrayList<StockInfoByPer>();
		String url = "http://market.finance.sina.com.cn/downxls.php?date="+date+"&symbol="+ StockUtil.getCode(code);
		try {
			URL ur = new URL(url);
			InputStreamReader reader = new InputStreamReader(ur.openStream(),"GBK");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			bufferedReader.readLine();//首行是标题我们不要了
			while ((line=bufferedReader.readLine())!=null){
				String[] infos = line.split("\t");
				//String time, double price, double change_price, double vloume, double totalNum, int type
				StockInfoByPer stockInfoByPer = new StockInfoByPer(infos[0],Double.parseDouble(infos[1]), StockUtil.getChangePrice(infos[2]),
						Double.parseDouble(infos[3]),Double.parseDouble(infos[4]), StockUtil.getType(infos[5]));

				stockInfoByPers.add(stockInfoByPer);

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return stockInfoByPers;
		} catch (IOException e) {
			e.printStackTrace();
			return stockInfoByPers;
		} catch (Exception e){
			return stockInfoByPers;
		}


		return stockInfoByPers;
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
	public List<StockInfoByCom> getComStockInfo(String codeNum,Date date,double maxNum){
		List<StockInfoByCom> stockInfoByComs = new ArrayList<StockInfoByCom>();
		System.out.println((long)(maxNum*100));
		String url = "http://vip.stock.finance.sina.com.cn/quotes_service/view/cn_bill_download.php?" +
				"symbol="+ StockUtil.getCode(codeNum)+"&num=60&page=1&sort=ticktime&asc=0&volume="+(long)(maxNum*100)+"&amount=0&type=0&day="+TimeUtil.getDate(date);
		try {
			URL ur = new URL(url);
			InputStreamReader reader = new InputStreamReader(ur.openStream(),"GBK");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;

			bufferedReader.readLine();//首行是标题我们不要了
			while ((line=bufferedReader.readLine())!=null){
				String[] infos = line.split(",");
				//String time, double price, double volume, double f_price, int type
				StockInfoByCom stockInfoByCom = new StockInfoByCom(infos[2],Double.parseDouble(infos[3]),
						Double.parseDouble(infos[4])/100,Double.parseDouble(infos[5]), StockUtil.getType(infos[6]));
				stockInfoByComs.add(stockInfoByCom);
			}
			bufferedReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return stockInfoByComs;
		} catch (IOException e) {
			e.printStackTrace();
			return stockInfoByComs;
		} catch (Exception e){
			return stockInfoByComs;
		}


		return stockInfoByComs;
	}


	//根据股票代码返回股票的基本信息
	public StockBasicInfo getBasicInfo(String codeNum){
		return stockBasicInfoDao.getStockBasicInfo(codeNum);

	}

	
}
