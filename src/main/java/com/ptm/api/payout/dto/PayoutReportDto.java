package com.ptm.api.payout.dto;

import java.time.LocalDateTime;

import com.ptm.api.config.constant.Eflags;
import com.ptm.api.config.constant.EtransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayoutReportDto {

	private Double remittanceAmount;

	private String accountNumber;

	private String beneficiaryName;

	private String route;

	private String userId;

	private String mobileNumber;

	private String ifscCode;

	private String type;

	private String txnId;

	private String merchantTxnId;

	private Double payoutChanrge;

	private Double profitAmount;
	
	private EtransactionStatus status;

	private String remark;

	private Eflags approvalRequired;
	
	private Double openingBalance;
	
	private Double closingBalance;
	
    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
    
    private String merchantCode;

}
