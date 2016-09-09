import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-9-9.
 * 测试布局
 */
public class GridTest extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GridTest gridTest = new GridTest();
        });
    }

    GridTest() {
        createUIComponents();
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUIComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.add(new JButton("1"));
        constraints.gridwidth = 5;
        constraints.weightx = 0;
        constraints.weighty = 0;
        this.add(panel, constraints);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.blue);
        panel1.add(new JButton("2"));
        constraints.gridwidth = 10;
        constraints.weightx = 0;
        constraints.weighty = 0;
        this.add(panel1, constraints);

        constraints.gridwidth = 0;
        constraints.weightx = 1;
        constraints.weighty = 0;
        this.add(new JPanel(), constraints);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.RED);
        constraints.gridwidth = 10;
        constraints.weightx = 0;
        constraints.weighty = 1;
        this.add(panel2, constraints);

        constraints.gridwidth = 5;
        constraints.weightx = 1;
        constraints.weighty = 1;
        this.add(new JPanel(), constraints);
    }
}
