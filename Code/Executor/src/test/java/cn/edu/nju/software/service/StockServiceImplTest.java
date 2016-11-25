package cn.edu.nju.software.service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by song on 16-11-25.
 */
public class StockServiceImplTest {

    @Test
    public void getStockInfoByTime() throws Exception {
        StockServiceImpl service = new StockServiceImpl();

        System.out.println(service.getStockInfoByTime("sh601633"));
    }
}