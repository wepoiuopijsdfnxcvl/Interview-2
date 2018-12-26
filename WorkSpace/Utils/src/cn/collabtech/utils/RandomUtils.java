package cn.collabtech.utils;

import java.util.Random;

public class RandomUtils {
	public static void main(String[] args) {
		Random random = new Random();
		int nextInt = random.nextInt(10);
		System.out.println(nextInt);

		System.out.println(getRandom(12));
	}

	/**
	 * 生成指定位数的随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandom(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}
}
