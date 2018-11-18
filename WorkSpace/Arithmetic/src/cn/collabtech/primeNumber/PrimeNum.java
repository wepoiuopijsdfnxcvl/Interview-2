package cn.collabtech.primeNumber;

public class PrimeNum {
	public static void main(String[] args) {
		for (int i = 3; i <= 100; i += 2) {
			boolean isPrime = false;
			for (int j = 3; j <= Math.sqrt(i); j += 2) {
				if (i % j == 0) {
					isPrime = true;
				}
			}
			if (!isPrime) {
				System.out.println(i);
			}
		}
	}
}
