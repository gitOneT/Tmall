package cn.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * <p>Title: HTMLGenController</p>
 * <p>Description: 生成静态页面的测试类</p>
 * @version 1.0
 */
@Controller
public class HTMLGenController {
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@RequestMapping("/genhtml")
	@ResponseBody
	public String genHtml() throws Exception{
		//从spring容器中获取FreeMarkerConfigurer对象.  注入进来
		//从FreeMarkerConfigurer 对象中获得Configuration对象
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		//使用Configuration 对象中获取Template对象
		Template template = configuration.getTemplate("hello.ftl");// 已经在配置文件中配置了路径了,
		//创建数据集
		Map dataMap=new HashMap<>();
		//向数据集中添加数据
		dataMap.put("hello", "1000");
		//创建文件输出writer 对象
		Writer writer=new FileWriter(new File("E:/temp/freemarker/spring-freemarker.html"));
		//使用template输出 process
		template.process(dataMap, writer);
		//关闭流
		writer.close();
		return "ok";
		
	} 
	
	
	
	
	
}
