package cn.collabtech.thread.createThread;

/**
 * @author Darrick_AZ 线程之间的通信 实现交替存钱
 */
public class ThreadCommunication {

	// 余额
	private int balance;

	// 存钱
	public synchronized void addMoneny(int my) {

		if (my > 0) {
			notify();
			balance += my;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "存钱。存钱以后，:当前的余额为：" + balance);
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class Customer extends Thread {
		ThreadCommunication acct;

		public Customer(ThreadCommunication acct) {
			this.acct = acct;
		}

		@Override
		public void run() {
			// 每人存款3次，每次1000
			for (int i = 0; i < 3; i++) {
				acct.addMoneny(1000);
			}
		}
	}

	public static void main(String[] args) {
		ThreadCommunication acct = new ThreadCommunication();// 账户
		Customer c1 = acct.new Customer(acct);// 储户1
		Customer c2 = acct.new Customer(acct);// 储户2

		c1.setName("储户1");
		c2.setName("储户2");

		c1.start();
		c2.start();
	}

}
