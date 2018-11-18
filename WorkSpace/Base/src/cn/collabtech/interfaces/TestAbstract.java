package cn.collabtech.interfaces;

public abstract class TestAbstract implements ITestInterface {
	// 找出接口中必要的方法，也就是子类必须实现的方法，定义成抽象方法，交由子类实现
	public abstract void method1();

	public abstract int method2();

	// 一些独特的方法可以在抽象类中默认实现
	public boolean method3() {
		return true;
	}
}