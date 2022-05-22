package com.ptm.api.user.controller.dto;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO extends UserDTO {

	public static final int PASSWORD_MIN_LENGTH = 4;

	public static final int PASSWORD_MAX_LENGTH = 100;

	@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
	private String password;
	
	private String otp;


	public RegisterUserDTO() {
	}


}
