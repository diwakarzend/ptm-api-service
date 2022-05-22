package com.ptm.api.wallet.controller.vo;

import java.time.LocalDateTime;

import com.ptm.api.config.constant.Eflags;
import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.wallet.entity.RequestForFund;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundRequestVO {

	private String requestUserName;

	private Double requestAmount;

	private String reqstfundUuid;

	private EtransactionStatus approveStatus;

	private Eflags proofUpdaodStatus;

	private LocalDateTime reqstDate;

	private String payementMode;

	private String fromBank;

	private String toBank;

	public FundRequestVO() {
	}

	public FundRequestVO(RequestForFund requestForFund) {
		this.requestUserName = requestForFund.getFromReqstUserId();
		this.requestAmount = requestForFund.getTxnAmt();
		this.reqstfundUuid = requestForFund.getReqstfunduuid();
		this.approveStatus = requestForFund.getApproveStatus();
		this.proofUpdaodStatus = requestForFund.getProofUpdaodStatus();
		this.reqstDate = requestForFund.getReqstDate();
		this.fromBank = requestForFund.getFromBank();
		this.toBank = requestForFund.getToBank();
		this.payementMode=requestForFund.getPayementMode();

	}

}
