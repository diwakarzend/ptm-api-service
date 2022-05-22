package com.ptm.api.payout.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PayoutDto {
	
	@NotNull(message = "remittanceAmount can not be null")
	@NotBlank(message = "remittanceAmount can not be blank")
	private String remittanceAmount;
	
	@NotNull(message = "accountNumber can not be null")
	@NotBlank(message = "accountNumber can not be blank")
	private String accountNumber;
	
	@NotNull(message = "beneficiaryName can not be null")
	@NotBlank(message = "beneficiaryName can not be blank")
	private String beneficiaryName;
	
	@NotNull(message = "route can not be null")
	@NotBlank(message = "route can not be blank")
	private String route;

	@NotNull(message = "mobileNumber can not be null")
	@NotBlank(message = "mobileNumber can not be blank")
	private String mobileNumber;
	
	@NotNull(message = "ifscCode can not be null")
	@NotBlank(message = "ifscCode can not be blank")
	private String ifscCode;
	
	private String clientId;
	
	@NotNull(message = "type can not be null")
	@NotBlank(message = "type can not be blank")
	private String type;
	
	private String merchantCode;


}
