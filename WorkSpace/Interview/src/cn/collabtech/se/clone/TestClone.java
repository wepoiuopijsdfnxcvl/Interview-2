package cn.collabtech.se.clone;

/**
 * 测试对象的深拷贝和浅拷贝
 * @author Darrick
 * @Date 2019年2月20日
 * @Description
 */
public class TestClone {

	public static void main(String[] args) throws CloneNotSupportedException {
		// 复制引用
		Person p1 = new Person(23, "zhang");
		Person p2 = p1;
		System.out.println(p1);// cn.collabtech.se.clone.Person@67b64c45
		System.out.println(p2);// cn.collabtech.se.clone.Person@67b64c45

		// 复制对象
		Person p3 = new Person(23, "zhang");
		Person p4 = (Person) p3.clone();
		System.out.println(p3);// cn.collabtech.se.clone.Person@4411d970
		System.out.println(p4);// cn.collabtech.se.clone.Person@6442b0a6
		/**
		 * 如果两个 Person 对象的 name 的地址值相同， 说明两个对象的 name 都指向同一个 String 对象，也就是浅拷贝， 而如果两个对象的
		 * name 的地址值不同， 那么就说明指向不同的 String 对象， 也就 是在拷贝 Person 对象的时候， 同时拷贝了 name 引用的
		 * String 对象， 也就是深拷贝
		 */
		String result = p3.getName() == p4.getName() ? "clone 是浅拷贝的" : "clone 是深拷贝的";
		System.out.println(result);
	}

}
