package cn.e3mall.sso.service;

import cn.e3mall.utills.E3Result;

public interface TokenService {
	E3Result getUserByToken(String token);
}
