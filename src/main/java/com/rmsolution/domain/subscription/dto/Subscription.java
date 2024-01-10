package com.rmsolution.domain.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 구독 필수정보와 선택정보 관련 DTO
 *
 * @author 윤동진
 * @since  2024. 1. 8.
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Subscription {
	private String subscriptionId;
	private String userId;
	private String solutionId;
	private String serviceType;
	private String storageCapacity;
	private String userCount;
	private int subscriptionPeriod;
	private String subscriptionDate;
	private String companyName;
	private String address;
	private String detailAddress;
	private String phoneNumber;
	private long remainingDays;
	
}
