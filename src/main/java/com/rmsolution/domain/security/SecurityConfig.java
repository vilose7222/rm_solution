package com.rmsolution.domain.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
/**
 * 
 * 인증 및 권한 부여를 처리
 *
 * @author 윤동진
 * @since  2024. 1. 7.
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/**
	 * 
	 * BCrypt 해시 알고리즘을 사용하는 PasswordEncoder 빈을 구성
	 * 
	 * @author 윤동진
	 * @since  2024. 1. 7.
	 * @return PasswordEncoder: BCryptPasswordEncoder를 사용하는 객체
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 
	 * 애플리케이션의 HTTP 보안 설정 구성
	 * 
	 * @author 윤동진
	 * @since  2024. 1. 7.
	 * @param  http: 구성 할 HttpSecurity 객체
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http
        	.logout()
        			 .logoutSuccessUrl("/");
			return http.build();
	}
}
