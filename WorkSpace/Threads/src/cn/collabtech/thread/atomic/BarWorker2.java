package cn.collabtech.thread.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class BarWorker2 implements Runnable {
	// 静态变量使用 AtomicBoolean 进行操作
	private static AtomicBoolean exists = new AtomicBoolean(false);
	private String name;

	public BarWorker2(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		if (exists.compareAndSet(false, true)) {
			System.out.println(name + " enter");
			try {
				System.out.println(name + " working");
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// do nothing
			}
			System.out.println(name + " leave");
			exists.set(false);
		} else {
			System.out.println(name + " give up");
		}
	}

	public static void main(String[] args) {
		BarWorker2 bar1 = new BarWorker2("bar1");
		BarWorker2 bar2 = new BarWorker2("bar2");
		new Thread(bar1).start();
		new Thread(bar2).start();
	}
}