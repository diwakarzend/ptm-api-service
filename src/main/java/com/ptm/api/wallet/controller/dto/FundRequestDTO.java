package com.ptm.api.wallet.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundRequestDTO {
	
	@NotNull(message = "payementMode can not be null")
	@NotBlank(message = "payementMode can not be blank")
	private String payementMode;
	
	@NotNull(message = "fromBank can not be null")
	@NotBlank(message = "fromBank can not be blank")
	private String fromBank;
	
	@NotNull(message = "toBank can not be null")
	@NotBlank(message = "toBank can not be blank")
	private String toBank;
	
	@NotNull(message = "requestAmount can not be null")
	private Double requestAmount;
	
	@NotNull(message = "Remark can not be null")
	@NotBlank(message = "Remark can not be blank")
	private String remark;
	
	@NotNull(message = "transationRefNo can not be null")
	@NotBlank(message = "transationRefNo can not be blank")
	private String transationRefNo;
	

	
}
