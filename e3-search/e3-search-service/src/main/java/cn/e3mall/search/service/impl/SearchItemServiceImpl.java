package cn.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.solr.SearchItem;
import cn.e3mall.utills.E3Result;
/**
 * 
 * <p>Title: SearchItemServiceImpl</p>
 * <p>Description:数据库导入到solr业务层</p>
 * @version 1.0
 */
@Service     
public class SearchItemServiceImpl implements SearchItemService {
	@Autowired 
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer; // 是注入进来的
	
	public E3Result importAllLit()  {
		try {
			//查询商品列表
			List<SearchItem> list = itemMapper.getItemList();
			//遍历商品列表'
			for (SearchItem searchItem : list) {
				//创建文件对象
				SolrInputDocument document=new SolrInputDocument();
				//向文档对象中添加域
				document.addField("id", searchItem.getId());
				document.addField("item_category_name", searchItem.getCategory_name());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_title", searchItem.getTitle());
				//把文档写入到索引库中
				solrServer.add(document);
			}
			//提交
			solrServer.commit();
			//返回导入成功
			return E3Result.ok();
			
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "亲数据导入失败!!");
		}
	}
	/**
	 *
	 * <p>Title: addDocument</p>
	 * <p>Description: 添加商品添加到索引库中 </p>
	 * @param itemid
	 * @return
	 * @throws Exception
	 */
	public E3Result addDocument(long itemid) throws Exception{
		//根据商品id ,去 数据库中查询商品信息
		SearchItem searchItem = itemMapper.getItemById(itemid);
		//同步solr数据流程   创建一个文档对象,将数据添加到域中, 将文档写入索引库,提交
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", searchItem.getId());
		document.addField("item_category_name", searchItem.getCategory_name());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_title", searchItem.getTitle());
		solrServer.add(document);
		solrServer.commit();
		return E3Result.ok();
		
	}
	
}
