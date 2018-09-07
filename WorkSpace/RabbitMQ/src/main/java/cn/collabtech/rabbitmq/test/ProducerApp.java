package cn.collabtech.rabbitmq.test;

public class ProducerApp {
	public static void main(String[] args) {
		MessageSender sender = new MessageSender();
		sender.sendMessage("hello RabbitMQ!");
	}
}
