package bl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by song on 16-9-5.
 * <p>
 * 服务器连接服务类
 */
public class Connect {
    public static final String IP;
    public static final String date;
    public static final int start;
    public static final int end;
    
    static {
        Properties properties = new Properties();
        try {
            InputStream in = Connect.class.getClassLoader().getResourceAsStream("db.properties");

            properties.load(in);
        } catch (IOException e) {
            /*do nothing*/
        }

        IP = properties.getProperty("ip");
        date = properties.getProperty("date");
        start = Integer.parseInt(properties.getProperty("start"));
        end = Integer.parseInt(properties.getProperty("end"));
    }
}
