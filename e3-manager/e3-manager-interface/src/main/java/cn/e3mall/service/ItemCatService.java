package cn.e3mall.service;

import java.util.List;

import cn.e3mall.pojo.EasyUITreeNode;

/**
 * 
 * <p>Title: ItemCatService</p>
 * <p>Description: 商品分类业务层接口</p>
 * @version 1.0
 */
public interface ItemCatService {
	/**
	 * 
	 * <p>Title: getItemCatList</p>
	 * <p>Description: 根据父节点一部加载子类节点</p>
	 * @param parentId父类节点
	 * @return 返回值是一个 EasyUITreeNode 格式的json数据
	 */
	List<EasyUITreeNode> getItemCatList(Long parentId);
}
