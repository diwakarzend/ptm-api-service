package com.ptm.api.integration.payout.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayoutDto {
	
	private String remittanceAmount;
	private String accountNumber;
	private String beneficiaryName;
	private String route;
	private String userId;
	private String apiKey;
	private String mobileNumber;
	private String ifscCode;
	private String clientId;
	private String type;
	private String merchantCode;

}
