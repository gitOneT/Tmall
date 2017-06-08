package cn.e3mall.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 为商品列表提供 total  和rows 的json 格式的数据
 * <p>Title: EasyUIResult</p>
 * <p>Description: </p>
 * @version 1.0
 */
public class EasyUIDataGridResult implements Serializable{
	private static final long serialVersionUID = -8696943096463909045L;
	/**
	 * 总记录数total:xx
	 */
	private Long total;
	/**
	 *响应的格式是[{},{},{}...{}]
	 */
	private List<?> rows;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
