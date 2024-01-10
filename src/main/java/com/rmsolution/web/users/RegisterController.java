package com.rmsolution.web.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rmsolution.domain.users.dto.Users;
import com.rmsolution.domain.users.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 회원가입 관련 컨트롤러
 *
 * @author 윤동진
 * @since  2024. 1. 10. (수정)
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/register")
public class RegisterController {

	private final UsersService usersService;
	
	/**
	 * 
	 * 회원가입 버튼 클릭 시 이동
	 * @author 윤동진
	 * @since  2024. 1. 7
	 * @param  model:뷰로 전달 하기 위한 모델 객체
	 * @param
	 * @return 뷰 이름(회원가입 페이지)
	 */
	@GetMapping("")
	public String registerForm(Model model) {
		Users users = Users.builder().build();
		model.addAttribute("users", users);
		return "user/signup";
	}
	
	/**
	 * 
	 * 회원가입 후 요청 처리(form태그에서 submit 요청)
	 * @author 윤동진
	 * @since  2024. 1. 7.
	 * @param  users: 회원가입 정보를 담은 객체
	 * @param  bindingResult: 입력값 검증 결과를 담은 객체
	 * @param  redirectAttributes: 리다이렉트 시 데이터를 전달하기 위한 객체
	 * @param  model:뷰로 전달 하기 위한 모델 객체
	 * @return 뷰 이름(오류가 있다면 회원가입페이지 내 처리, 성공 시 메인 페이지로 리다이렉트)
	 */
	@PostMapping("")
	public String register(@Validated @ModelAttribute Users users,BindingResult bindingResult,
			RedirectAttributes redirectAttributes ,Model model) {
		model.addAttribute("user", users);
		if(bindingResult.hasErrors()){
			    return "user/signup";
		}
		usersService.register(users);
		redirectAttributes.addFlashAttribute("status", true);
		return "redirect:/";
	}
}
