package com.rmsolution.domain.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 솔루션 플랜 관련 DTO
 *
 * @author 윤동진
 * @since  2024. 1. 8.
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solution {
	private String solutionId;
	private String name;
	private String cost;
	private String storageCapacityMin;
	private String storageCapacityMax;
	private String userCountMax;
}
