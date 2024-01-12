package com.rmsolution.web.users;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsolution.domain.users.dto.LoginForm;
import com.rmsolution.domain.users.dto.Users;
import com.rmsolution.domain.users.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 로그인 처리 관련 컨트롤러
 *
 * @author 윤동진
 * @since 2024. 1. 10. (수정)
 * @version 1.0
 */
@RequiredArgsConstructor
@RequestMapping("/login")
@Controller
public class LoginController {

	private final UsersService usersService;

	/**
	 * 
	 * 로그인 화면으로 이동 (loginForm 객체 미리 생성)
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 7.
	 * @param model: 뷰로 데이터 전달을 위한 모델 객체
	 * @return 뷰 이름 (로그인 화면)
	 */
	@GetMapping("")
	public String loginForm(Model model) {
		LoginForm loginForm = LoginForm.builder().build();
		model.addAttribute("loginForm", loginForm);
		return "user/login";
	}

	/**
	 * 로그인 처리
	 * 
	 * @since 2024. 1. 8.
	 * @param loginForm:     로그인 폼 데이터를 담고 있는 객체
	 * @param bindingResult: 입력값 검증 결과를 담은 객체
	 * @param request:       HTTP 요청 정보를 담고 있는 객체
	 * @return 뷰 이름 또는 리다이렉트 경로
	 */
	@PostMapping("")
	public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			request.setAttribute("loginFail", true);
			return "user/login";
		}

		Users loginUser = usersService.isUser(loginForm.getLoginId(), loginForm.getPassword());

		// 회원이 아닌 경우
		if (loginUser == null) {
			bindingResult.rejectValue("loginFail", "loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "user/login";
		}
		// 회원인 경우
		HttpSession session = request.getSession();
		session.setAttribute("loginUser", loginUser);
		session.setAttribute("userId", loginUser.getId());
		return "redirect:/";
	}

	/**
	 * 
	 * 아이디 찾기 클릭시 실행
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 12.
	 * @return 뷰 이름(아이디 찾기 팝업)
	 */
	@GetMapping("/find")
	public String findMember(Model model) {
		return "/user/findpopup";
	}

	/**
	 * 
	 * 아이디 찾기 기능 관련 메서드 (fetch 요청 응답)
	 * 
	 * @author 윤동진
	 * @since 2024. 1. 12.
	 * @param name  회원 이름
	 * @param email 회원 이메일
	 * @return ResponseEntity<Object>: 회원 정보와 함께 Http 응답 상태를 담은 객체
	 */
	@GetMapping(value = "/result/{name}/{email}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Object> resultFind(@PathVariable("name") String name, @PathVariable("email") String email,
			Model model) {
		Users user = usersService.findByNameAndEmail(name, email);

		if (user != null) {
			// 회원 존재하면 200 과 함께 회원 정보 전달
			return ResponseEntity.ok(user);
		} else {
			// 회원이 존재하지 않으면 404를 보냄
			return ResponseEntity.notFound().build();
		}
	}

}
