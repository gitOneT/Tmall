package cn.e3mall.search.service;



import cn.e3mall.solr.SearchResult;

public interface SearchService {
	//返回值:pojo 参数:  查询条件, 分页  /
	SearchResult search(String keyword,int page,int rows) throws Exception;
}
