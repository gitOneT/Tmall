package cn.e3mall.sso.service;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.utills.E3Result;

public interface RegisterService {
	//校验 用户名,手机, 邮箱
	E3Result checkCodeRegister(String param,int type);
	//用户注册功能
	E3Result registerUser(TbUser tbUser);
}
