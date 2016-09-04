package present.panel.stock;

import util.NumberUtil;
import vo.StockBasicInfoVO;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            panel.setBackground(new Color(0xeeeeee));
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

                name = new MyLabel(" -- ");
                industry = new MyLabel(" -- ");
                area = new MyLabel(" -- ");
                liquid = new MyLabel(" -- ");
                total = new MyLabel(" -- ");
                totalAssets = new MyLabel(" -- ");
                liquidAssets = new MyLabel(" -- ");
                fixedAssets = new MyLabel(" -- ");
                reserved = new MyLabel(" -- ");
                reservedPerShare = new MyLabel(" -- ");
                eps = new MyLabel(" -- ");
                bvps = new MyLabel(" -- ");
                timeToMarket = new MyLabel(" -- ");

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

                panel.add(eastBox, BorderLayout.CENTER);
            }
        });
    }

    /**
     * 设置基本信息
     *
     * @param stockBasicInfoVO 基本信息对象
     */
    void setBasicInfo(StockBasicInfoVO stockBasicInfoVO) {
        SwingUtilities.invokeLater(() -> {
            name.setText(stockBasicInfoVO.getName());
            industry.setText(stockBasicInfoVO.getIndustry());
            area.setText(stockBasicInfoVO.getArea());
            liquid.setText(NumberUtil.transferUnit(stockBasicInfoVO.getOutstanding() * 1e4));
            total.setText(NumberUtil.transferUnit(stockBasicInfoVO.getTotals() * 1e4));
            totalAssets.setText(NumberUtil.transferUnit(stockBasicInfoVO.getTotalAssets() * 1e4));
            liquidAssets.setText(NumberUtil.transferUnit(stockBasicInfoVO.getLiquidAssets() * 1e4));
            fixedAssets.setText(NumberUtil.transferUnit(stockBasicInfoVO.getFixedAssets() * 1e4));
            reserved.setText(NumberUtil.transferUnit(stockBasicInfoVO.getReserved() * 1e4));
            reservedPerShare.setText(NumberUtil.transferUnit(stockBasicInfoVO.getReservedPerShare() * 1e4));
            eps.setText(NumberUtil.transferUnit(stockBasicInfoVO.getEps()));
            bvps.setText(NumberUtil.transferUnit(stockBasicInfoVO.getBvps()));
            timeToMarket.setText(getDate(stockBasicInfoVO.getTimeToMarket()));

            panel.revalidate();
            panel.repaint();
        });
    }

    /**
     * 转换时间
     *
     * @return 日期格式： yyyy/MM/dd
     */
    private String getDate(Date date) {
        if (date == null) {
            return "  --  ";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        return dateFormat.format(date);
    }
}
