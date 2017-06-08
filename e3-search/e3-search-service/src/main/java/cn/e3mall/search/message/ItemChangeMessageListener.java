package cn.e3mall.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.search.service.impl.SearchItemServiceImpl;
/**
 * 
 * <p>Title: ItemChangeMessageListener</p>
 * <p>Description:商品,更变后的 消费者 监听器, 负责一直监听 发送的消息 </p>
 * @version 1.0
 */
public class ItemChangeMessageListener implements MessageListener {
	
	@Autowired
	private SearchItemServiceImpl searchItemServiceImpl;
	
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage=(TextMessage) message;
			long id = Long.parseLong(textMessage.getText());
			searchItemServiceImpl.addDocument(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
