package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.RegisterService;
import cn.e3mall.utills.E3Result;
/**
 * 
 * <p>Title: RegisterServiceImpl</p>
 * <p>Description: 用户注册业务层</p>
 * @version 1.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper tbUserMapper;
	/**
	 * 
	 * <p>Title: checkCodeRegister</p>
	 * <p>Description: 注册时,用户名,手机号,邮箱的 校验</p>
	 * @param param : 用户名, 手机号 ,邮箱
	 * @param type:参数类型  1. 用户名, 2.手机号, 3.邮箱
	 * @return 没有返回 true  从数据库中查询出来 返回 false
	 * @see cn.e3mall.sso.service.RegisterService#checkCodeRegister(java.lang.String, int)
	 */
	public E3Result checkCodeRegister(String param, int type) {
		//根据不同的type 生成不同的类型的查询条件
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		//根据不同类型 执行不同的查询
		if(type==1){
			criteria.andUsernameEqualTo(param);
		}else if (type==2) {
			criteria.andPhoneEqualTo(param);
		}else if (type==3) {
			criteria.andEmailEqualTo(param);
		}else{
			return E3Result.build(400, "数据类型错误亲!");
		}
		//执行查询条件
		List<TbUser> list = tbUserMapper.selectByExample(example);
		//判断查询结果是否包含数据 ,如果包含,就返回false 没有包含就放回true
		if (list!=null &&list.size()>0) {
			return E3Result.ok(false);
		}
		return E3Result.ok(true);
	}
	public E3Result registerUser(TbUser tbUser) {
		//在用户名注册之前最好 对数据最后做一次校验保证数据的完整性,补全属性,对密码进行md5加密
		// 用户名, 手机号,
		if(StringUtils.isBlank(tbUser.getUsername())||StringUtils.isBlank(tbUser.getPhone())){
			return E3Result.build(400, "亲你的数据不完整");
		}
		E3Result result = this.checkCodeRegister(tbUser.getUsername(), 1);
		if(!(boolean) result.getData()){
			return result.build(400, "亲.用户名已经被占用");
		}
		E3Result result1 = this.checkCodeRegister(tbUser.getPhone(), 2);
		if(!(boolean) result1.getData()){
			return result.build(400, "亲.手机号已经注册");
		}
		//对密码进行MD5加密
		String password = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
		//补全其他属性
		tbUser.setPassword(password);
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		tbUserMapper.insert(tbUser);
		//添加成功
		return result.ok();
	}

}
