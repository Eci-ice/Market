package unittesting;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class creategoodtest {

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
		assertEquals("fail1",new creategoodfortesting().create(" ", " ", " ", " "));//无判断 非空
		
	}
	@Test
	public void test02() {
		assertEquals("success",new creategoodfortesting().create("Abc", "Good", "1.1", "./xx.png"));
	}
	//./img/buyer/food-1.jpg
	@Test
	public void test03() {
		assertEquals("fail",new creategoodfortesting().create("猫粮", "好猫粮", "1.1", "aaaa"));
		
	}
	@Test
	public void test04() {
		assertEquals("fail1",new creategoodfortesting().create("Abc猫粮", "Good猫粮", "a", "./xx.png"));//无判断  
		
	}
	@Test
	public void test05() {
		assertEquals("fail",new creategoodfortesting().create("123!@#", "123!@#", "1.1", "./xx.png"));
		
	}
}