import cn.edu.nju.software.config.State;
import cn.edu.nju.software.config.StringMessage;
import cn.edu.nju.software.utils.JsonDataUtil;
import cn.edu.nju.software.utils.StringHashUtil;
import cn.edu.nju.software.vo.NowTimeSelectedStockInfoVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by Jiayiwu on 8/14/16.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class JsonTestByJiayiWu {
        @Test
        public void test(){
            NowTimeSelectedStockInfoVO test = getNowTimeStockInfo("sh601998");
            System.out.println(test.getDate());

            HashMap<String,String> hashMap = new HashMap<>();

            hashMap.put("sh601998","sh601998");
            hashMap.put("sh601991","sh601991");
            hashMap.put("sh601992","sh601992");

            System.out.println(StringHashUtil.hash2string(hashMap));

        }

    public static NowTimeSelectedStockInfoVO getNowTimeStockInfo(String codeNum) {
        StringMessage stringMessage=JsonDataUtil.getNowTimeStockResult(codeNum);
        if(stringMessage.getResult()== State.OK){
            JSONArray jsonArray=JSONArray.fromObject(stringMessage.getData());
            JSONObject jsonObject1=jsonArray.getJSONObject(0);
            JSONObject jsonObject2=jsonObject1.getJSONObject("data");
            NowTimeSelectedStockInfoVO po=new NowTimeSelectedStockInfoVO(jsonObject2.getString("gid"),jsonObject2.getDouble("increPer"),jsonObject2.getDouble("increase"),
                    jsonObject2.getString("name"),jsonObject2.getDouble("todayStartPri"),jsonObject2.getDouble("yestodEndPri"),jsonObject2.getDouble("nowPri"),
                    jsonObject2.getDouble("todayMax"),jsonObject2.getDouble("todayMin"),jsonObject2.getString("date"),jsonObject2.getString("time"),
                    jsonObject2.getDouble("traNumber"),jsonObject2.getDouble("traAmount"));
            return  po;

        }
        return null;
    }
}
