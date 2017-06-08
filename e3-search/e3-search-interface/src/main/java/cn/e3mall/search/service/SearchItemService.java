package cn.e3mall.search.service;

import cn.e3mall.utills.E3Result;

/**
 * 
 * <p>Title: SearchItemService</p>
 * <p>Description:数据库导入solr</p>
 * @version 1.0
 */
public interface SearchItemService {
	E3Result importAllLit() throws Exception;
}
