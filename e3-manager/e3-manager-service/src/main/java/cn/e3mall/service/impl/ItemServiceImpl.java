package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.jedis.JedisClient;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import cn.e3mall.utills.E3Result;
import cn.e3mall.utills.IDUtils;
import cn.e3mall.utills.JsonUtils;
/**
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: 商品列表 :现在将数据,加入redis中</p>
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired     
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;// 添加jms模版，　用来发送添加的商品的id
	@Resource
	private Destination topicDestination; //选择发送消息的形式
	
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_ITEM_PRE}")
	private String REDIS_ITEM_PRE;
	
	@Value("${ITEM_INFO_EXPIRE}")
	private Integer ITEM_INFO_EXPIRE;
	
	
	/**
	 * 
	 * <p>Title: getItemById</p>
	 * <p>Description: 现在将跟id 查询出的商品信息方法哦redis缓存中</p>
	 * @param itemId
	 * @return
	 * @see cn.e3mall.service.ItemService#getItemById(long)
	 */
	public TbItem getItemById(long itemId) {
		// 添加到缓存, 即使失败也不能影响正常程序执行
		try {
			//先从缓存中查询数据,
			String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
			if(StringUtils.isNoneBlank(json)){
				//把json 转成java对象 ,提供给表现层
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
			
		} catch (Exception e) {
			System.out.println("根据id查询商品信息redis..异常1");
			e.printStackTrace();
		}
		///根据主键查询商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		try {
			//向redis 中添加数据, 将java转成 json 保存
			jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":BASE", JsonUtils.objectToJson(item));
			//设置redis中商品信息的时效 ,保存内存使用率
			jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE", ITEM_INFO_EXPIRE);
		} catch (Exception e) {
			System.out.println("根据id查询商品信息添加到redis..异常2");
			e.printStackTrace();
		}
			return item;
	}

	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		//设置分页信息 
		PageHelper.startPage(page, rows);
		TbItemExample example=new TbItemExample();
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		//返回查询的结果集
		EasyUIDataGridResult result= new EasyUIDataGridResult();
		result.setRows(list);
		//取分页结果集
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}
	/*
	 *添加商品 应该根据数据库分别插入数据
	 */
	public E3Result addItem(TbItem item, String desc) {
		 //1.生产商品ID 
		final long itemId = IDUtils.genItemId();
		//2.补全 TBItem的属性
		item.setId(itemId);
		item.setCreated(new Date());
		item.setStatus((byte)1);//商品状态
		item.setUpdated(new Date());
		//向商品表中添加数据
		itemMapper.insert(item);
		//发送,主题信息, 将商品的id发送
		jmsTemplate.send(topicDestination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				// 要发送的消息
				TextMessage message = session.createTextMessage(itemId+"");
				return message;
			}
		});
		
		//创建一个商品描述表. 向描述表中添加数据
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(new Date());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		
		return E3Result.ok();//已经在类中定义好了 , 意思就执行插入成功
	}

	@Override
	public TbItemDesc getItemDescByid(long itemId) {
		// 添加到缓存, 即使失败也不能影响正常程序执行
				try {
					//先从缓存中查询数据,
					String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
					if(StringUtils.isNotBlank(json)){
						//把json 转成java对象 ,提供给表现层
						TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
						
						return itemDesc;
					}
					
				} catch (Exception e) {
					System.out.println("根据id查询商品描述信息添加到redis1..异常");
					e.printStackTrace();
				}
		
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		try { 
			//向redis 中添加数据, 将java转成 json 保存
			jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
			//设置redis中商品信息的时效 ,保存内存使用率
			jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_INFO_EXPIRE);
		} catch (Exception e) {
			System.out.println("根据id查询商品描述信息添加到redis2..异常");
			e.printStackTrace();
		}
		return itemDesc;
	}



}
