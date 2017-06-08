package cn.e3mall.pojo;

import java.io.Serializable;
/**
 * Tree树的返回值
 * <p>Title: EasyUITreeNode</p>
 * <p>Description: </p>
 * @version 1.0
 */
public class EasyUITreeNode implements Serializable {
	private static final long serialVersionUID = 4558287260317863478L;
	private Long id;
	private String text;
	/**参考EasyUI API 如下
	 * state：节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
	 */
	private String state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}	
