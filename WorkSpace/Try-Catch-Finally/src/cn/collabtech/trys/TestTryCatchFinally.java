package cn.collabtech.trys;

public class TestTryCatchFinally {
	public static void main(String[] args) {
		testBasic();
	}
	public static int testBasic() {
		int i = 1;
		try {
			i++;
			int a =2/0;
			System.out.println("try block, i = " + i);
			return i;
		} catch (Exception e) {
			i++;
			System.out.println("catch block i = " + i);
		} finally {
			i = 10;
			System.out.println("finally block i = " + i);
			
		}
		return i;
	}
}
