package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.EasyUITreeNode;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;

	public List<EasyUITreeNode> getItemCatList(Long parentId) {
		//根据prentId 查询父节点
		TbItemCatExample example=new TbItemCatExample();
		//设置查询条件
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//创建放回结果集
		List<EasyUITreeNode> reusltTreeNode=new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			//把列表转化成 sasyuitreenode 数据
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			reusltTreeNode.add(node);
		}
		//返回结果集 
		return reusltTreeNode;
	}

}
