package com.rmsolution.web.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rmsolution.domain.users.dto.LoginForm;
import com.rmsolution.domain.users.dto.Users;
import com.rmsolution.domain.users.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 로그인 처리 관련 컨트롤러
 *
 * @author 윤동진
 * @since  2024. 1. 10. (수정)
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
@Controller
public class LoginController {

	private final UsersService usersService;
	
	/**
	 * 
	 * 로그인 화면으로 이동 (loginForm 객체 미리 생성)
	 * @author 윤동진
	 * @since  2024. 1. 7.
	 * @param model: 뷰로 데이터 전달을 위한 모델 객체
	 * @return 뷰 이름 (로그인 화면)
	 */
	@GetMapping("")
	public String loginForm(Model model) {
		LoginForm loginForm = LoginForm.builder().build();
		model.addAttribute("loginForm",loginForm);
		return "user/login";
	}
	
	/**
	 * 로그인 처리
	 * @since  2024. 1. 8.
	 * @param loginForm: 로그인 폼 데이터를 담고 있는 객체
	 * @param bindingResult: 입력값 검증 결과를 담은 객체
	 * @param request: HTTP 요청 정보를 담고 있는 객체
	 * @return 뷰 이름 또는 리다이렉트 경로
	 */
	@PostMapping("") 
	public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			request.setAttribute("loginFail", true);
			return "user/login";
		}
		
		Users loginUser = usersService.isUser(loginForm.getLoginId(), loginForm.getPassword());
		
		// 회원이 아닌 경우
		if (loginUser == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "user/login";
		}
		// 회원인 경우
		HttpSession session =  request.getSession();
		session.setAttribute("loginUser", loginUser);
		session.setAttribute("userId", loginUser.getId());
		log.info("로그인 한 유저의 정보 : {}", loginUser);
		return "redirect:/";
	}
}