package present.panel.progress;

import present.utils.CircleProgressBar;

import javax.swing.*;

/**
 * Created by song on 16-9-2.
 *
 * 进图面板
 */
public class ProgressPanel extends JPanel {
    private CircleProgressBar progressBar;

    public ProgressPanel() {
        createUIComponents();
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            progressBar = new CircleProgressBar();
            add(progressBar);
            this.revalidate();
            this.repaint();
        });
    }

    public void setProgress(int progress) {
        progressBar.setGradualProcess(progress);

        this.repaint();
    }
}
