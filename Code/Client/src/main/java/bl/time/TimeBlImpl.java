package bl.time;

import bl.Connect;
import blservice.time.TimeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by song on 16-9-11.
 * <p>
 * 时间相关
 */
public class TimeBlImpl implements TimeService {

    @Override
    public Calendar getCurrentTime() throws IOException {
        String urlPath = "http://" + Connect.IP + "/currentTime";
        URL url = new URL(urlPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        long millis = Long.parseLong(reader.readLine());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return calendar;
    }
}
