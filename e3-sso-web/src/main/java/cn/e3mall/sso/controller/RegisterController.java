package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;
import cn.e3mall.utills.E3Result;

/**
 * 
 * <p>Title: RegisterController</p>
 * <p>Description:注册功能Controller </p>
 * @version 1.0
 */
@Controller
public class RegisterController {
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkCode(@PathVariable String param ,@PathVariable Integer type){
		E3Result result = registerService.checkCodeRegister(param, type);
		return result;
		
	}
	
	@RequestMapping(value="/page/register")
	public String showRegister(TbUser tbUser){
		return "register";
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public E3Result register(TbUser tbUser){
		E3Result result = registerService.registerUser(tbUser);
		return result;
	}
	
	
}
