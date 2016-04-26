package cn.edu.gdut.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/applicationContext.xml"})

public class TestBase {

	private static Object object = new Object();
	@Test
	public void testA(){
		synchronized (object) {
			testB();
		}
	}
	
	private void testB(){
		synchronized (object) {
			System.out.println("yes i am in");
		}
	}
}
