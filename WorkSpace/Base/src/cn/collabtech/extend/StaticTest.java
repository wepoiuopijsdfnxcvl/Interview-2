package cn.collabtech.extend;

public class StaticTest {
	public static void main(String[] args) {
		M m = new N();
		m.output();
		m.inPut();
	}
}

class M {
	public static void output() {
		System.out.println("M");
	}

	public void inPut() {
		System.out.println("M inPut");
	}
}

class N extends M {
	public static void output() {
		System.out.println("N");
	}

	public void inPut() {
		System.out.println("N inPut");
	}
}
