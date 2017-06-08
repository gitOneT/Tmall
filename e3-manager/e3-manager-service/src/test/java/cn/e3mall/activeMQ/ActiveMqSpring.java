package cn.e3mall.activeMQ;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 
 * <p>Title: ActiveMqSpring</p>
 * <p>Description:测试MQ整合spring </p>
 * @version 1.0
 */
public class ActiveMqSpring {
	@Test
	public void sendMassage(){
		//初始化spring 容器
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//从容器中获取jmsTemplate 模版
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		//从容器中获取一个destination对象 //从容器中获取.取出一个队列目的,或者是主题目的,最后强转成Destination
		Destination destination = (Destination) applicationContext.getBean("queueDestination");
		//发送消息
		jmsTemplate.send(destination,new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				//在这使用session编写消息
				TextMessage message = session.createTextMessage("spring 整合activeMQ.123..");
				return message;
			}
		});
	}
	
}
