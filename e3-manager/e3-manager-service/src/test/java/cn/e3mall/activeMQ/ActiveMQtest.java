package cn.e3mall.activeMQ;

import java.io.IOException;

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
import org.apache.ibatis.cache.decorators.WeakCache;
import org.junit.Test;

/**
 * 
 * <p>
 * Title: ActiveMQtest
 * </p>
 * <p>
 * Description:测试MQ 消息队列
 * </p>
 * 
 * @version 1.0
 */
public class ActiveMQtest {

	// 点对点 队列形式发送端
	@Test
	public void testQueueProduce() throws JMSException {
		// 1.创建一个ConnectionFactory对象,需要指定,服务的端口和 iP
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		// 2.使用ConncetionFactory对象创建一个.Connection 对象
		Connection connection = connectionFactory.createConnection();
		// 3。开启链接, 使用connention 的start的对象
		connection.start();
		// 4.使用Connection 对象创建一个.Session对象
		/**参数一是否开启事物,如果是true, 则忽略第二个参数
		 * 参数二:当参数一为false 是时候才有意义,.消息的应答模式:1,自动应答,手动应答,一般都是自动应答
		 */
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.使用Session对象创建一个,Destination对象,(topic ,queue),此处创建一个,queue 对象
		Queue queue = session.createQueue("test-queue1");
		// 6.使用session对象创建一个,producer对象'
		MessageProducer producer = session.createProducer(queue);
		// 7.创建一个Massage对象,创建一个TextMassage对象
		TextMessage message = session.createTextMessage("hello MQ queue1");
		// 8.使用producer 对象发送信息
		producer.send(message);
		// 9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	// 测试 消费者 Consumer 接收消息
	@Test
	public void testQueueConsumer() throws JMSException, IOException {
		// 1.创建一个ConnetionFactoory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		// 2.从ConnectionFactory中获取一个Connectio 对象
		Connection connection = connectionFactory.createConnection();
		// 3.开启链接.调用Connection 中的start方法
		connection.start();
		// 4.使用connection中获取session 对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.使用session对象获取一个Destnation对象.和发送端保持一致queue,并且队列名称一致
		Queue queue = session.createQueue("spring-queue");
		// 6.使用session 对象创建一个consumer 对象
		MessageConsumer createConsumer = session.createConsumer(queue);
		// 7.接收消息,
		createConsumer.setMessageListener(new MessageListener() { // 使用 MassageListener的匿名内部类
			//onMessage 接收信息
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				String text = null;
				// 消息的内容
				try {
					text = textMessage.getText();
					// 8.打印消息
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//等待鍵盤輸入.
		System.in.read();
		
		// 9.关闭资源
		createConsumer.close();
		session.close();
		connection.close();
	}
	//====================TOPIC:广播形式==========================
	@Test
	public void TestTopicProducer() throws JMSException{
		//1.创建ConnectionFactory对象,需要指定服务的端口和ip
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		//2.使用ConnectionFactory对象创建,一个Connection 对象
		Connection connection = connectionFactory.createConnection();
		//3.开启链接,调用,Connection 的start方法.
		connection.start();
		//4.使用connection对象,创建Sesssion对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用Session对象创建一个Destination对象,(topic .queue),此处创建一个topic 对象
		Topic topic = session.createTopic("test-topic");
		//6.使用session 创建一个producer .
		MessageProducer producer = session.createProducer(topic);
		//7.创建一个Message 对象,创建一个textMessage对象
		TextMessage message = session.createTextMessage("hhhhhhhhtttPPPPCCC");
		//8.使用producer 对象发送消息
		producer.send(message);
		//9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	
	/**
	 * <p>Title: TestTopicCousumer</p>
	 * <p>Description:消息接收者 </p>
	 * @throws JMSException 
	 * @throws IOException 
	 */
	@Test
	public void TestTopicCousumer() throws JMSException, IOException{
		//1.创建一个ConnectionFactory 对象
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		//2.使用ConnectionFactory对象 创建一个Connection 对象
		Connection connection = connectionFactory.createConnection();
		//3.开启链接.调用,Connection的start 方法
		connection.start();
		//4.使用Connection 创建session 对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用session 创建,Destination对象'保持和发送端口一样
		Topic topic = session.createTopic("test-topic");
		//6.使用session创建一个Consumer对象
		MessageConsumer consumer = session.createConsumer(topic);
		//7.接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage= (TextMessage) message;
				String text=null;
				try {
					text = textMessage.getText();
					//8.打印结果
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("消费者已启动.....");
		System.in.read();
		//9.关闭资源
		consumer.close();
		session.close();
		connection.close();
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
