package cn.e3mall.search.mapper;

import java.util.List;

import cn.e3mall.solr.SearchItem;

/**
 * 
 * <p>Title: ItemMapper</p>
 * <p>Description:solr的mapper接口 </p>
 * @version 1.0
 */
public interface ItemMapper {
	List<SearchItem> getItemList();
	SearchItem getItemById(long itemid);
}
