package present.utils;

import org.json.JSONException;
import org.json.JSONObject;
import present.component.TipText;
import util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/29.
 *
 * 股票json文件的一些基本信息
 */
public class StockJsonInfo {

    public static final String JSON_PATH = "src/main/resources/file/basicInfo.json";

    public static final List<String> JSON_KEYS = new ArrayList<>();

    public static final String KEY_CODE = "code";

    public static final String KEY_NAME = "name";

    public static final String KEY_INDUSTRY = "industry";

    static {
        JSON_KEYS.add(KEY_CODE);
        JSON_KEYS.add(KEY_NAME);
        JSON_KEYS.add(KEY_INDUSTRY);
    }
}
