package cn.collabtech.thread.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了。
 * CountDownLatch类只提供了一个构造器： publicCountDownLatch(intcount) { };//参数count为计数值
 * 然后下面这3个方法是CountDownLatch类中最重要的方法： 1. public void await() throws
 * InterruptedException { };//调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行 2. public
 * boolean await( longtimeout, TimeUnit unit)throws InterruptedException {
 * };//和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行 3. public void countDown() {
 * };//将count值减1
 */
public class Test {
	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(2);
		new Thread() {
			public void run() {
				try {
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(3000);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		new Thread() {
			public void run() {
				try {
					System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(3000);
					System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
		try {
			System.out.println("等待2个子线程执行完毕...");
			// 只有当countDown的数量减少为0才会唤醒继续回到主线程
			latch.await();
			System.out.println("2个子线程已经执行完毕");
			System.out.println("继续执行主线程");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}