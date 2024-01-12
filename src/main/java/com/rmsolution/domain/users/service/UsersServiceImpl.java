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
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 6.
	 * @param users: 회원 객체
	 */
	@Override
	@Transactional
	public void register(Users users) {
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		usersMapper.create(users);
		log.info("저장한 패스워드 : {}", users.getPassword());
	}

	/**
	 * 
	 * 회원 가입 시 아이디 중복 체크
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 11.
	 * @param id: 회원가입을 위해 입력하는 아이디
	 * @return 아이디가 DB에 존재하면 true, 없다면 false
	 */
	@Override
	public boolean isExistId(String id) {
		boolean result = usersMapper.checkExistId(id);
		return result;
	}

	/**
	 * 
	 * 회원가입 시 입력한 정보를 바인딩 (암호화 된 비밀번호와 입력한 실제 번호를 비교)로그인 시 사용
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 7.
	 * @param id:       회원 아이디
	 * @param password: 회원 비밀번호
	 * @return 해당하는 아이디 와 비밀번호를 가진 회원 객체
	 */
	@Override
	@Transactional
	public Users isUser(String id, String password) {
		Users user = usersMapper.findById(id);
		if (user != null) {
			if (passwordEncoder.matches(password, user.getPassword()) == true) {
				log.info("로그인 성공 : {}", user);
				return user;
			} else {
				log.info("결과 false");
			}
		}
		// 로그인 실패
		return null;
	}

	/**
	 * 
	 * 구독신청을 한 회원의 구독여부 변경
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 10.
	 * @param id: 회원의 아이디
	 */
	@Override
	@Transactional
	public void updateSubscribedToTrue(String id) {
		usersMapper.updateSubscribed(id);
	}

	/**
	 * 
	 * 아이디 찾기 시 사용 (이름과 이메일에 해당하는 회원을 찾기)
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 12.
	 * @param name:  찾고자 하는 회원의 이름
	 * @param email: 찾고자 하는 회원의 이메일
	 * @return 해당하는 이름과 이메일을 가진 회원 객체
	 */
	@Override
	public Users findByNameAndEmail(String name, String email) {
		return usersMapper.findUser(name, email);
	}

}
