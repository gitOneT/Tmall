package cn.e3mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * <p>Title: MyMessageListener实现MessageListener</p>
 * <p>Description: 接收消息  </p>
 * @version 1.0
 * desc :写完实习类,需要在bean 中配置 才生效
 */
public class MyMessageListener implements MessageListener{

	public void onMessage(Message message) {
		try {
			//获取消息
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
