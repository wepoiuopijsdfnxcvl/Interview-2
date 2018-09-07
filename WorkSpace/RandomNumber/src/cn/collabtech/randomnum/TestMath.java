package cn.collabtech.randomnum;
/**
 * @author Darrick_AZ
 * 测试Math
 */

import org.junit.Test;

public class TestMath {

	@Test
	public void testMath01() {
		double random = Math.random();
		System.out.println(random);
		//生成1~10的整数
		System.out.println((int)(random*10)+1);
	}
	
	
}
