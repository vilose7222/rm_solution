package com.rmsolution.web.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rmsolution.domain.subscription.dto.Subscription;
import com.rmsolution.domain.subscription.service.SubscriptionService;
import com.rmsolution.domain.users.dto.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 대시보드 및 구독연장 관련 컨트롤러
 *
 * @author 윤동진
 * @since  2024. 1. 10.
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@Controller
public class DashboardController {

	private final SubscriptionService subscriptionService;
	
	
	@GetMapping("")
	public String dashboardForm(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    Users loginUser = (Users) session.getAttribute("loginUser");

	    if (loginUser != null) {
	        Subscription dashboardInfo = subscriptionService.isSubscribedUser(loginUser.getId());
	        subscriptionService.calculateRemainingDays(dashboardInfo);
	        // 구독 정보가 있는지 여부를 확인
	        if (dashboardInfo != null) {
	            model.addAttribute("dashboardInfo", dashboardInfo);
	            return "user/dashboard";
	        } else {
	            // 구독하지 않은 경우 다른 페이지로 이동 또는 처리
	            return "redirect:/subscribe"; // 예시로 구독 페이지로 리다이렉트
	        }
	    } else {
	        return "redirect:/login";
	    }
	}
}
