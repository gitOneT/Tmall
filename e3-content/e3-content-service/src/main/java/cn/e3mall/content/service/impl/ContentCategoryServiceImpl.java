package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.EasyUITreeNode;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
import cn.e3mall.utills.E3Result;
/**
 * 
 * <p>Title: ContentCategoryServiceImpl</p>
 * <p>Description:内容分类业务层实现类 </p>
 * @version 1.0
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	/**
	 * 
	 * <p>Title: getContentCatList</p>
	 * <p>Description:根据parentID查询子节点列表 </p>
	 * @param parentId 根据父类的ID 查询子节点的列表
	 * @return 返回一个Tree 的节点数据
	 * @see cn.e3mall.content.service.ContentCategoryService#getContentCatList(long)
	 */
	public List<EasyUITreeNode> getContentCatList(long parentId) {
	
		//1.取出查询的ID 
		//2.根据parentId 去查询tb_content_category表中的 子类节点数据
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//3.设置查询条件.
		criteria.andParentIdEqualTo(parentId);
		//执行查询 
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		//4,把查询结果转换成,EasyUITreeNode  的类型. id, text, state
		List<EasyUITreeNode> treeNodeList =new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode tree=new EasyUITreeNode();
			tree.setId(tbContentCategory.getId());
			tree.setText(tbContentCategory.getName());
			tree.setState(tbContentCategory.getIsParent()?"closed":"open"); //
			treeNodeList.add(tree);
		}
		//5.将树形数据返回
		return treeNodeList;
	}
	/** 
	 * <p>Title: addContentCategory</p>
	 * <p>Description:添加叶子节点 </p>
	 * @param parentId, name 
	 * @return 返回一个添加后的id . 目的是 能继续添加节点
	 * @see cn.e3mall.content.service.ContentCategoryService#addContentCategory(java.lang.Long)
	 */
	public E3Result addContentCategory(Long parentId,String name) {
		// 接收两个参数.向tb_content_category中插入参数
		 TbContentCategory tbContentCategory=new TbContentCategory();
		// a>创建ContentCategory 对象.补全里面的属性.
		 tbContentCategory.setParentId(parentId);
		 tbContentCategory.setName(name);
		 tbContentCategory.setSortOrder(1);
		 tbContentCategory.setStatus(1);
		 tbContentCategory.setCreated(new Date());
		 tbContentCategory.setUpdated(new Date());
		//该类目是否为父类目，1为true，0为false', 新添加的是一个叶子节点.
		 tbContentCategory.setIsParent(false);
		 
		 //执行插入语句
		 tbContentCategoryMapper.insert(tbContentCategory);
		 //  应该是为了 能继续在这个节点上继续添加叶子节点
		 //判断父节点isparent属性,如果不是true改为ture  // 根据parentId查询父节点
		 TbContentCategory parentnode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentnode.getIsParent()){
			parentnode.setIsParent(true);
			//更新父节点
			tbContentCategoryMapper.updateByPrimaryKey(parentnode);
		}
		//需要返回主键,返回,E3Result ,其中包括TbContentCatagory
		return E3Result.ok(tbContentCategory);
	}
	/**
	 * 
	 * <p>Title: updateTreeNode</p>
	 * <p>Description: 根据节点id 重命名</p>
	 * @param id
	 * @param name
	 * @return
	 * @see cn.e3mall.content.service.ContentCategoryService#updateTreeNode(java.lang.Long, java.lang.String)
	 */
	public E3Result updateTreeNode(Long id, String name) {
		E3Result e3Result=new E3Result();
		try{
			TbContentCategory record=new TbContentCategory();
			record.setId(id);
			record.setName(name);
			tbContentCategoryMapper.updateByPrimaryKeySelective(record);
			return e3Result.ok();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

}
