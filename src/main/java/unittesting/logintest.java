package unittesting;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class logintest {

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
		assertEquals("success1",new loginfortesting().login("123", "123"));
	}
	
	@Test
	public void test02() {
		assertEquals("success2",new loginfortesting().login("111", "111"));
	}
	@Test
	public void test03() {
		assertEquals("用户名错误或不存在",new loginfortesting().login("110", "111"));
	}
	@Test
	public void test04() {
		assertEquals("密码错误！！！",new loginfortesting().login("123", "111"));
	}
	
}