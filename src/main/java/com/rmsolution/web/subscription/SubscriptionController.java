package com.rmsolution.web.subscription;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsolution.domain.subscription.dto.Solution;
import com.rmsolution.domain.subscription.dto.Subscription;
import com.rmsolution.domain.subscription.service.SolutionService;
import com.rmsolution.domain.subscription.service.SubscriptionService;
import com.rmsolution.domain.users.dto.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 구독 관련 컨트롤러
 *
 * @author 윤동진
 * @since 2024. 1. 10. (수정)
 * @version 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/subscription")
public class SubscriptionController {

	private final SubscriptionService subscriptionService;
	private final SolutionService solutionService;

	/**
	 * 
	 * 구독 버튼 클릭 시 이동
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 9.
	 * @param model: 뷰로 데이터 전달을 위한 객체
	 * @return 뷰 이름(구독신청 페이지)
	 */
	@GetMapping("")
	public String subscribeForm(Model model) {
		// 존재하는 솔루션 플랜 목록 조회
		List<Solution> planList = solutionService.getSolutionPlanList();

		// 비어있는 구독 객체 생성
		Subscription subscription = Subscription.builder().build();

		// 데이터 전달
		model.addAttribute("planList", planList);
		model.addAttribute("subscription", subscription);

		return "/user/subscription";
	}

	/**
	 * 
	 * 구독 신청 시 처리 (js에서 fetch로 전송)
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 9.
	 * @param subscripition: 구독 정보 가지고 있는 객체
	 * @return ResponseEntity: HTTP 응답 생성하는 객체(응답 후 js에서 처리)
	 */
	@ResponseBody
	@PostMapping("")
	public ResponseEntity<?> handleSubscription(@RequestBody Subscription subscription) {

		// userId가 이미 존재하는지 반환 (있으면 참, 없으면 거짓) 결과로 구독 가능 여부 판단
		boolean userIdAlreadyExists = subscriptionService.checkUserIdExists(subscription.getUserId());

		if (userIdAlreadyExists) {
			// id가 이미 존재하는 경우 (신규 구독 불가,구독 연장 권유, 대시보드로 연결)
			Map<String, Object> response = new HashMap<>();
			response.put("userIdAlreadyExists", true);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
		// id가 존재하지 않는다면(구독이 가능) 구독 정보를 저장 하고 정상 응답 처리
		subscriptionService.setSubscription(subscription);

		Map<String, Object> response = new HashMap<>();
		response.put("userIdAlreadyExists", false);
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * 대시보드 이동 전 구독여부를 확인 (js에서 fetch로 처리) 로그인 한 사용자의 정보를 조회 후 응답
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 10.
	 * @param model:   뷰로 데이터 전달을 위한 모델 객체
	 * @param request: HTTP 요청 정보를 담은 객체
	 * @return ResponseEntity<Map<String, Object>>: 대시보드 정보와 함께 응답 객체
	 */
	@ResponseBody
	@GetMapping("/check")
	public ResponseEntity<Map<String, Object>> checkSubscription(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Users loginUser = (Users) session.getAttribute("loginUser");

		Subscription dashboardInfo = subscriptionService.isSubscribedUser(loginUser.getId());

		// 구독 정보 있는지 확인
		if (dashboardInfo != null) {
			model.addAttribute("dashboardInfo", dashboardInfo);
			Map<String, Object> response = new HashMap<>();
			response.put("dashboardInfo", dashboardInfo);
			// 대시보드로 이동
			return ResponseEntity.ok(response);
		} else {
			// 구독 페이지로 이동
			return ResponseEntity.ok(Collections.emptyMap());
		}
	}

	/**
	 * 
	 * 구독 연장 신청 요청 시 발생 기존의 구독 기간에 신청기간 추가 + 연장 히스토리에 신청일과 기간,아이디 기록
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 11.
	 * @param subscription: 구독 신청한 정보를 담은 객체
	 * @return ResponseEntity<Object>: 결과 정보 응답 객체
	 */
	@ResponseBody
	@PostMapping("/extension")
	public ResponseEntity<Object> updatePeriod(@RequestBody Subscription subscription) {
		try {
			subscriptionService.updateSubscriptionPeriod(subscription);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("구독 연장 실패");
		}
	}
}
