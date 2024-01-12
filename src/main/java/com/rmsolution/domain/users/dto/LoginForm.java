package com.rmsolution.domain.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * 사용자의 로그인 정보를 담는 폼
 * 
 * 아이디, 비밀번호 등 저장
 *
 * @author 윤동진
 * @since  2024. 1. 3.
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter 
@Setter
@ToString
@Builder
public class LoginForm {
	@NotBlank(message = "아이디를 입력하여 주세요.")
	@Size(min = 6, max = 12, message = "아이디는 6~12자 사이여야 합니다.")
	private String loginId;
	@Size(min = 6, max = 20, message = "비밀번호는 6자 이상입니다.")
	@NotBlank(message = "비밀번호를 입력하여 주세요")
	private String password;
	private String loginFail;
}





