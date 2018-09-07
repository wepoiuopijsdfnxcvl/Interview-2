package cn.collabtech.rabbitmq.test;

public class ConsumerApp {
	//声明一个队列名字
	private final static String QUEUE_NAME = "hello";
	
	
	public static void main(String[] args) {
		MessageConsumer mc1 = new MessageConsumer();
		mc1.consume(QUEUE_NAME,"consumer1");
		MessageConsumer mc2 = new MessageConsumer();
		mc2.consume(QUEUE_NAME,"consumer2");
		MessageConsumer mc3 = new MessageConsumer();
		mc3.consume(QUEUE_NAME,"consumer3");
		MessageConsumer mc4 = new MessageConsumer();
		mc4.consume(QUEUE_NAME,"consumer4");
		MessageConsumer mc5 = new MessageConsumer();
		mc5.consume(QUEUE_NAME,"consumer5");
	}
}
