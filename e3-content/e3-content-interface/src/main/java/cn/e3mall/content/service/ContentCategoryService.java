package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.pojo.EasyUITreeNode;
import cn.e3mall.utills.E3Result;

/**
 * 
 * <p>Title: ContentCategoryService</p>
 * <p>Description:内容分类服务 </p>
 * @version 1.0
 */
public interface ContentCategoryService {
	/**
	 * 展示内容分类列表
	 */
	List<EasyUITreeNode>  getContentCatList(long parentId);
	/**
	 *添加叶子节点
	 */
	E3Result addContentCategory(Long parentId,String name );
	/**
	 *重命名
	 */
	E3Result updateTreeNode(Long id ,String name);
	
}

