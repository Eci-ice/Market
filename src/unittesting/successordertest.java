package unittesting;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class successordertest {

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
	public void test01() {
		assertEquals("success",new successorderfortesting().success("1"));
	}
	
	@Test
	public void test02() {
		assertEquals("订单ID无效",new successorderfortesting().success("-1"));
	}
	@Test
	public void test03() {
		assertEquals("订单ID无效",new successorderfortesting().success("0"));
	}
	@Test
	public void test04() {
		assertEquals("订单ID不能为空",new successorderfortesting().success(" "));
	}
}
