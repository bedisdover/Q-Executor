package present.panel.progress;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 16-9-2.
 * <p>
 * 测试进度条与SwingWorker结合使用
 */
public class ProgressPanelTest extends JPanel {
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame();

            frame.setBounds(100, 100, 400, 400);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            ProgressPanel progressPanel = new ProgressPanel();
            frame.getContentPane().add(progressPanel);

            frame.setVisible(true);

            TestTask task = new TestTask();
            task.addPropertyChangeListener(evt -> {
                if ("progress".equals(evt.getPropertyName())) {
                    progressPanel.setProgress((Integer) evt.getNewValue() + 1);
                }
            });

            task.execute();
        });
    }


    private static class TestTask extends SwingWorker<List<String>, String> {
        @Override
        protected List<String> doInBackground() throws Exception {
            List<String> result = new ArrayList<>();

            String url = "http://121.42.143.164/StockInfoByPrice?codeNum=sh600000";
            URL ur = new URL(url);
            System.out.println(System.currentTimeMillis());
            InputStream stream = ur.openStream();

            ProgressMonitorInputStream monitor = new ProgressMonitorInputStream(frame, "Loading ", stream);

            InputStream in = new BufferedInputStream(monitor);
            while (in.available() > 0) {
                byte[] data = new byte[38];
                in.read(data);
                System.out.write(data);
            }

            Reader reader1 = new InputStreamReader(stream);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream()));
//        String line = reader.readLine();
            for (int i = 0; i < 100; i++) {
                char[] temp = new char[100];
                reader1.read(temp, 0, 100);
                setProgress(i);
            }
            System.out.println(System.currentTimeMillis());
//        JSONArray jsonArray = new JSONArray(line);

            return result;
        }

        @Override
        protected void done() {
            try {
                System.out.println(get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}