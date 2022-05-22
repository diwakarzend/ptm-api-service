package com.ptm.api.wallet.service.mapper;

import com.ptm.api.config.constant.EtransactionType;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.wallet.entity.UserWalletActivity;

public class UserWalletActivityMapper {

	public UserWalletActivity mapUserWalletActivity(String userId, EtransactionType txnType, Double amount,
			Double currentAmount,String remark,String serviceType,String txnId) {
		UserWalletActivity userWalletActivity = new UserWalletActivity();
		userWalletActivity.setUserid(userId);
		userWalletActivity.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
		userWalletActivity.setTxnType(txnType);
		userWalletActivity.setAmount(amount);
		userWalletActivity.setCurrentAmount(currentAmount);
		userWalletActivity.setRemarks(remark);
		userWalletActivity.setServiceType(serviceType);
		userWalletActivity.setTxnId(txnId);
		return userWalletActivity;
	}

}
