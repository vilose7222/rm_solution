package com.rmsolution.domain.subscription.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsolution.domain.subscription.dto.Subscription;
import com.rmsolution.domain.subscription.mapper.SubscriptionMapper;
import com.rmsolution.domain.users.mapper.UsersMapper;

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
	private final UsersMapper usersMapper;
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
		usersMapper.updateSubscribed(subscription.getUserId());
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
	
	/**
	 * 
	 * 대시보드 이동 회원 인증
	 * @author 윤동진
	 * @since  2024. 1. 10.
	 * @param  userId: 대시보드 이동하고자 하는 회원의 아이디
	 * @return 해당하는 아이디를 가진 회원의 구독 정보 반환
	 */
	@Override
	@Transactional
	public Subscription isSubscribedUser(String userId) {
		return subscriptionMapper.isSubscribed(userId);
	}
	
	/**
	 * 
	 * 남은 구독일수 계산 관련 메서드
	 * @author 윤동진
	 * @since  2024. 1. 10.
	 * @param  구독 객체
	 * @return 남은 구독일자
	 */
	@Override
	public long calculateRemainingDays(Subscription subscription) {
		//현재 날짜
		LocalDate currentDate = LocalDate.now();
		
		//구독 시작일을 LocalDate로 변환
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일");
		LocalDate startDate = LocalDate.parse(subscription.getSubscriptionDate(),formatter);
		
		//남은 일수 계산
		long remainingDays = subscription.getSubscriptionPeriod() - currentDate.toEpochDay() + startDate.toEpochDay();
		subscription.setRemainingDays(remainingDays);
		return remainingDays;
	}
}
