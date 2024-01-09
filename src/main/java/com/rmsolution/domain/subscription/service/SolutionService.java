package com.rmsolution.domain.subscription.service;

import java.util.List;

import com.rmsolution.domain.subscription.dto.Solution;
/**
 * 
 * 솔루션 제품 비즈니스로직 처리를 위한 명세
 *
 * @author 윤동진
 * @since  2024. 1. 8.
 * @version 1.0
 */
public interface SolutionService {
	
	/**
	 * 
	 * 존재하는 솔루션 제품 리스트를 반환
	 * @author 윤동진
	 * @since  2024. 1. 8.
	 * @return 존재하는 솔루션 List
	 */
	public List<Solution> getSolutionPlanList();
}
