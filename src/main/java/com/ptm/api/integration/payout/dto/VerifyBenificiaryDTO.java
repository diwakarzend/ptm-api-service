package com.ptm.api.integration.payout.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyBenificiaryDTO {
	
	 String userId;
	 String apikey;
	 String mobileNumber;
	 String clientId;
	 String ifscCode;
	 String accountNumber;

}
