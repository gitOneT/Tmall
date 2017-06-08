package cn.e3mall.service;

import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.utills.E3Result;

public interface ItemService {
	TbItem getItemById(long itemId);
	EasyUIDataGridResult getItemList(Integer page,Integer rows);
	E3Result addItem(TbItem item ,String desc);
	TbItemDesc getItemDescByid(long itemId);
}
