package bl.vwap;

import bl.Connect;
import blservice.vwap.MLForVWAPService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vo.MLForVWAPPriceVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 16-9-9.
 * VWAP相关
 */
public class MLForVWAPServiceImpl implements MLForVWAPService {
    @Override
    public MLForVWAPPriceVO getDynamicPrice(String stockID) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(getContent("dynamicPrice", stockID));

        JSONArray array = jsonObject.getJSONArray("priceList");
        int currentTime = jsonObject.getInt("currentTime");

        ArrayList<Double> price = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            price.add((Double) array.get(i));
        }

        return new MLForVWAPPriceVO(price, currentTime);
    }

    @Override
    public List<Integer> getStaticVol(String stockID) throws IOException, JSONException {
        JSONArray array = new JSONArray(getContent("staticVol", stockID));

        List<Integer> volume = new ArrayList<>();
        for (int i = 0; i< array.length(); i++) {
            volume.add((Integer) array.get(i));
        }

        return volume;
    }

    private String getContent(String urlPattern, String stockID) throws IOException {
        String urlPath = "http://" + Connect.IP + "/" + urlPattern + "?stockID=" + stockID;
        URL url = new URL(urlPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));

        return reader.readLine();
    }
}
