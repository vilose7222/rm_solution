package com.rmsolution.domain.users.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.rmsolution.domain.users.dto.Users;

/**
 * 
 * 회원 서비스 관련 DB처리를 위한 메서드 명세
 *
 * @author 윤동진
 * @since 2024. 1. 3.
 * @version 1.0
 */
@Mapper
public interface UsersMapper {
	
	/**
	 * 
	 * 회원가입 시 입력한 정보를 바인딩
	 * @author 윤동진
	 * @since  2024. 1. 4.
	 * @param  users: 회원 객체
	 */
	public void create(Users users);

	/**
	 * 
	 * 해당하는 id를 가진 회원의 정보 가져오기
	 * @author 윤동진
	 * @since  2024. 1. 4.
	 * @param  id: 회원 아이디
	 * @return 해당하는 아이디 를 가진 회원 객체
	 */
	public Users findById(@Param("id")String id);
	
	
	/**
	 * 
	 * 회원 가입 시 아이디 중복 체크
	 * @author 윤동진
	 * @since  2024. 1. 11.
	 * @param  id: 회원가입을 위해 입력하는 아이디
	 * @return 아이디가 DB에 존재하면 true, 없다면 false
	 */
	public boolean checkExistId(@Param("id") String id);
	
	
	/**
	 * 
	 * 로그인 하는 회원의 정보를 검사
	 * @author 윤동진
	 * @since  2024. 1. 5.
	 * @param  id:회원의 아이디
	 * @param  password: 회원의 비밀번호
	 * @return 해당하는 id와 password 를 가진 회원 객체
	 */
	public Users findByIdAndPasswd(@Param("id") String id, @Param("password") String password);
	
	/**
	 * 
	 * 구독 신청 시 회원의 구독 여부를 false에서 true로 변경
	 * @author 윤동진
	 * @since  2024. 1. 10.
	 * @param  id: 구독 신청을 한 회원의 아이디
	 */
	public void updateSubscribed(@Param("id") String id);
	
	/**
	 * 
	 * 아이디 찾기 시 사용 (이름과 이메일에 해당하는 회원을 찾기)
	 * @author 윤동진
	 * @since  2024. 1. 12.
	 * @param  name: 찾고자 하는 회원의 이름
	 * @param  email: 찾고자 하는 회원의 이메일
	 * @return 해당하는 이름과 이메일을 가진 회원 객체
	 */
	public Users findUser(@Param("name")String name, @Param("email")String email);
}
