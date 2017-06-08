package cn.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;
import cn.e3mall.solr.SearchResult;
/**
 * 
 * <p>Title: SearchServiceImpl</p>
 * <p>Description:搜索的分页显示业务层 </p>
 * @version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDao dao;
	
	public SearchResult search(String keyword, int page, int rows) throws Exception {
		//创建一个SolrQuery
		SolrQuery query=new SolrQuery();
		//设置查询条件
		query.setQuery(keyword);
		//设置分页显示
		if(page<=0) page=1;
		query.setStart((page-1)*rows);
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//开启高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		//调用DAO 进行查询
		SearchResult result = dao.Search(query);
		//计算总页数
		int count = result.getRecourdCount();
		int totalPage=count/rows;
		if(count % rows >0 )
			totalPage++;
		
		// 向SearchResult 设置 值
		result.setTotalPage(totalPage);
		//添加到返回结果
		return result;
	}

}
