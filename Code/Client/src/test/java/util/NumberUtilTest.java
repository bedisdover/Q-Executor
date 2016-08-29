package util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by song on 16-8-29.
 *
 * 测试NumberUtil
 */
public class NumberUtilTest {
    @Test
    public void transferUnit() throws Exception {
        assertEquals(NumberUtil.transferUnit(34510.23, 1), "3.5万");
        assertEquals(NumberUtil.transferUnit(34510.23, 2), "3.45万");
        assertEquals(NumberUtil.transferUnit(34510.23, 3), "3.451万");
        assertEquals(NumberUtil.transferUnit(34510.23, 8), "3.451023万");
        assertEquals(NumberUtil.transferUnit(2189342134.32, 2), "21.89亿");
    }
}