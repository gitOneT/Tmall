package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.pojo.EasyUITreeNode;
import cn.e3mall.utills.E3Result;

/**
 * 
 * <p>Title: ContentCategoryController</p>
 * <p>Description: </p>
 * @version 1.0
 */
@Controller
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list") 
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0")Long parentId ){
		List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
		return list;
	}
	
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addNewTreeNode(String name,Long parentId){
		E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
		return e3Result;
	}
	/**
	 *重命名
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updataTreeNode(Long id ,String name){
		E3Result e3Result=new E3Result();
		contentCategoryService.updateTreeNode(id, name);
		return e3Result.ok() ;
	}
	
}
