package cn.collabtech.recursion;

public class FibonacciSequence {
	// 1、1、2、3、5 ... 第30位多少
	public static void main(String[] args) {
		System.out.println(Fribonacci(9));

	}
	public static int Fribonacci(int n) {
		if (n <= 2)
			return 1;
		else
			return Fribonacci(n - 1) + Fribonacci(n - 2);

	}
}