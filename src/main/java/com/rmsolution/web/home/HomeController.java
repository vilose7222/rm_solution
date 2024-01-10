package com.rmsolution.web.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.rmsolution.domain.users.dto.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * 홈 이동시 필요한 컨트롤러 (세션 회원 인증)
 *
 * @author 윤동진
 * @since  2024. 1. 10. (수정)
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class HomeController {
	
	/**
	 * 
	 * 메인페이지 이동
	 * @author 윤동진
	 * @since  2024. 1. 6.
	 * @param loginUser: 로그인한 사용자 정보
	 * @param model:뷰로 데이터를 전달하기 위한 모델 객체
	 * @param request: HTTP 요청 정보를 담은 객체
	 * @return 뷰 이름 (메인 페이지)
	 */
	@GetMapping("")
	public String home(@SessionAttribute(name="loginUser", required = false)Users loginUser,Model model,HttpServletRequest request) {
		if(loginUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
		}
		return "index";
	}
}
