package cn.collabtech.test;

public class Person {

	int i = 0;

	public void add(Person p) {
		p = new Person();
		p.i = 2;
	}

	public static void main(String[] args) {
		Person p = new Person();
		System.out.println(p);
		p.add(p);
		System.out.println(p);
	}

	@Override
	public String toString() {
		return "Person [i=" + i + "]";
	}

}
