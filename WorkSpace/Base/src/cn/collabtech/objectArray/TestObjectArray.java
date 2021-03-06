package cn.collabtech.objectArray;

import java.util.ArrayList;
import java.util.List;

public class TestObjectArray {
	
	public static void main(String[] args) {
		List<Person> persons = new ArrayList();
		Person person1 = new Person("A", 1);
		Person person2 = new Person("B", 1);
		persons.add(person1);
		persons.add(person2);
		System.out.println(persons);
		person1.setName("AA");
		System.out.println(persons);
		
	}	
}


class Person{
	
	private String name;
	private int age;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
}
