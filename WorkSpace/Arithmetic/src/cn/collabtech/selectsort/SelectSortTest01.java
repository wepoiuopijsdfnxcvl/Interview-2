package cn.collabtech.selectsort;

import java.util.Arrays;

/**
 * @author Darrick_AZ 选择排序 每次比较出最大或最小的值 保存其下标 并把它放在相应的位置 冒泡排序 每次都进行相邻的比较如果
 *         大于或者小于则进行交换
 */
public class SelectSortTest01 {

	public static void main(String[] args) {
		int[] array = new int[] { 1, 4, 3, 5, 7, 6, 2 };
		System.out.println(Arrays.toString(selectSort(array)));
	}

	public static int[] selectSort(int[] array) {
		if (array != null && array.length > 0) {

			for (int i = 0; i < array.length - 1; i++) {
				int index = i;
				// 每个数都要比较
				for (int j = index + 1; j < array.length; j++) {
					// 记录最小的位置
					if (array[j] < array[index]) {
						index = j;
					}
				}
				if (index != i) {
					int temp = array[i];
					array[i] = array[index];
					array[index] = temp;
				}

			}

		}
		return array;
	}

}
