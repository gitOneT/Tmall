package cn.e3mall.solrtest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;
import org.springframework.web.context.request.NativeWebRequest;

import com.sun.tools.corba.se.idl.ValueRepositoryId;

/**
 * 
 * <p>Title: TestSorlJ</p>
 * <p>Description:测试SolrJ </p>
 * @version 1.0
 */
public class TestSorlJ {
	@Test
	public void run() throws SolrServerException, Exception{
		//创建一个SolrServer 对象
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//创建一个SolrInputDocumnet 
		SolrInputDocument  document=new SolrInputDocument();
		document.addField("id", "doc1");
		document.addField("item_title", "测试商品1");
		document.addField("item_price", 1000);
		solrServer.add(document);
		solrServer.commit();
		
	}
	@Test
	public void del() throws SolrServerException, IOException{
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.131:8080/solr");
		solrServer.deleteByQuery("id:doc1");
		solrServer.commit();
	}
	@Test
	public void HightSearch() throws SolrServerException{
		//创建一个SolrServer对象
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//创建一个查询对象SolrQuery
		SolrQuery  solrQuery=new SolrQuery();
		//设置查询条件
		solrQuery.set("q", "阿尔卡特");
		//设置分页条件
		solrQuery.setStart(1);
		solrQuery.setRows(10);
		//开启高亮
		solrQuery.setHighlight(true);
		//设置高亮的字段
		solrQuery.addHighlightField("item_title");
		//高亮开始的标签
		solrQuery.setHighlightSimplePre("<em style='color:red'>");
		solrQuery.setHighlightSimplePost("<em/>");
		//设置默认搜索域	
		solrQuery.set("df", "item_title");
		//执行查询得到一个response对象
		QueryResponse queryResponse = solrServer.query(solrQuery);
		//////////////获取高亮 结果
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		
		
		//取出查询结果总记录数
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询的总记录数:"+solrDocumentList);
		for (SolrDocument solrDocument : solrDocumentList) { //遍历结果集中每一项结果
			System.out.println(solrDocument.get("id"));
			//取出高亮结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title="";
			if(list.size()>0&& list!=null){
				//取出高亮的结果
				title = list.get(0);
			}else{
				// 否则正常取出
				title=(String) solrDocument.get("item_title");
			}
			System.out.println(title);
			//一次打印其他结果
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
