package cn.e3mall.activeMQ;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * <p>Title: MessageConsumer</p>
 * <p>Description: 目的是 初始化spring 容器</p>
 * @version 1.0
 */
public class MessageConsumer {
	@Test
	public void runSpring() throws IOException{
		//初始化 spring容器
		ApplicationContext  applicationContext=new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-activemq.xml");
		//等待
		System.in.read();
	}
}
