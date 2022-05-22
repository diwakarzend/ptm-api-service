package com.ptm.api.wallet.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptm.api.config.constant.EwalletType;
import com.sun.istack.NotNull;

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
public class UserWalletActionDTO {
	@NotNull
	private String userId;
	@NotNull
	private Double amount;
	
	private String remark;
	
	@JsonIgnore
	private String serviceType;
	
	@JsonIgnore
	private String txnId;
	
	
	//@JsonIgnore
	private EwalletType ewalletType;

}
