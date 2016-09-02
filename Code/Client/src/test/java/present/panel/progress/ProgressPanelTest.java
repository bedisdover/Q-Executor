package present.panel.progress;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 16-9-2.
 * <p>
 * 测试进度条与SwingWorker结合使用
 */
public class ProgressPanelTest extends JPanel {
    public static void main(String[] args) {
        TestTask task = new TestTask();
        task.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                System.out.println(evt.getNewValue());
            }
        });
        task.execute();
        System.out.println("ProgressPanelTest.main");

        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                System.out.println("sdfa.....");
                return null;
            }
        };

        worker.execute();
    }
}

class TestTask extends SwingWorker<List<String>, String> {
    @Override
    protected List<String> doInBackground() throws Exception {
        System.out.println("TestTask.doInBackground");
        List<String> result = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            result.add((i + 1) + "");
            publish((i + 1) + "");
            Thread.sleep(1000);
        }

        return result;
    }

    @Override
    protected void process(List<String> chunks) {
        System.out.println(chunks.get(0));
    }

    @Override
    protected void done() {
        try {
            System.out.println("TestTask.done");
            System.out.println(get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}