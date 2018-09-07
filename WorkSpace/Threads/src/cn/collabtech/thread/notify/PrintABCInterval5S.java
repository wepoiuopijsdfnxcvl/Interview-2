package cn.collabtech.thread.notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Darrick_AZ 3个线程分别打印五次A B C
 */
public class PrintABCInterval5S {

	// 每个线程打印的总数
	int count = 5;

	// 可重入锁
	Lock lock = new ReentrantLock();
	Condition conditionA = this.lock.newCondition();
	Condition conditionB = this.lock.newCondition();
	Condition conditionC = this.lock.newCondition();

	class PrintA implements Runnable {
		@Override
		public void run() {
			try {
				lock.lock();
				for (int i = 0; i < 5; i++) {
					Thread.sleep(500);
					System.out.println("A");
				}
				conditionB.signal();
				conditionA.await();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}

		}
	}

	class PrintB implements Runnable {
		@Override
		public void run() {
			try {
				lock.lock();
				for (int i = 0; i < 5; i++) {
					Thread.sleep(500);
					System.out.println("B");
				}
				conditionC.signal();
				conditionB.await();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}

		}
	}

	class PrintC implements Runnable {
		@Override
		public void run() {
			try {
				lock.lock();
				for (int i = 0; i < 5; i++) {
					Thread.sleep(500);
					System.out.println("C");
				}
				conditionA.signal();
				conditionC.await();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}

		}
	}

	public static void main(String[] args) {
		PrintABCInterval5S pi = new PrintABCInterval5S();
		new Thread(pi.new PrintA()).start();
		new Thread(pi.new PrintB()).start();
		new Thread(pi.new PrintC()).start();
	}
}
