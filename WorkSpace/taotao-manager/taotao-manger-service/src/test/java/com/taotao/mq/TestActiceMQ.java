package com.taotao.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestActiceMQ {
	/**
	 * 发送
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQueueProducer() throws Exception {
		/*
		 * 1.创建一个连接工厂对象——ConnectionFactory对象，需要指定ActiceMQ服务的ip及端口号。
		 * 注意参数brokerURL的开头是tcp://，而不是我们通常所见的http://，端口是61616而不是我们访问ActiceMQ
		 * 后台管理页面时所使用的8161。
		 */
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");
		// 2.使用ConnectionFactory对象来创建一个Connection对象
		Connection connection = connectionFactory.createConnection();
		// 3.开启连接，需要调用Connection对象的start方法
		connection.start();
		/*
		 * 4.使用Connection对象创建一个Session对象
		 * 第一个参数：是否开启事务(ActiceMQ的事务)，一般不使用分布式事务，因为它特别消耗性能，而且顾客体验特别差，
		 * 现在互联网的做法是保证数据的最终一致(也就是允许暂时数据不一致)。比如顾客下单购买东西，一旦订单生成完就立刻响应给用户
		 * 下单成功。至于下单后一系列的操作，比如通知会计记账、通知物流发货、商品数量同步等等都先不用管，只需要发送一条消息到消息队列，
		 * 消息队列来告知各模块进行相应的操作，一次告知不行就两次，直到完成所有相关操作为止，这也就做到了数据的最终一致性。
		 * 如果第一个参数设为true，那么第二个参数将会被直接忽略掉。如果第一个参数为false，那么第二个参数才有意义。
		 * 第二个参数：消息的应答模式，常见的有手动应答和自动应答两种模式。我们一般使用自动应答模式。
		 */
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.使用Session对象创建一个Destination对象，目的地有两种形式，一种是queue，一种是topic
		Queue queue = session.createQueue("test-queue"); // 不同名字的Queue相当于是不同的队列，你要是发生消息的时候，你要注意你这个消息要往哪个队列中发送
		// 6.使用Session对象创建一个Producer对象
		MessageProducer producer = session.createProducer(queue);
		// 7.使用Producer对象发生消息
		TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("使用ActiceMQ 发送的队列消息");
		// TextMessage textMessage2 = session.createTextMessage("使用ActiceMQ 发送的队列消息");
		producer.send(textMessage);
		// 8.关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	/**
	 * 接收
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQueueConsumer() throws Exception {
		// 1.创建一个连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");
		// 2.使用连接工厂对象创建一个连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 3.使用连接对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 4.使用Session对象创建一个Destination对象，使用Queue
		Queue queue = session.createQueue("test-queue");
		// 5.使用Session对象创建一个消费者
		MessageConsumer consumer = session.createConsumer(queue);
		// 6.使用消费者对象接收消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// 7.打印消息
				TextMessage textMessage = (TextMessage) message;
				String text = "";
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		/*
		 * 程序等待接收用户结束操作，等待键盘输入 程序自己并不知道什么时候有消息，也不知道什么时候不再发送消息了，这就需要手动干预了，
		 * 当我们想停止接收消息时，可以在控制台输入任意键，然后回车即可结束接收操作（也可以直接按回车）。
		 */
		System.out.println("read before..");
		// 目的是为了阻塞在这儿，等待消息
		System.in.read();
		System.out.println("read back..");

		/*
		 * // 下面在实际开发中一般不推荐使用 while(true) { Message message = consumer.receive(3000); if
		 * (message == null) { break; } // 7.打印消息 TextMessage textMessage =
		 * (TextMessage) message; String text = ""; try { text = textMessage.getText();
		 * System.out.println(text); } catch (JMSException e) { e.printStackTrace(); } }
		 */
		// 8.关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	/**
	 * 生产者topic模式
	 */
	@Test
	public void testTopicProducer() throws Exception {
		// 创建一个连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");
		// 使用连接工厂对象来创建一个连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 使用连接对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 使用Session对象来创建一个Topic
		Topic topic = session.createTopic("test-topic");
		// 使用Session对象来创建一个Producer，指定其目的地是Topic
		MessageProducer producer = session.createProducer(topic);
		// 创建一个TextMessage对象
		TextMessage message = session.createTextMessage("使用topic来发送的消息");
		// 使用Producer对象来发送消息
		producer.send(message);
		// 关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	/**
	 * topic消费者模式
	 */
	@Test
	public void testTopicConsumer() throws Exception {
		// 创建一个连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");
		// 使用工厂对象创建一个连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 使用连接对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 创建一个Destination对象，使用topic
		Topic topic = session.createTopic("test-topic");
		// 使用Session对象创建一个消费者
		MessageConsumer consumer = session.createConsumer(topic);
		System.out.println("topic消费者3。。。。");
		// 使用消费者对象接收消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// 打印消息
				TextMessage textMessage = (TextMessage) message;
				String text = "";
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		// 程序等待
		System.in.read();

		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	@Test
	public void testString() {
		String str = null;
		boolean noneBlank = StringUtils.isNotBlank(str);
		System.out.println(noneBlank);
	}
	
	
	
}