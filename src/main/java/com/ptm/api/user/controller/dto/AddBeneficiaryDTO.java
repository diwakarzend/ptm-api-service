package com.ptm.api.user.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBeneficiaryDTO {
	
	@NotNull(message = "mobileNumber can not be null")
	@NotBlank(message = "mobileNumber can not be blank")
	private String mobileNumber;

	
	@NotNull(message = "bankName can not be null")
	@NotBlank(message = "bankName can not be blank")
	private String bankName;
	
	@NotNull(message = "accountNumber can not be null")
	@NotBlank(message = "accountNumber can not be blank")
	private String accountNumber;
	
	@NotNull(message = "ifscCode can not be null")
	@NotBlank(message = "ifscCode can not be blank")
	private String ifscCode;
	
	@NotNull(message = "firstName can not be null")
	@NotBlank(message = "firstName can not be blank")
	private String firstName;
	
	@NotNull(message = "lastName can not be null")
	@NotBlank(message = "lastName can not be blank")
	private String lastName;
	
	
}
