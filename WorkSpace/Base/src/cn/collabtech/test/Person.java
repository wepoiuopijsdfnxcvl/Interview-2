package cn.collabtech.test;

public class Person {

	int i = 0;

	public static void test() {
		System.out.println("ss");
	}

	public void add(Person p) {
		p = new Person();
		p.i = 2;
	}

	public static void main(String[] args) {
		Person p = new Person();
		System.out.println(p);
		p.add(p);
		System.out.println(p);

		int i = 1, j = 10;
		do {
			if (i++ > --j)
				continue;
		} while (i < 5);
		System.out.println(i + " - " + j);
	}

	@Override
	public String toString() {
		return "Person [i=" + i + "]";
	}

}
