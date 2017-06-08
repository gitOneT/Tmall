package cn.e3mall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;



/**
 * 
 * <p>Title: FreemarkerTest</p>
 * <p>Description: freemarker测试类</p>
 * @version 1.0
 */
public class FreemarkerProperties {

	@Test
	public void genFile() throws Exception{
		//创建一个configuration对象,直接new 一个对象,构造方法参数就是freemarker对应的版本号
		Configuration configuration=new Configuration(Configuration.getVersion());
		//设置文件模版所在的路径
		configuration.setDirectoryForTemplateLoading(new File("E:/WorkSpace/work-ssm/e3-content/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		//设置模版加载的字符集 就是utf-8
		configuration.setDefaultEncoding("utf-8");
		//加载一个模版,创建一个模版对象
		Template template=configuration.getTemplate("student.ftl");
		//创建一个模版对象使用的数据集,,使用使用pojo,也是是map 一般常用map
		Map dataMap=new HashMap<>();
		//向数据集中添加数据
     dataMap.put("hello", "this is freemarker test,if you see it ,explain is success");
		ArrayList<Student> studnetList=new ArrayList<>();
		
		Student student=new Student(1, "鹿晗", 26);
		Student student1=new Student(2, "鹿晗", 27);
		Student student2=new Student(3, "鹿晗", 28);
		Student student3=new Student(4, "鹿晗", 29);
		Student student4=new Student(5, "鹿晗", 30);
		Student student5=new Student(6, "鹿晗", 31);
		Student student6=new Student(7, "鹿晗", 32);
		studnetList.add(student);
		studnetList.add(student1);
		studnetList.add(student2);
		studnetList.add(student3);
		studnetList.add(student4);
		studnetList.add(student5);
		studnetList.add(student6);
		dataMap.put("studentList", studnetList);
		dataMap.put("date", new Date());
		dataMap.put("myVal", null);
		
		//dataMap.put("stu", student);
		
		//创建一个writer对象,一般创建一个,FileWriter对象,指定生成的文件名
		Writer writer=new FileWriter(new File("E:/temp/freemarker/student.html"));
		//调用 模版对象的process方法输入文件
		template.process(dataMap, writer);;
		
		//关闭流
		writer.close();
	}
}
