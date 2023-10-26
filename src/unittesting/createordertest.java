package unittesting;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class createordertest {

    @BeforeClass
    public static void setUBeforeClassp() throws Exception {
        System.out.println("总测试开始");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("总测试结束");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("单测试开始");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("单测试结束");
    }

    @Test
    public void testNormalCase() {
        createorderfortesting createOrder = new createorderfortesting();
        String result = createOrder.create("浙江工商大学", "11111111111", "猫粮大哥", "1");
        assertEquals("success", result);
    }

    @Test
    public void testNonExistentGood() {
        createorderfortesting createOrder = new createorderfortesting();
        String result = createOrder.create("浙江工商大学", "11111111111", "猫粮大哥", "10000000");
        assertEquals("fail7", result);
    }

    @Test
    public void testEmptyInput() {
        createorderfortesting createOrder = new createorderfortesting();
        String result = createOrder.create("", "11111111111", "猫粮大哥", "3");
        assertEquals("fail1", result);
    }

    @Test
    public void testInvalidPhone() {
        createorderfortesting createOrder = new createorderfortesting();
        String result = createOrder.create("浙江工商大学", "555-1234", "猫粮大哥", "3");
        assertEquals("fail7", result);
    }

    @Test
    public void testInvalidGoodID() {
        createorderfortesting createOrder = new createorderfortesting();
        String result = createOrder.create("浙江工商大学", "11111111111", "猫粮大哥", "abc");
        assertEquals("fail5", result);
    }

    @Test
    public void testDuplicateOrder() {
        createorderfortesting createOrder = new createorderfortesting();

        // 创建一个订单
        String result1 = createOrder.create("浙江工商大学", "22222222222", "猫粮大哥", "1");
        assertEquals("success", result1);

        // 尝试使用相同的参数再次创建订单
        String result2 = createOrder.create("浙江工商大学", "22222222222", "猫粮大哥", "1");
        assertEquals("fail8", result2);
    }
}

