package com.rmsolution.domain.users.service;

import com.rmsolution.domain.users.dto.Users;
/**
 * 회원과 관련한 비즈니스로직 처리를 위한 명세
 *
 * @author 윤동진
 * @since  2024. 1. 5.
 * @version 1.0
 */
public interface UsersService {
	
	/**
	 * 
	 * 회원가입 시 입력한 정보를 바인딩
	 * @author 윤동진
	 * @since  2024. 1. 5.
	 * @param  users: 회원 객체
	 */
	public void register(Users users);
	
	/**
	 * 
	 * 해당하는 id를 가진 회원의 정보 가져오기
	 * @author 윤동진
	 * @since  2024. 1. 4.
	 * @param  id: 회원 아이디
	 * @param  password: 회원 비밀번호
	 * @return 해당하는 아이디 와 비밀번호를 가진 회원 객체
	 */
	public Users isUser(String id, String password);
	
	/**
	 * 
	 * 구독 신청을 하는 회원의 구독여부를 변경(회원 가입 시 default는 false)
	 * @author 윤동진
	 * @since  2024. 1. 10.
	 * @param  id: 구독 신청을 하는 회원의 아이디
	 */
	public void updateSubscribedToTrue(String id);
}
