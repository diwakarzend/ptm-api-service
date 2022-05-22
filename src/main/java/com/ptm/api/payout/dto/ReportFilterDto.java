package com.ptm.api.payout.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportFilterDto {
	
	private String clientId;
	
	private String txnId;
	
	private String accountNumber;
	
	private String vendorId;
	
	private String route;
	
	private String status;
	
	private String date;
	
	
}
