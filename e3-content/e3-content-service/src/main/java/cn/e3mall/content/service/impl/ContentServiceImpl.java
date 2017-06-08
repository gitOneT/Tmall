package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.jedis.JedisClient;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
import cn.e3mall.utills.E3Result;
import cn.e3mall.utills.JsonUtils;
/**
 * 
 * <p>Title: ContentServiceImpl</p>
 * <p>Description:商品内容显示的业务层  </p>
 * @version 1.0
 */
@Service     
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper; 
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	/**
	 * 
	 * <p>Title: getContentList</p>
	 * <p>Description:商品内容列表分页的显示 </p>
	 * @param page
	 * @param rows
	 * @return
	 * @see cn.e3mall.content.service.ContentService#getContentList(java.lang.Integer, java.lang.Integer)
	 */
	public EasyUIDataGridResult getContentList(Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page,rows);
		TbContentExample example=new TbContentExample();
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//创建一个EasyUIDataGridResult 主要是 符合响应的数据格式
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setRows(list);
		// 去分页的结果
		PageInfo<TbContent> pageInfo=new PageInfo<>(list);
		long total = pageInfo.getTotal();
		// 再把 总记录数据 填充进去
		result.setTotal(total);
		return result;
	}
	/**
	 * 
	 * <p>Title: addContent</p>
	 * <p>Description: 商品内容添加</p>
	 * @param content
	 * @return
	 * @see cn.e3mall.content.service.ContentService#addContent(cn.e3mall.pojo.TbContent)
	 */
	public E3Result addContent(TbContent content) {
		//将内容插入到tb_content表中
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//插入到数据库
		contentMapper.insert(content);
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());//,添加新商品内容,清除指定的key,保证数据最新
		return E3Result.ok();
	}
	/**
	 * 
	 * <p>Title: getContentListById</p>
	 * <p>Description: 根据商品内容分类的id查询内容列表</p>
	 * @param cid 
	 * @return
	 * @see cn.e3mall.content.service.ContentService#getContentListById(java.lang.Long)
	 * @idea :将数据添加到redis中,减少数据交互,减少数据库压力
	 */
	public List<TbContent> getContentListById(long cid) {
		//1.先从redis中查询数据 \\在这添加一个try catch(){} 防止抛出异常,影响正常程序运行
		try {
			String json = jedisClient.hget(CONTENT_LIST, cid+"");
			if(StringUtils.isNotBlank(json)){
				//将数据 装换成 列表说需要的信息
				//将json 数据转化成list集合
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example =new TbContentExample(); 
			Criteria criteria = example.createCriteria();
			//设置条件,根据分类的id 查询商品内容列表
			criteria.andCategoryIdEqualTo(cid);
		 List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
			try {
				 //向redis中添加数据
				jedisClient.hset("CONTENT_LIST", cid+"", JsonUtils.objectToJson(list));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return list;
	}

}
