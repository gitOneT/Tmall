package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;
/**
 * 
 * <p>Title: Item</p>
 * <p>Description:专门为商品详情页面提供的pojo </p>
 * @version 1.0
 */
public class Item extends TbItem {
	//先提供一个图片显示的属性 给JSP页面 ,注意属不能私有化
	public  String[] getImages(){  
		String image = this.getImage();//图片地址串
		if(image!=null && !image.equals("")){
			String[] imas = image.split(",");
			return imas;
		}
		return null;
	}
	// 提供一个无参构造 和一个有参构造

	public Item() {
	}
	public Item (TbItem tbItem){
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setId(tbItem.getId());
		this.setImage(tbItem.getImage());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
	}
	
}
