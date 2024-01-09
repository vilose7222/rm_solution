package com.rmsolution.web.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rmsolution.domain.users.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 관리 서비스 컨트롤러
 *
 * @author 윤동진
 * @since  2024. 1. 3.
 * @version 1.0
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UsersController {

	private final UsersService usersService;
	private final PasswordEncoder passwordEncoder;
	
	
	
}
