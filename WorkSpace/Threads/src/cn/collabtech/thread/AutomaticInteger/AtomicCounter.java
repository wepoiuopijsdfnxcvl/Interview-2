package cn.collabtech.thread.AutomaticInteger;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
	private AtomicInteger count = new AtomicInteger(0);

	// 使用AtomicInteger之后，不需要加锁，也可以实现线程安全。
	public void increment() {
		// 获取当前的值并自增
		count.incrementAndGet();
	}

	/**
	 * 获取当前的值
	 * 
	 * @return
	 */
	public int getCount() {
		return count.get();
	}

	// 递减
	public void deIncrement() {
		count.decrementAndGet();
	}
}
