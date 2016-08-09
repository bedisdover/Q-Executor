package DataBase;

import java.util.ArrayList;
import java.util.Date;

public interface StockInfoData {

public ArrayList<String> findByCode(String code,Date start,Date end);
//根据股票代码，开始时间，结束时间 返回一个arraylist 其中每个数据里面不同属性用/分割

}
