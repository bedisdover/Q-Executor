import cn.edu.nju.software.dao.StockBasicInfoDao;
import cn.edu.nju.software.model.StockBasicInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by 王栋 on 2016/8/12 0012.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/mvc.xml","/applicationContext.xml"})
public class InitStockInfo {
    @Resource
    StockBasicInfoDao stockBasicInfoDao;
    @Test
    public void test1() throws JSONException, IOException{
        List<StockBasicInfo> temp = stockBasicInfoDao.getAllStocksBasicInfo();
        JSONArray jsonArray = new JSONArray();
        for (StockBasicInfo stockBasicInfo:temp){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",stockBasicInfo.getCode());
            jsonObject.put("name",stockBasicInfo.getName());
            jsonObject.put("industry",stockBasicInfo.getIndustry());
            jsonArray.put(jsonObject);
        }

        File file = new File("basicInfo.txt");
        if (!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(jsonArray.toString().getBytes());

        System.out.println(temp.size());
        fileOutputStream.close();
    }
}
