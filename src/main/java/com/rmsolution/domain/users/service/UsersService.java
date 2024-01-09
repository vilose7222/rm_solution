package com.rmsolution.domain.users.service;

import com.rmsolution.domain.users.dto.Users;

public interface UsersService {
	//회원가입
	public void register(Users users);
	//로그인
	public Users isUser(String id, String password);
}
