package com.ptm.api.integration.payout.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterBenificiaryDTO {
	 String mobileNumber;
	 String remid;
	 String ifscCode;
	 String accountNumber;
	 private String firstName;
	 private String lastName;
}
