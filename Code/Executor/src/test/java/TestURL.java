import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by song on 16-8-24.
 * <p>
 * 测试访问Controller
 */
public class TestURL {
    public static void main(String[] args) {
        String url = "http://121.42.143.164/login";
        try {
            URL ur = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream()));
            String line;
            line = reader.readLine();

            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
