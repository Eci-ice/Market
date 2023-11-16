package unittesting;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class changepwdtest {

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
		assertEquals("success",new changepwdfortesting().change("123", "123", "123", "100", "100"));
	}
	@Test
	public void test02() {
		assertEquals("旧密码错误",new changepwdfortesting().change("123", "123", "100", "100", "100"));
	}
	@Test
	public void test03() {
		assertEquals("新密码与旧密码一致",new changepwdfortesting().change("123", "123", "123", "123", "123"));
	}
	@Test
	public void test04() {
		assertEquals("新密码与确认密码不一致",new changepwdfortesting().change("123", "123", "123", "110", "100"));
	}
	@Test
	public void test05() {
		assertEquals("fail1",new changepwdfortesting().change("123", "123", "123", " ", " "));
	}
}