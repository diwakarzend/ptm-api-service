package com.ptm.api.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ptm.api.config.constant.EtransactionType;
import com.ptm.api.config.constant.EwalletType;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.wallet.controller.dto.UserWalletActionDTO;
import com.ptm.api.wallet.entity.PtmTxn;

@Service
public class WalletTransferService {

	@Autowired
	private UserWalletService userWalletService;

	@Autowired
	private TransactionService transactionService;

	@Transactional
	public void transferWalletAmount(UserWalletActionDTO userWalletAction,String adminUserID, EtransactionType adminTransType,
			EtransactionType userTransType) {
		validateSenderAndReceiver(adminUserID, userWalletAction.getUserId() ,adminTransType,userTransType);
		validateUserBal(adminUserID, adminTransType, userWalletAction.getEwalletType(),
				userWalletAction.getAmount());// admin

		validateUserBal(userWalletAction.getUserId(), userTransType, userWalletAction.getEwalletType(),
				userWalletAction.getAmount());// user

		walletAction(userWalletAction.getAmount(), userWalletAction.getEwalletType(),
				adminUserID, adminTransType, "inter wallet transaction");// for admin

		walletAction(userWalletAction.getAmount(), userWalletAction.getEwalletType(), userWalletAction.getUserId(),
				userTransType, "wallet transaction by admin");// for user
	}

	private void validateSenderAndReceiver(String senderId, String reciverId,EtransactionType adminTransType,EtransactionType userTransType) {
		
		if (senderId.equals(reciverId)) {
			throw new UserServiceException(UserExceptionCodeAndMassage.GENERAL_ERROR);
		}else if(adminTransType.equals(userTransType)) {
			throw new UserServiceException(UserExceptionCodeAndMassage.GENERAL_ERROR);
		}

	}

	private void walletAction(Double amount, EwalletType ewalletType, String userId, EtransactionType etransactionType,
			String remark) {
		PtmTxn ptmTxn = transactionService.initiateTransaction(remark, etransactionType, userId);
		UserWalletActionDTO userWalletActionDTO = UserWalletActionDTO.builder().amount(amount).userId(userId)
				.remark(remark).serviceType("SEND_MONEY").ewalletType(ewalletType).txnId(ptmTxn.getTxnUuid()).build();
		if (etransactionType.equals(EtransactionType.DEBIT)) {
			userWalletActionDTO.setAmount(amount);
			userWalletService.userWalletDebit(userWalletActionDTO);

		} else if (etransactionType.equals(EtransactionType.CREDIT)) {
			userWalletActionDTO.setAmount(amount);
			userWalletService.userWalletDeposit(userWalletActionDTO);
		}
		transactionService.doneTransaction(ptmTxn);

	}

	private void validateUserBal(String userId, EtransactionType etransactionType, EwalletType ewalletType,
			Double amount) {
		boolean istrasferVaild = etransactionType.equals(EtransactionType.DEBIT)
				&& userWalletService.getUserBalInWallet(userId, ewalletType) < amount;
		if (istrasferVaild) {
			throw new UserServiceException(UserExceptionCodeAndMassage.USER_WALLET_AMOUNT_INSUFFICIENT);
		}
	}

}
