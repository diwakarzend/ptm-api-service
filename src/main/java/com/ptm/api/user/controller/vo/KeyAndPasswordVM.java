package com.ptm.api.user.controller.vo;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing the user's key and password.
 */
@Getter
@Setter
public class KeyAndPasswordVM {
	@NotBlank
	@NotNull
	private String otp;
	
	@NotBlank
	@NotNull
	private String newPassword;
	
	@NotNull
	@NotBlank
	private String phoneNumber;

}
