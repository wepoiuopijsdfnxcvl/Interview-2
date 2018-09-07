package cn.collabtech.bubblesort;

import java.util.Arrays;

/**
 * @author Darrick_AZ 冒泡排序
 */
public class BubbleSortTest01 {

	public static void main(String[] args) {
		int[] tmp = { 1, 5, 6, 3, 2, 4, 7 };
		int[] newArray = sort(tmp);
		System.out.println(Arrays.toString(newArray));
	}

	public static int[] sort(int[] array) {
		/**
		 * 开始排序
		 */
		// 外层循环是代表总共要比较多少次
		for (int i = 0; i < array.length - 1; i++) {
			// 内层循环则是代表每个内循环元素比较的次数 并且进行交换
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j] > array[j + 1]) {
					int temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
		return array;
	}

}
