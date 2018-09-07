package cn.collabtech.thread.createThread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Darrick_AZ 创建线程的方式三 实现Callable接口
 */
public class ThreadImplementCallable implements Callable<Integer> {

	int ticket = 30;
	Lock lock = new ReentrantLock();

	@Override
	public Integer call() throws Exception {
		lock.lock();
		try {
			for (int i = 1; i <= 20; i++) {
				while (ticket > 0) {
					Thread.sleep(200);
					System.out.println(Thread.currentThread().getName() + "\t售票\t" + ticket-- + "\t还剩\t" + ticket);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return new Random().nextInt(10);
	}

	public static void main(String[] args) {
		ThreadImplementCallable tic = new ThreadImplementCallable();
		new Thread(new FutureTask<>(tic), "窗口A").start();
		new Thread(new FutureTask<>(tic), "窗口B").start();
		new Thread(new FutureTask<>(tic), "窗口C").start();
	}

}
