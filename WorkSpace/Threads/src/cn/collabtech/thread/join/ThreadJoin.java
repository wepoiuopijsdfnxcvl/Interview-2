package cn.collabtech.thread.join;

public class ThreadJoin {
	
	static Thread thread1 = new Thread(new Runnable() {
		@Override
		public void run() {
			System.out.println("thread1");
		}
	});
	
	static Thread thread2 = new Thread(new Runnable() {
		@Override
		public void run() {
			System.out.println("thread2");
		}
	});
	static Thread thread3 = new Thread(new Runnable() {
		@Override
		public void run() {
			System.out.println("thread3");
		}
	});
	
	public static void main(String[] args) throws InterruptedException {
		//join() 让主线程等待子线程执行完成 （其他子线程也是要靠主线程来start()）
		thread1.start();
		thread1.join();
		thread2.start();
		thread2.join();
		thread3.start();
	}
	
	
	

}
