package com.ptm.api.wallet.service.mapper;

import com.ptm.api.config.constant.EwalletStatus;
import com.ptm.api.config.constant.EwalletType;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.wallet.entity.UserWallet;

public class UserWalletMapper {
	
	public UserWallet mapUserWallet(String userId, Double amount ,EwalletType ewalletType ) {
		UserWallet userWallet=new UserWallet();
		userWallet.setUserid(userId);
		userWallet.setBalanceInBaseCurrency(amount);
		userWallet.setBalanceInConsumerCurrency(amount);
		userWallet.setWalletType(ewalletType);
		userWallet.setWalletStatus(EwalletStatus.ACTIVE);
		userWallet.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());

		return userWallet;
	}

}
