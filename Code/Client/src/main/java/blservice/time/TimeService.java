package blservice.time;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by song on 16-9-11.
 * <p>
 * 时间相关
 */
public interface TimeService {
    /**
     * 获取服务器时间
     *
     * @throws IOException 网络传输异常
     */
    Calendar getCurrentTime() throws IOException;
}
