package com.rmsolution.domain.subscription.service;

import com.rmsolution.domain.subscription.dto.Subscription;
/**
 * 
 * 구독 관련 비즈니스 로직 처리를 위한 인터페이스
 *
 * @author 윤동진
 * @since  2024. 1. 9.
 * @version 1.0
 */
public interface SubscriptionService {
	/**
	 * 
	 * 구독 신청 시 데이터 바인딩
	 * @author 윤동진
	 * @since  2024. 1. 9.
	 * @param subscription:구독 객체
	 */
	public void setSubscription(Subscription subscription);
	
	/**
	 * 
	 * 구독을 신청하는 회원이 이미 신청을 했는지 확인
	 * @author 윤동진
	 * @since  2024. 1. 9.
	 * @param userId: 로그인 한 유저의 아이디
	 * @return 이미 신청했다면 참, 아니면 거짓
	 */
	public boolean checkUserIdExists(String userId);
}
