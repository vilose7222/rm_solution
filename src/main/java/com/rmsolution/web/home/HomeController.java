package com.rmsolution.web.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rmsolution.domain.subscription.dto.Solution;
import com.rmsolution.domain.subscription.dto.Subscription;
import com.rmsolution.domain.subscription.service.SolutionService;
import com.rmsolution.domain.subscription.service.SubscriptionService;
import com.rmsolution.domain.users.dto.LoginForm;
import com.rmsolution.domain.users.dto.Users;
import com.rmsolution.domain.users.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class HomeController {
	
	private final UsersService usersService;
	private final SolutionService solutionService;
	private final SubscriptionService subscriptionService;
	private final PasswordEncoder passwordEncoder;
	
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
	
	/**
	 * 
	 * 회원가입 버튼 클릭 시 이동
	 * @author 윤동진
	 * @since  2024. 1. 7
	 * @param  model:뷰로 전달 하기 위한 모델 객체
	 * @param
	 * @return 뷰 이름(회원가입 페이지)
	 */
	@GetMapping("/register")
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
	@PostMapping("/register")
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
	
	/**
	 * 
	 * 로그인 화면으로 이동 (loginForm 객체 미리 생성)
	 * @author 윤동진
	 * @since  2024. 1. 7.
	 * @param model: 뷰로 데이터 전달을 위한 모델 객체
	 * @return 뷰 이름 (로그인 화면)
	 */
	@GetMapping("/login")
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
	@PostMapping("/login") 
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
		return "redirect:/";
	}

	/**
	 * 
	 * 구독 버튼 클릭 시 이동
	 * @author 윤동진
	 * @since  2024. 1. 9.
	 * @param  model: 뷰로 데이터 전달을 위한 객체
	 * @return 뷰 이름(구독신청 페이지)
	 */
	@GetMapping("/subscription")
	public String subscribeForm(Model model) {
		//존재하는 솔루션 플랜 목록 조회
		List<Solution> planList = solutionService.getSolutionPlanList();
		
		//비어있는 구독 객체 생성
		Subscription subscription = Subscription.builder().build();
		
		//데이터 전달
		model.addAttribute("planList",planList);
		model.addAttribute("subscription",subscription);
		
		return "/user/subscription";
	}
	/**
	 * 
	 * 구독 신청 시 처리 (js에서 fetch로 전송)
	 * @author 윤동진
	 * @since  2024. 1. 9.
	 * @param  subscripition: 구독 정보 가지고 있는 객체
	 * @return ResponseEntity: HTTP 응답 생성하는 객체(응답 후 js에서 처리)
	 */
	@ResponseBody
	@PostMapping("/subscription")
	public ResponseEntity<?> handleSubscription(@RequestBody Subscription subscription) {
		
		//userId가 이미 존재하는지 반환 (있으면 참, 없으면 거짓) 결과로 구독 가능 여부 판단
		boolean userIdAlreadyExists = subscriptionService.checkUserIdExists(subscription.getUserId());
		
		if(userIdAlreadyExists) {
			//id가 이미 존재하는 경우 (신규 구독 불가,구독 연장 권유, 대시보드로 연결)
			 Map<String, Object> response = new HashMap<>();
		     response.put("userIdAlreadyExists", true);
		     return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
		//id가 존재하지 않는다면(구독이 가능) 구독 정보를 저장 하고 정상 응답 처리
		subscriptionService.setSubscription(subscription);
		Map<String, Object> response = new HashMap<>();
	    response.put("userIdAlreadyExists", false);
		return ResponseEntity.ok(response);
	}
	
	

	
	
	
	
	
	
	
	
	
	
}
