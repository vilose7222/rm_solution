package com.rmsolution.domain.users.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsolution.domain.users.dto.Users;
import com.rmsolution.domain.users.mapper.UsersMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

	private final UsersMapper usersMapper;
	private final PasswordEncoder passwordEncoder;

	
	//회원가입 메서드 (패스워드 암호화)
	@Override
	@Transactional
	public void register(Users users) {
		//19:58 추가
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		    usersMapper.create(users);
		    log.info("저장한 패스워드 : {}", users.getPassword());
	}

	@Override
	@Transactional
	public Users isUser(String id, String password) {
	    Users user = usersMapper.findById(id);
	    if (user != null) {
	        if (passwordEncoder.matches(password, user.getPassword()) == true) {
	        	log.info("로그인 성공 : {}",user);
	            return user;
	        }
	        else{
	            log.info("결과 false");
	        }
	    }

	    // 로그인 실패
	    return null;
	}
}
