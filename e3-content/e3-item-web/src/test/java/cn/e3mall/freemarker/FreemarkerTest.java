package cn.e3mall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;



/**
 * 
 * <p>Title: FreemarkerTest</p>
 * <p>Description: freemarker标准模版</p>
 * @version 1.0
 */
public class FreemarkerTest {

	@Test
	public void genFile() throws Exception{
		//创建一个configuration对象,直接new 一个对象,构造方法参数就是freemarker对应的版本号
		Configuration configuration=new Configuration(Configuration.getVersion());
		//设置文件模版所在的路径
		configuration.setDirectoryForTemplateLoading(new File("E:/WorkSpace/work-ssm/e3-content/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		//设置模版加载的字符集 就是utf-8
		configuration.setDefaultEncoding("utf-8");
		//加载一个模版,创建一个模版对象
		Template template=configuration.getTemplate("hello.ftl");
		//创建一个模版对象使用的数据集,,使用使用pojo,也是是map 一般常用map
		Map dataMap=new HashMap<>();
		//向数据集中添加数据
		dataMap.put("hello", "this is freemarker test,if you see it ,explain is success");
		//创建一个writer对象,一般创建一个,FileWriter对象,指定生成的文件名
		Writer writer=new FileWriter(new File("E:/temp/freemarker/hello.html"));
		//调用 模版对象的process方法输入文件
		template.process(dataMap, writer);;
		//关闭流
		writer.close();
	}
}
