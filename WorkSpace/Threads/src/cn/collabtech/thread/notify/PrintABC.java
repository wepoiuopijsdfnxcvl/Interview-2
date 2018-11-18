package cn.collabtech.thread.notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Darrick_AZ 测试3个线程分别打印ABC
 */
public class PrintABC {

	// 打印的总数
	int count = 0;
	Lock lock = new ReentrantLock();
	Condition conditionA = this.lock.newCondition();
	Condition conditionB = this.lock.newCondition();
	Condition conditionC = this.lock.newCondition();

	class PrintA implements Runnable {
		@Override
		public void run() {
			while (true)
				if (count < 15) {
					try {
						lock.lock();
						System.out.print("A");
						Thread.sleep(500);
						conditionB.signal(); // 线程b唤醒,因为a打印完应该打印b
						conditionA.await(); // 线程a进入等待队列
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
		}
	}

	class PrintB implements Runnable {
		@Override
		public void run() {
			while (true)
				if (count < 15) {
					try {
						lock.lock();
						System.out.print("B");
						Thread.sleep(500);
						conditionC.signal(); 
						conditionB.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}

				}
		}
	}

	class PrintC implements Runnable {
		@Override
		public void run() {
			while (true)
				if (count < 15) {
					try {
						lock.lock();
						Thread.sleep(500);
						System.out.println("C" + count);
						count++;// 打印完c后,count++
						conditionA.signal();
						conditionC.await(); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
		}
	}

	public static void main(String[] args) {
		PrintABC printABCD = new PrintABC();
		new Thread(printABCD.new PrintA()).start();
		new Thread(printABCD.new PrintB()).start();
		new Thread(printABCD.new PrintC()).start();
	}

}
