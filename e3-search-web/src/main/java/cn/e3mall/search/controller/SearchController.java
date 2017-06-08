package cn.e3mall.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.search.service.SearchService;
import cn.e3mall.solr.SearchItem;
import cn.e3mall.solr.SearchResult;

/**
 * <p>Title: SearchController</p>
 * <p>Description:商品搜索的动作类 </p>
 * @version 1.0
 */
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@Value("${PAGE_ROWS}")
	private Integer PAGE_ROWS;
	
	@RequestMapping("/search")
	public String  searchItem(String keyword,@RequestParam(defaultValue="1") Integer page,Model model) throws Exception{
		//解决get中文乱码
		 keyword= new String(keyword.getBytes("iso8859-1"), "utf-8");
		SearchResult search = searchService.search(keyword, page, PAGE_ROWS);
		List<SearchItem> itemList = search.getItemList();
		int recourdCount = search.getRecourdCount();
		int totalPage = search.getTotalPage();
		//使用model 将数据传递给页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", totalPage);
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", recourdCount);
		model.addAttribute("itemList", itemList);
		//返回逻辑视图
		return "search";
		
	}	
	
}
