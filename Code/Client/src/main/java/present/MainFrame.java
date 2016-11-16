package present;

import present.panel.account.AccountPanel;
import present.panel.introduce.IntroPanel;
import present.panel.stock.SearchPanel;
import present.panel.trade.TradePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Y481L on 2016/8/25.
 *
 * 软件主窗体
 */
public class MainFrame extends JFrame{

    public static final int PANEL_W = 1100;

    public static final int PANEL_H = 680;

    private static final int MENU_W = 100;

    private static final int MENU_H = 48;

    private static final Font font = new Font("微软雅黑", Font.PLAIN, 16);

    public MainFrame() {

        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//            String[] fonts = GraphicsEnvironment.
//                    getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//            for(String s : fonts) System.out.println(s);
        }
        catch (Exception e) {
            System.out.println("Look and feel Exception!");
        }

        this.addComponents();
        this.setAttributes();
    }

    private void setAttributes() {
        this.setIconImage(new ImageIcon("src/main/resources/images/icon.png").getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setFrameAtCenter();
        setMinimumSize(new Dimension(PANEL_W, PANEL_H + MENU_H));
        this.setVisible(true);
    }

    private void addComponents() {
        //内容面板
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        container.setLayout(new BorderLayout());
        PanelSwitcher switcher = new PanelSwitcher(container);
        IntroPanel current = new IntroPanel();
        switcher.setCurrent(current);
        container.add(current);
        this.add(container, BorderLayout.CENTER);

        //导航栏
        this.createNavigator(switcher);
    }

    /**
     * 创建导航栏
     */
    private void createNavigator(PanelSwitcher switcher) {
        //导航栏
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(0xeee7d9));

        //图标面板
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);
        JLabel logo = new JLabel(new ImageIcon("src/main/resources/images/logo.png"));
        logo.setPreferredSize(new Dimension(MENU_W, MENU_H));
        logoPanel.add(logo);
        bar.add(logoPanel, BorderLayout.WEST);

        //菜单面板
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        menuPanel.setOpaque(false);
        bar.add(menuPanel, BorderLayout.CENTER);

        //交易
        JButton trade = this.createButton("交易");
        trade.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                switcher.jump(new TradePanel());
            }
        });
        menuPanel.add(trade);

        //股票
        JButton stock = this.createButton("股票");
        stock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                switcher.jump(new SearchPanel(switcher));
            }
        });
        menuPanel.add(stock);

        //简介
        JButton introduce = this.createButton("简介");
        introduce.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                switcher.jump(new IntroPanel());
            }
        });
        menuPanel.add(introduce);

        //账户账单
        JButton account = this.createButton("账户");
        account.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                switcher.jump(new AccountPanel(switcher));
            }
        });
        menuPanel.add(account);

        this.add(bar, BorderLayout.NORTH);
    }

    private JButton createButton(String name) {
        JButton btn = new JButton(name);
        btn.setContentAreaFilled(false);
        btn.setForeground(new Color(0xf4c400));
        btn.setFont(font);
        btn.setPreferredSize(new Dimension(MENU_W, MENU_H));
        return btn;
    }

    private void setFrameAtCenter() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        Dimension frame = this.getPreferredSize();
        this.setLocation((screen.width - frame.width) >> 1,
                (screen.height - frame.height) >> 1);
    }
}
