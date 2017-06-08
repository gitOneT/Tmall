package cn.e3mall.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.utills.E3Result;

/**
 * 
 * <p>Title: TBContentController</p>
 * <p>Description: 商品内容列表显示</p>
 * @version 1.0
 */
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public 	EasyUIDataGridResult getContentList(Integer page, Integer rows){
		EasyUIDataGridResult result = contentService.getContentList(page, rows);
		return result;
	} 
	
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent tbContent){
		contentService.addContent(tbContent);
		return E3Result.ok();
		
	}
}
