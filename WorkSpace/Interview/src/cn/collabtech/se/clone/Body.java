package cn.collabtech.se.clone;

/**
 * 测试实现对象的深拷贝 （对象及对象引用的对象都需要实现Cloneable接口）
 * @author Darrick
 * @Date 2019年2月20日
 * @Description
 */
public class Body implements Cloneable {
	public Head head;

	public Body() {
	}

	public Body(Head head) {
		this.head = head;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Body newBody = (Body) super.clone();
		newBody.head = (Head) head.clone();
		return newBody;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		Body body = new Body(new Head(new Face()));
		Body body1 = (Body) body.clone();
		System.out.println("body == body1 : " + (body == body1));
		System.out.println("body.head == body1.head : " + (body.head == body1.head));
	}

}

class Head implements Cloneable {
	public Face face;

	public Head() {
	}

	public Head(Face face) {
		this.face = face;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

class Face implements Cloneable {
	public Face() {
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
