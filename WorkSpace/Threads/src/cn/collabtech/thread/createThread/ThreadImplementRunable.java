package cn.collabtech.thread.createThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Darrick_AZ 创建线程的方式二 实现Runnable接口
 */
public class ThreadImplementRunable implements Runnable {

	// 总票数
	private int tickets = 30;

	// 新建锁
	Lock lock = new ReentrantLock();

	@Override
	public void run() {
		// for (int i = 0; i < 30; i++) {
		// synchronized (this) {
		// if (tickets > 0) {
		// try {
		// Thread.sleep(100);
		// System.out.println(Thread.currentThread().getName() + "\t正在售票\t" + tickets--
		// + "\t还剩票数\t" + tickets);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }

		for (int i = 0; i < 30; i++) {
			lock.lock();
			if (tickets > 0) {
				try {
					Thread.sleep(100);
					System.out.println(Thread.currentThread().getName() + "\t正在售票\t" + tickets-- + "\t还剩票数\t" + tickets);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
			}
		}
	}

	public static void main(String[] args) {
		ThreadImplementRunable tir = new ThreadImplementRunable();
		Thread t1 = new Thread(tir);
		t1.setName("窗口A");
		Thread t2 = new Thread(tir);
		t2.setName("窗口B");
		Thread t3 = new Thread(tir);
		t3.setName("窗口C");
		t1.start();
		t2.start();
		t3.start();
	}

}
