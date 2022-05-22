package com.ptm.api.integration.payout.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMoneyDTO extends VerifyBenificiaryDTO{
	private String remittanceAmount;
	private String beneficiaryID;
	private String beneficiaryName;
	private String route;

}
