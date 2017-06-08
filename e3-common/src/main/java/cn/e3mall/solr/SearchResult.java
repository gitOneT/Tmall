package cn.e3mall.solr;


import java.io.Serializable;
import java.util.List;

/**
 * 
 * <p>Title: SearchResult</p>
 * <p>Description: 商品搜索放回的结果</p>
 * @version 1.0
 */
public class SearchResult implements Serializable{
	private static final long serialVersionUID = -1937021887858761084L;
	private List<SearchItem> itemList;
	private int totalPage;
	private int recourdCount;
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getRecourdCount() {
		return recourdCount;
	}
	public void setRecourdCount(int recourdCount) {
		this.recourdCount = recourdCount;
	}
	
}
