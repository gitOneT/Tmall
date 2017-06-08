package cn.e3mall.sso.service;

import cn.e3mall.utills.E3Result;

public interface LoginService {
	/**参数:用户名和密码
	 * 业务逻辑:
	 * 1.判断用户名和密码是否正确
	 * 2.如果不正确返回到登陆页面
	 * 3.如果正确,生成token
	 * 4.把用户信息写入redis,key:token value:用户信息 ,不包含用户密码
	 * 5.设置session的过期时间
	 * 6.把token返回
	 */
	E3Result userLogin(String username,String password);
}
