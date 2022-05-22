package com.ptm.api.wallet.controller.vo;

import java.time.LocalDateTime;

import com.ptm.api.wallet.entity.UserWalletActivity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWalletTransactionVO {

	private String userId;
	private String transactionType;
	private Double transactionAmout;
	private Double currentAmount;
	private LocalDateTime createdDate;
	private String remarks;
	private String transactionId;

	public UserWalletTransactionVO() {
	}

	public UserWalletTransactionVO(UserWalletActivity userWalletActivity) {

		this.userId = userWalletActivity.getUserid();
		this.transactionAmout = userWalletActivity.getAmount();
		this.currentAmount = userWalletActivity.getCurrentAmount();
		this.transactionType = userWalletActivity.getTxnType().toString();
		this.createdDate = userWalletActivity.getCreatedDate();
		this.remarks = userWalletActivity.getRemarks();
		this.transactionId = userWalletActivity.getTxnId();

	}

}
