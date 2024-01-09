package com.rmsolution.domain.subscription.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsolution.domain.subscription.dto.Solution;
import com.rmsolution.domain.subscription.mapper.SolutionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/**
 * 
 * 솔루션 제품 관련 비즈니스 로직 처리를 위한 구현체
 *
 * @author 윤동진
 * @since  2024. 1. 8.
 * @version 1.0
 */
public class SolutionServiceImpl implements SolutionService{
	
	private final SolutionMapper solutionMapper;

	/**
	 * 
	 * 솔루션 제품들을 나타내기 위한 메서드
	 * @author 윤동진
	 * @since  2024. 1. 8.
	 * @return 존재하는 솔루션 제품 List
	 */
	@Override
	@Transactional
	public List<Solution> getSolutionPlanList() {
		return solutionMapper.findByAll();
	}
	
	
	
}
