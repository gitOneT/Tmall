package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

/**
 * 
 * <p>Title: ItemController</p>
 * <p>Description:商品详情页面显示 </p>
 * @version 1.0
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService; 
	
	@RequestMapping("/item/{itemId}")
	public String itemAllMessage(@PathVariable long itemId,Model model){
		//根据商品id 查询商品信息
		TbItem ibItem = itemService.getItemById(itemId);
		//将TbItem对象 装成item 对象
		Item item=new Item(ibItem);
		//根据商品id 查询商品描述
		TbItemDesc itemDesc = itemService.getItemDescByid(itemId);
		//将数据传递给 页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
	
	
}
