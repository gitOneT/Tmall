package cn.e3mall.content.service;



import java.util.List;

import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.utills.E3Result;

public interface ContentService {
	//商品内容的添加
	E3Result addContent(TbContent content);
	EasyUIDataGridResult getContentList(Integer page,Integer rows);
	//动态展示 首页的广告
	List<TbContent> getContentListById(long cid);
} 
