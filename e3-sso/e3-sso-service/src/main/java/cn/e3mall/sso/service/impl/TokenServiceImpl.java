package cn.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.jedis.JedisClient;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import cn.e3mall.utills.E3Result;
import cn.e3mall.utills.JsonUtils;
/**
 * 
 * <p>Title: TokenServiceImpl</p>
 * <p>Description:根据token取用户信息 token 在reidis中</p>
 * @version 1.0
 */
@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE_TIME}")
	private int SESSION_EXPIRE_TIME;
	
	@Override
	public E3Result getUserByToken(String token) {
		//1.根据token 从redies 中取出用户信息
		String json = jedisClient.get("SESSION"+token);
		//2.取不到用户信息,说明token 已经过期了,返回重新登陆
		if(StringUtils.isBlank(json)){
			return E3Result.build(201, "亲,你的登陆时间,已经过期");
		}
		//3.取到token信息 ,更新一下token的时间
		jedisClient.expire(token, SESSION_EXPIRE_TIME);
		//4.返回结果里面包含. TBuser对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		
		return E3Result.ok(user);
	}

}
