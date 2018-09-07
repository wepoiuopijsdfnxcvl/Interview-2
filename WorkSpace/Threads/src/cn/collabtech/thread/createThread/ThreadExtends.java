package cn.collabtech.thread.createThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Darrick_AZ 继承Thread类的方式
 * 
 *         线程 操作 资源 synchronized要保证锁是唯一的
 * 
 *         lock 或者 synchronized一定要在循环里边 要不然 会造成一个线程进去之后把死循环了 把资源全部操作完成
 */
public class ThreadExtends extends Thread {

	private int tickets = 100;
	private Lock lock = new ReentrantLock();
	private Object obj = new Object();

	@Override
	public void run() {
		while (true) {
			lock.lock();// 这里也可以使用synchronized(obj){if中的代码} 要使用同一把锁
			if (tickets > 0) {
				try {
					Thread.sleep(100);// 休息一会
					System.out.println(
							Thread.currentThread().getName() + "\t正在卖第\t" + tickets-- + "张票" + "\t还剩" + tickets + "票");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();// 释放锁
				}
			}
		}

		// for (int i = 1; i <= 100; i++) {
		// synchronized (obj) {
		// if (tickets > 0) {
		// System.out.println(Thread.currentThread().getName() + "\t售票\t" + tickets-- +
		// "\t还剩\t" + tickets);
		// }
		// }
		// }
	}

	public static void main(String[] args) {
		ThreadExtends te = new ThreadExtends();
		Thread t1 = new Thread(te);
		Thread t2 = new Thread(te);
		Thread t3 = new Thread(te);
		t1.setName("窗口A");
		t2.setName("窗口B");
		t3.setName("窗口C");
		t1.start();
		t2.start();
		t3.start();
	}

}
