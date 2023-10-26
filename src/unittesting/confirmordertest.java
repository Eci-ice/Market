package unittesting;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class confirmordertest {

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
		assertEquals("success",new confirmorderfortesting().confirm("1"));
	}
	
	@Test
	public void test02() {
		assertEquals("该商品无法冻结",new confirmorderfortesting().confirm("-1"));
	}
	@Test
	public void test03() {
		assertEquals("fail2",new confirmorderfortesting().confirm("aaaaa"));
	}
	@Test
	public void test04() {
		assertEquals("fail1",new confirmorderfortesting().confirm(" "));
	}
}
