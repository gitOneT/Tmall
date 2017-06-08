package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.utills.E3Result;

/**
 * 
 * <p>Title: SearchItemController</p>
 * <p>Description: 索引库一键导入 </p>
 * @version 1.0
 */
@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemService searchItemService;
	  
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importDate () throws Exception{
		E3Result e3Result = searchItemService.importAllLit();
		return e3Result;
		
	}
}
