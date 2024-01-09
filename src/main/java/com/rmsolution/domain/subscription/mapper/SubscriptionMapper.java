package com.rmsolution.domain.subscription.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.rmsolution.domain.subscription.dto.Subscription;
/**
 * 
 * 구독 관련 Mybatis mapper클래스
 *
 * @author 윤동진
 * @since  2024. 1. 9.
 * @version 1.0
 */
@Mapper
public interface SubscriptionMapper {
	/**
	 * 
	 * 구독 신청 시 데이터를 담아주는 메서드
	 * @author 윤동진
	 * @since  2024. 1. 8.
	 * @param subscription: 구독 객체
	 */
	public void setData(Subscription subscription);
	
	/**
	 * 
	 * 구독 신청 시 이미 신청 한 회원인지 확인 하기 위한 메서드
	 * @author 윤동진
	 * @since  2024. 1. 9.
	 * @param userId: 구독 신청하는 사용자의 아이디(세션에 저장)
	 * @return 이미 존재하면 참, 없으면 거짓
	 */
	public boolean checkId(@Param("userId") String userId);
}
