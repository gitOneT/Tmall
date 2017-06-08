package cn.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * <p>Title: GenHtmlListener</p>
 * <p>Description:监听商品添加商品生成静态化页面 </p>
 * @version 1.0
 */
@Controller
public class GenListener implements MessageListener{
	@Autowired
	private ItemService itemServer;
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;
	@Value("${GEN_HTML}")
	private String GEN_HTML;
	
	@Override
	public void onMessage(Message message) {
		try {
			//创建一个模版,参考JSP --已经 将改造完成,就是换成freemarker提供的标签即可
			//从消息中获取商品ID
			TextMessage textMessage=(TextMessage) message;
			//根据商品ID查询商品信息,商品的基本信息和描述信息,
			String itemId = textMessage.getText();
			Long id=new Long(itemId);
			//等待事物提交
			Thread.sleep(500);
			
			TbItem tbItem = itemServer.getItemById(id);
			//装换将TbItem转成item 一个完整商品信息对象
			Item item=new Item(tbItem);
			TbItemDesc itemDesc = itemServer.getItemDescByid(id);
			//创建一个数据集,封装商品数据
			Map data=new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", itemDesc);
			//加载模版
			Configuration configuration = freemarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			//创建一个输出流,输出到指定的文件目录和文件名称
			Writer out=new FileWriter(new File(GEN_HTML+id+".html"));
			//生产成静态页面
			template.process(data, out);
			//关闭流
			out.close();
		} catch (Exception e) {
		}
	}

}
