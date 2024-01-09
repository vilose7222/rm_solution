package com.rmsolution.domain.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
/**
 * 회원 컴포넌트 (BeanValidation 관련 메시지 추가)
 * @author 윤동진
 *
 */
public class Users {
	@NotBlank(message = "아이디를 입력하세요.")
	@Size(min = 6 , max = 12 , message="6~12 자리입니다.")
	private String id;
	@NotBlank(message="비밀번호를 입력하세요.")
	private String password;
	@NotBlank(message="이름을 입력하세요.")
	private String name;
	@NotBlank(message = "이메일을 입력하세요.")
	private String email;
	private String regdate;
}
