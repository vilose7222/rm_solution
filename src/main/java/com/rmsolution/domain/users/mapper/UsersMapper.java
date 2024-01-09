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
	// 회원가입
	public void create(Users users);

	//id에 해당하는 회원 찾기
	public Users findById(@Param("id")String id);
	
	// 로그인
	public Users findByIdAndPasswd(@Param("id") String id, @Param("password") String password);

}
