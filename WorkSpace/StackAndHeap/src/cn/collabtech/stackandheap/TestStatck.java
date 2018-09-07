package cn.collabtech.stackandheap;

public class TestStatck {
	
	public static void main(String[] args) {
		Person p1 = new Person();
		p1.setName("A");
		p1.setAge(1);
		System.out.println(p1);
		Person p2 = p1;
		System.out.println(p2);
		p1.setAge(11);
		System.out.println(p2);
		
		System.out.println(p1==p2);
	}
}
