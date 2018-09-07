package cn.collabtech.thread.createThread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Darrick_AZ 创建线程的方式四 使用线程池
 */
public class ThreadExecutorsThreadPoll {

	public static void main(String[] args) {
		// ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
		// ScheduledFuture<Integer> result = null;
		//
		// try {
		// for (int i = 0; i <= 20; i++) {
		// result = service.schedule(new Callable<Integer>() {
		// @Override
		// public Integer call() throws Exception {
		// Thread.sleep(500);
		// System.out.println(Thread.currentThread().getName());
		//
		// return new Random().nextInt(30);
		// }
		//
		// }, 3, TimeUnit.SECONDS);
		// }
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// service.shutdown();
		// }

		// ######################################################################################
		// 一池里面有固定了5个干活的线程
		// ExecutorService threadPool = Executors.newFixedThreadPool(5);
		// ExecutorService threadPool = Executors.newSingleThreadExecutor();
		ExecutorService threadPool = Executors.newCachedThreadPool();
		Future<Integer> result = null;
		try {
			for (int i = 1; i <= 30; i++) {
				result = threadPool.submit(new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						Thread.sleep(500);
						System.out.print(Thread.currentThread().getName());
						return new Random().nextInt(30);
					}
				});
				System.out.println("-----: " + result.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}

	}

}
