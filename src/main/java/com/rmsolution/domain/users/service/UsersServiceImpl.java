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

	/**
	 * 
	 * 회원가입 시 입력한 정보를 바인딩 (비밀번호는 암호화)
	 * @author 윤동진
	 * @since  2024. 1. 6.
	 * @param  users: 회원 객체
	 */
	@Override
	@Transactional
	public void register(Users users) {
		//19:58 추가
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		    usersMapper.create(users);
		    log.info("저장한 패스워드 : {}", users.getPassword());
	}

	
	/**
	 * 
	 * 회원가입 시 입력한 정보를 바인딩 (암호화 된 비밀번호와 입력한 실제 번호를 비교)로그인 시 사용
	 * @author 윤동진
	 * @since  2024. 1. 7.
	 * @param  id: 회원 아이디
	 * @param  password: 회원 비밀번호
	 * @return 해당하는 아이디 와 비밀번호를 가진 회원 객체
	 */
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

	
	/**
	 * 
	 * 구독신청을 한 회원의 구독여부 변경
	 * @author 윤동진
	 * @since  2024. 1. 10.
	 * @param  id: 회원의 아이디
	 */
	@Override
	@Transactional
	public void updateSubscribedToTrue(String id) {
		usersMapper.updateSubscribed(id);
	}
	
	
	
}
