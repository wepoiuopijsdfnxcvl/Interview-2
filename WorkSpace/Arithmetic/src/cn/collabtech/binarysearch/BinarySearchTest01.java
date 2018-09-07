package cn.collabtech.binarysearch;

import cn.collabtech.bubblesort.BubbleSortTest01;

/**
 * @author Darrick_AZ 二分查找 ※：数组是已经排序好的
 */
public class BinarySearchTest01 {

	public static void main(String[] args) {
		int[] tmp = { 1, 5, 6, 3, 2, 4, 7 };
		// 利用二分法查找元素并返回其下标
		System.out.println(searchEle(tmp, 6));
	}

	private static int searchEle(int[] array, int i) {
		if (array.length > 0) {
			// 先进行数组的排序
			int[] newArray = BubbleSortTest01.sort(array);
			// newArray >>> {1,2,3,4,5,6,7}

			int start = 0;
			int end = array.length - 1;
			while (start <= end) {
				int middle = (start + end) / 2;
				if (i < newArray[middle]) {
					end = middle - 1;
				} else if (i > array[middle]) {
					start = middle + 1;
				} else {
					return middle;
				}
			}
		}
		return -1;
	}

}
