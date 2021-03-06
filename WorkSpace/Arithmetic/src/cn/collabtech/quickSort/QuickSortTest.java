package cn.collabtech.quickSort;

public class QuickSortTest {
	static int count = 0;

	public static void main(String[] args) {
		int values[] = { 5, 4, 8, 3, 7, 2, 1, 9, 0, 6 };
		sort(values, 0, (values.length - 1));
		System.out.printf("\n\n排序后的结果是：");
		for (int i = 0; i < values.length; i++) {
			System.out.printf("%d ", values[i]);
		}
	}

	public static void sort(int a[], int left, int right) {
		if (left >= right)/* 如果左边索引大于或者等于右边的索引就代表已经整理完成一个组了 */
		{
			return;
		}
		int i = left;
		int j = right;
		int key = a[left];

		while (i < j) /* 控制在当组内寻找一遍 */
		{
			while (i < j && key <= a[j])
			/*
			 * 而寻找结束的条件就是，1，找到一个小于或者大于key的数（大于或小于取决于你想升 序还是降序）2，没有符合条件1的，并且i与j的大小没有反转
			 */
			{
				j--;/* 向前寻找 */
			}

			a[i] = a[j];
			/*
			 * 找到一个这样的数后就把它赋给前面的被拿走的i的值（如果第一次循环且key是 a[left]，那么就是给key）
			 */

			while (i < j && key >= a[i])
			/*
			 * 这是i在当组内向前寻找，同上，不过注意与key的大小关系停止循环和上面相反， 因为排序思想是把数往两边扔，所以左右两边的数大小与key的关系相反
			 */
			{
				i++;
			}

			a[j] = a[i];
		}

		a[i] = key;/* 当在当组内找完一遍以后就把中间数key回归 */
		sort(a, left, i - 1);/* 最后用同样的方式对分出来的左边的小组进行同上的做法 */
		sort(a, i + 1, right);/* 用同样的方式对分出来的右边的小组进行同上的做法 */
		/* 当然最后可能会出现很多分左右，直到每一组的i = j 为止 */
	}
	/**
	 * 快速排序简单的说就是选择一个基准，将比起大的数放在一边，小的数放到另一边。对这个数的两边再递归上述方法。
	 * 
	 * 如本题
	 * 
	 * 66 13 51 76 81 26 57 69 23，以66为基准，升序排序的话，比66小的放左边，比66大的放右边， 类似这种情况 13 。。。
	 * 66。。。69
	 * 
	 * 具体快速排序的规则一般如下：
	 * 
	 * 从右边开始查找比66小的数，找到的时候先等一下，再从左边开始找比66大的数，将这两个数借助66互换一下位置，继续这个过程直到两次查找过程碰头。
	 * 
	 * 例子中：
	 * 
	 * 66 13 51 76 81 26 57 69 23
	 * 
	 * 从右边找到23比66小，互换
	 * 
	 * 23 13 51 76 81 26 57 69 66
	 * 
	 * 从左边找到76比66大，互换
	 * 
	 * 23 13 51 66 81 26 57 69 76
	 * 
	 * 继续从右边找到57比66小，互换
	 * 
	 * 23 13 51 57 81 26 66 69 76
	 * 
	 * 从左边查找，81比66大，互换
	 * 
	 * 23 13 51 57 66 26 81 69 76
	 * 
	 * 从右边开始查找26比66小，互换
	 * 
	 * 23 13 51 57 26 66 81 69 76
	 * 
	 * 从左边开始查找，发现已经跟右边查找碰头了，结束，第一堂排序结束
	 */
}
