package cn.e3mall.solr;

import java.io.Serializable;
/**
 * 
 * <p>Title: SerachItem</p>
 * <p>Description:solr查询结果的自定义的实体类 </p>
 * @version 1.0
 */
public class SearchItem implements Serializable {
	private static final long serialVersionUID = 8387244858868110541L;
	private String  id;
	private String title;
	private String image;
	private String sell_point;
	private long  price;
	private String category_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSell_point() {
		return sell_point;
	}
	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String[] getImages(){// 为了取图片 特别添加的属性
	 if(image !=null && !image.equals("")){
		 String[] strings = image.split(",");
		 return strings;
	 }
		return null;
		
	}
	
}
