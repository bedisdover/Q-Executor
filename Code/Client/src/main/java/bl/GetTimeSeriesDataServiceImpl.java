package bl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import vo.StockTimeSeriesVO;
import blservice.GetTimeSeriesDataService;

public class GetTimeSeriesDataServiceImpl implements GetTimeSeriesDataService{

	public List<StockTimeSeriesVO> getData(String codeNum) {
		
		return null;
	}
	public static void main(String args[]){
		String url="http://121.42.143.164/StockInfoByTime?codeNum=sh600001";
		try {
			URL ur=new URL(url);
			BufferedReader reader=new BufferedReader(new InputStreamReader(ur.openStream()));
			String line=reader.readLine();
			
			System.out.println(line);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
