package cn.e3mall.sso.controller;

import java.awt.PageAttributes.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.sso.service.TokenService;
import cn.e3mall.utills.E3Result;
import cn.e3mall.utills.JsonUtils;

/**
 * 
 * <p>Title: TokenController</p>
 * <p>Description:token查询用户信息</p>
 * @version 1.0
 */
@Controller
public class TokenController {
	@Autowired
	private TokenService tokenService;
	@RequestMapping(value="/user/token/{token}",
			produces=org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String  selectUerByToken(@PathVariable String token,String callback){
		E3Result result = tokenService.getUserByToken(token);
		//响应之前判读是否为jsonp 请求
		if(StringUtils.isNotBlank(callback)){
			//把结果封装成一个JS语句.方便回显数据
			return callback+"("+JsonUtils.objectToJson(result)+");";
		}
		return JsonUtils.objectToJson(result);
		
	}
	
}
