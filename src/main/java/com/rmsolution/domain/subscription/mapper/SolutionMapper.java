package com.rmsolution.domain.subscription.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.rmsolution.domain.subscription.dto.Solution;
@Mapper
/**
 * 
 * 솔루션 플랜 관련 Mybatis mapper클래스
 *
 * @author 윤동진
 * @since  2024. 1. 9.
 * @version 1.0
 */
public interface SolutionMapper {
	/**
	 * 
	 * 솔루션 플랜의 모든 정보를 불러오는 메서드
	 * @author 윤동진
	 * @since  2024. 1. 9.
	 * @return Solution객체의 정보
	 */
	public List<Solution> findByAll();
}
