package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * 
 * <p>Title: IndexController</p>
 * <p>Description:商城首页的动作类</p>
 * @version 1.0
 */
@Controller
public class IndexController {
		
	@Autowired
	private ContentService  contentService;
	//获取资源文件中的cid 
	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_LUNBO_ID;
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		//查询内容列表
		List<TbContent> ad1List = contentService.getContentListById(CONTENT_LUNBO_ID);//为了不固定定cid  就写在资源文件中了
		model.addAttribute("ad1List", ad1List);//使用  Model 就是为了把数据传递给页面轮播图使用
		return "index";
	}
}
