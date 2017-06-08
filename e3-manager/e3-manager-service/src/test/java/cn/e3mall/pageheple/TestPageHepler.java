package cn.e3mall.pageheple;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

public class TestPageHepler {
	//@Test  暂时不能用
	public void testPageResult(){
		//初始化 spring 容器
		ApplicationContext applicationContext=new  ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//获得mapper的代理对象
		TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
		PageHelper.startPage(1, 10);//设置 分页显示
		//执行查询
		TbItemExample example=new TbItemExample();
		List<TbItem> list = mapper.selectByExample(example);
		//分页信息
		PageInfo<TbItem> info = new PageInfo<>(list);
		System.out.println(info.getTotal());
		System.out.println(info.getPageSize());
		System.out.println(info.getPages());
	}
}
