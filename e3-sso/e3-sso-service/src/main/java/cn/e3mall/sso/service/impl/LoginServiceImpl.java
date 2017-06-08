package cn.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.jedis.JedisClient;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.LoginService;
import cn.e3mall.utills.E3Result;
import cn.e3mall.utills.JsonUtils;
/**
 * <p>Title: LoginServiceImpl</p>
 * <p>Description:用户登陆业务层</p>
 * @version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE_TIME}")
	private int SESSION_EXPIRE_TIME;
	
	/**参数:用户名和密码
	 * 业务逻辑:
	 * 1.判断用户名和密码是否正确
	 * 2.如果不正确返回到登陆页面
	 * 3.如果正确,生成token
	 * 4.把用户信息写入redis,key:token value:用户信息 ,不包含用户密码
	 * 5.设置session的过期时间
	 * 6.把token返回
	 */
	public E3Result userLogin(String username, String password) {
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		//先判断用户名是否正确
		if(list.size()==0 || list==null){
			return E3Result.build(400, "用户名或者密码错误");
		}
		TbUser user = list.get(0);
		//在判断密码是否正确
		if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return E3Result.build(400, "用户名或者密码错误");
		}
		//都正确后,就生成token
		String token = UUID.randomUUID().toString();
		//将用户信息存入session中 ,固定的前缀
		user.setPassword(null);// 密码在服务端处理
		jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
		//设置过期时间
		jedisClient.expire("SESSION:"+token,SESSION_EXPIRE_TIME );
		return E3Result.ok(token);
	}

}
