package cn.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.solr.SearchItem;
import cn.e3mall.solr.SearchResult;

/**
 *商品搜索DAO
 * <p>Title: SearchDao</p>
 * <p>Description:这个SearchDAO 不改所用直接是DAO实现类了</p>
 * @version 1.0
 */
@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	
	/**
	 * 
	 * <p>Title: Search</p>
	 * <p>Description: 根据查询条件 ,查询结果.</p>
	 * @param query
	 * @return
	 * @throws SolrServerException
	 */
	public SearchResult Search(SolrQuery query) throws SolrServerException{
		//根据query 查询索引库
		QueryResponse response = solrServer.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		//取查询的总记录数
		long found = solrDocumentList.getNumFound();
		
		SearchResult result = new SearchResult();
		result.setRecourdCount((int) found);
		//取商品列表,需要取高亮显示
		List<SearchItem> itemList=new ArrayList<>();
		// 设置高亮
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem item=new SearchItem();
			item.setId((String) solrDocument.get("id"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");//先取id,在根据id取对应的商品高亮的字段
			String title="";
			if(list.size()>0 && list!=null){
				 title = list.get(0);
			}else{
				title= (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			itemList.add(item);//将查询出的商品放到商品集合中
		}
		result.setItemList(itemList);
		//返回结果
		return result;
		
	}
	
	
}
