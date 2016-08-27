package present.panel.stock;

import javax.swing.*;
import java.awt.*;

/**
 * Created by song on 16-8-26.
 * <p>
 * 股票基本信息
 */
class BasicInfoPanel extends JPanel {

    private JPanel panel;

    private MyLabel labelName, labelIndustry, labelArea, labelLiquid,
            labelTotal, labelTotalAssets, labelLiquidAssets, labelFixedAssets,
            labelReserved, labelReservedPerShare, labelEps, labelBvps, labelTimeToMarket;

    private MyLabel name, industry, area, liquid, total, totalAssets, liquidAssets,
            fixedAssets, reserved, reservedPerShare, eps, bvps, timeToMarket;

    BasicInfoPanel() {
        panel = this;

        init();
        createUIComponents();
    }

    private void init() {
        SwingUtilities.invokeLater(() -> {
            panel.setBackground(new Color(0xcccccc));
            panel.setLayout(new BorderLayout());

            panel.revalidate();
        });
    }

    private void createUIComponents() {
        SwingUtilities.invokeLater(() -> {
            {
                Box westBox = Box.createVerticalBox();

                labelName = new MyLabel("公司名称");
                labelIndustry = new MyLabel("股票类型");
                labelArea = new MyLabel("公司创建地");
                labelLiquid = new MyLabel("流通股本");
                labelTotal = new MyLabel("总股本");
                labelTotalAssets = new MyLabel("总资产");
                labelLiquidAssets = new MyLabel("流动资产");
                labelFixedAssets = new MyLabel("固定资产");
                labelReserved = new MyLabel("公积金");
                labelReservedPerShare = new MyLabel("每股公积金");
                labelEps = new MyLabel("每股收益");
                labelBvps = new MyLabel("每股净资");
                labelTimeToMarket = new MyLabel("上市时间");

                westBox.add(labelName);
                westBox.add(labelIndustry);
                westBox.add(labelArea);
                westBox.add(labelLiquid);
                westBox.add(labelTotal);
                westBox.add(labelTotalAssets);
                westBox.add(labelLiquidAssets);
                westBox.add(labelFixedAssets);
                westBox.add(labelReserved);
                westBox.add(labelReservedPerShare);
                westBox.add(labelEps);
                westBox.add(labelBvps);
                westBox.add(labelTimeToMarket);

                panel.add(westBox, BorderLayout.WEST);
            }

            {
                Box eastBox = Box.createVerticalBox();

                name = new MyLabel("浦发银行");
                industry = new MyLabel("金融行业");
                area = new MyLabel("上海");
                liquid = new MyLabel("6.00亿");
                total = new MyLabel("7.67亿");
                totalAssets = new MyLabel("98.00亿");
                liquidAssets = new MyLabel("46.34亿");
                fixedAssets = new MyLabel("53.23亿");
                reserved = new MyLabel("2345元");
                reservedPerShare = new MyLabel("0.10元");
                eps = new MyLabel("0.11元");
                bvps = new MyLabel("1.64元");
                timeToMarket = new MyLabel("2011-04-26");

                eastBox.add(name);
                eastBox.add(industry);
                eastBox.add(area);
                eastBox.add(liquid);
                eastBox.add(total);
                eastBox.add(totalAssets);
                eastBox.add(liquidAssets);
                eastBox.add(fixedAssets);
                eastBox.add(reserved);
                eastBox.add(reservedPerShare);
                eastBox.add(eps);
                eastBox.add(bvps);
                eastBox.add(timeToMarket);

                panel.add(eastBox, BorderLayout.EAST);
            }
        });
    }
}
