package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.sso.service.LoginService;
import cn.e3mall.utills.CookieUtils;
import cn.e3mall.utills.E3Result;

/**
 * 
 * <p>Title: LoginController</p>
 * <p>Description:用户登陆Controller </p>
 * @version 1.0
 */
@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Value("TOKEN_KEY")
	private String TOKEN_KEY;
	
	@RequestMapping("/page/login")
	public String showLoginJsp(){
		return "login";
	}
	
	@RequestMapping(value="/user/login" ,method=RequestMethod.POST)
	@ResponseBody
	public E3Result login(String username ,String password,HttpServletRequest request,
			
		HttpServletResponse response	){
		//1.接收参数,2.调用service 
		E3Result result = loginService.userLogin(username, password);
		//从返回结果中取出token ,写入cookie,Cookie 要跨域
		if(result.getStatus()==200){
			String token = result.getData().toString();
			//使用cookie 的工具类对cookie进行操作
			CookieUtils.setCookie(request, response, TOKEN_KEY, token);
		}

		return result;
	}
	
}		
