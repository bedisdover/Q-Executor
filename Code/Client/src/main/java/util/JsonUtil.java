package util;

import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Y481L on 2016/8/29.
 *
 * Json 解析工具类
 */
public class JsonUtil {

    /**
     * 将json文件解析，找出包含所给关键字的json对象
     * @param jsonKeys json对象所包含的关键字列表
     * @param fileName json文件路径
     * @param key 所要匹配的关键字
     * @return json对象列表
     */
    public static List<JSONObject> contains(List<String> jsonKeys, String fileName, String key) {
        String json = getString(fileName);
        List<JSONObject> result = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            int length = array.length();
            Object item;
            for(int i = 0; i < length; ++i) {
                item = array.get(i);
                if (isJSonArray(item)) {
                    result.addAll(contains(jsonKeys, item.toString(), key));
                }else {
                    for (String k : jsonKeys) {
                        JSONObject obj = (JSONObject) item;
                        if(obj.getString(k).contains(key)) {
                            result.add(obj);
                            break;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param srcKey
     * @param targetKey
     * @param key
     * @param filepath
     * @return
     */
    public static String confirm(String srcKey, String targetKey, String key, String filepath) {
        Vector<String> keys = new Vector<>();
        keys.add(srcKey);
        keys.add(targetKey);
        List<JSONObject> list = contains(keys, filepath, key);
        try {
            return list.get(0).getString(targetKey);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
//        String result = null;
//        String file = getString(filepath);
//        try {
//            JSONArray array = new JSONArray(file);
//            int length = array.length();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return result;
    }

    /**
     * 将json文件转化成字符串
     * @param filename 文件名
     * @return json文件内容转化而成的字符串
     */
    private static String getString(String filename) {
        StringBuilder result = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while(scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return new String(result);
    }

    private static boolean isJSonArray(Object obj) {
        return obj.getClass() == JSONArray.class;
    }
}
