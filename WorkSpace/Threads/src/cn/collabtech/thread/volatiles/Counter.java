package cn.collabtech.thread.volatiles;

/**
 * @author Darrick_AZ volatile关键字就是提示VM：对于这个成员变量不能保存它的私有拷贝，而应直接与共享成员变量交互。
 *         Volatile修饰的成员变量在每次被线程访问时，都强迫从共享内存中重读该成员变量的值。而且，当成员变量发生变化时，强迫线程将变化值回写到共享内存。这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个值。
 */
public class Counter {

	// volatile关键字去修饰变量的时候，所以线程都会直接读取该变量并且不缓存它
	public volatile static int count = 0;

	public static void inc() {

		try {
			// Thread.sleep(1);
		} catch (Exception e) {
		}

		count++;
		System.out.println(count);
	}

	public static void main(String[] args) {

		// 同时启动1000个线程，去进行i++计算，看看实际结果

		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				public void run() {
					Counter.inc();
				}
			}).start();
		}

		// 这里每次运行的值都有可能不同,可能为1000
		System.out.println("运行结果:Counter.count=" + Counter.count);
	}

}