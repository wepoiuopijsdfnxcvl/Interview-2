package cn.collabtech.randomnum;

import java.util.Random;

import org.junit.Test;

/**
 * @author Darrick_AZ
 * 测试Random生成随机数
 */
public class TestRandom {

	//Random.next(1) 生成[0,1)的随机整数
	
	@Test
	public void testRandom01() {
		Random rm = new Random();
		int nextInt = rm.nextInt(2);
		System.out.println(nextInt);
	}
	
	@Test
	public void testRandom02() {
		Random rm = new Random();
		 float nextFloat = rm.nextFloat();
		System.out.println(nextFloat);
	}
	
}
