package cn.collabtech.objclone;

public class TestClone {
	public static void main(String[] args) throws CloneNotSupportedException {
		Person p1 = new Person();
		p1.setAge(31);
		p1.setName("Peter");

		Person p2 = (Person) p1.clone();
		System.out.println(p1 == p2);// false
		p2.setName("Jacky");
		System.out.println("p1=" + p1);// p1=Person [name=Peter, age=31]
		System.out.println("p2=" + p2);// p2=Person [name=Jacky, age=31] }
	}
}
