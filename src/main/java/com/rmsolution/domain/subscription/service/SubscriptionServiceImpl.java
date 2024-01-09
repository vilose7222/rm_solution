package com.rmsolution.domain.subscription.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsolution.domain.subscription.dto.Subscription;
import com.rmsolution.domain.subscription.mapper.SubscriptionMapper;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
/**
 * 
 * 구독신청 관련 비즈니스 로직 처리를 위한 구현체
 *
 * @author 윤동진
 * @since  2024. 1. 8.
 * @version 1.0
 */
public class SubscriptionServiceImpl implements SubscriptionService{

	private final SubscriptionMapper subscriptionMapper;
	
	/**
	 * 
	 * 구독 신청 시 데이터를 바인딩
	 * @author 윤동진
	 * @since  2024. 1. 8.
	 * @param subscription: 구독 객체
	 */
	@Override
	@Transactional
	public void setSubscription(Subscription subscription) {
		subscriptionMapper.setData(subscription);
	}
	
	/**
	 * 
	 * 구독 신청 시 이미 신청 한 회원인지 확인 하기 위한 메서드
	 * @author 윤동진
	 * @since  2024. 1. 9.
	 * @param userId: 구독 신청하는 사용자의 아이디(세션에 저장)
	 * @return 이미 존재하면 참, 없으면 거짓
	 */
	@Override
	@Transactional
	public boolean checkUserIdExists(String userId) {
	    return subscriptionMapper.checkId(userId);
	}
}