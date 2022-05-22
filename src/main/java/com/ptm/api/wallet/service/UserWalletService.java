package com.ptm.api.wallet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ptm.api.catalog.service.SmsService;
import com.ptm.api.config.constant.EtransactionType;
import com.ptm.api.config.constant.EwalletType;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.wallet.controller.dto.UserWalletActionDTO;
import com.ptm.api.wallet.controller.vo.UserWalletTransactionVO;
import com.ptm.api.wallet.entity.PtmTxn;
import com.ptm.api.wallet.entity.UserWallet;
import com.ptm.api.wallet.entity.UserWalletActivity;
import com.ptm.api.wallet.repository.UserWalletActivityRepository;
import com.ptm.api.wallet.repository.UserWalletRepository;
import com.ptm.api.wallet.service.mapper.UserWalletActivityMapper;
import com.ptm.api.wallet.service.mapper.UserWalletMapper;

@Service
public class UserWalletService {

	@Autowired
	private UserWalletRepository userWalletRepository;

	@Autowired
	private UserWalletActivityRepository userWalletActivityRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private SmsService smsService;

	public Map<EwalletType, Double> getUserWallet() {
		List<UserWallet> userWallet = userWalletRepository.findByUserid(SecurityUtils.getCurrentUserLogin().get());

		return userWallet.stream()
				.collect(Collectors.toMap(UserWallet::getWalletType, UserWallet::getBalanceInBaseCurrency));

	}

	public void createUserWallet(String userId,EwalletType ewalletType) {
		// to do transaction insert
		validateUserWallet(userId,ewalletType);
		PtmTxn ptmTxn = transactionService.initiateTransaction("user registration " + userId, EtransactionType.CREDIT,userId);
		UserWalletMapper userWalletMapper = new UserWalletMapper();
		UserWallet userWallet = userWalletMapper.mapUserWallet(userId, 0D,ewalletType);
		UserWallet userWalletSaved = userWalletRepository.save(userWallet);
		saveUserWalletActivity(userId, userWalletSaved, EtransactionType.CREDIT, 0D, 0D,
				"user wallet created with 0 amount ", "NONE", ptmTxn.getTxnUuid());
		transactionService.doneTransaction(ptmTxn);
	}
	
	

	public List<UserWalletTransactionVO> walletTransaction(String walletType,Pageable pageable) {
		List<UserWalletActivity> userWalletList = userWalletActivityRepository
				.findByUseridAndBusinessWalletWalletType(SecurityUtils.getCurrentUserLogin().get(), EwalletType.valueOf(walletType), pageable);

		return userWalletList.stream().map(mapper -> new UserWalletTransactionVO(mapper)).collect(Collectors.toList());
	}

	public List<UserWalletTransactionVO> userWalletTransaction(String userId, Pageable pageable) {
		List<UserWalletActivity> userWalletList = userWalletActivityRepository.findByUserid(userId, pageable);

		return userWalletList.stream().map(mapper -> new UserWalletTransactionVO(mapper)).collect(Collectors.toList());
	}

	public void userWalletDeposit(UserWalletActionDTO userWalletDepositDTO) {
		// to do transaction insert
		
		EwalletType ewalletType=(userWalletDepositDTO.getEwalletType()==null)? EwalletType.MAIN_WALLET: userWalletDepositDTO.getEwalletType();
		System.out.println(ewalletType);
		System.out.println(userWalletDepositDTO);
		UserWallet userWallet = userWalletRepository
				.findByUseridAndWalletType(userWalletDepositDTO.getUserId(), ewalletType)
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND));

		saveUserWalletActivity(userWalletDepositDTO.getUserId(), userWallet,EtransactionType.CREDIT,userWalletDepositDTO.getAmount(),
				userWallet.getBalanceInBaseCurrency(),userWalletDepositDTO.getRemark(),userWalletDepositDTO.getServiceType(),userWalletDepositDTO.getTxnId());

		Double totalAmount = userWallet.getBalanceInBaseCurrency() + userWalletDepositDTO.getAmount();
		userWallet.setBalanceInBaseCurrency(totalAmount);
		userWallet.setBalanceInConsumerCurrency(totalAmount);
	    userWalletRepository.save(userWallet);
	    
	    smsService.sendWalletTransSms(userWalletDepositDTO, totalAmount, "CREDIT");


	}
	
	
	
	@Transactional
	public void userWalletDebit(UserWalletActionDTO userWalletActionDTO) {
		// to do transaction insert
		EwalletType ewalletType=(userWalletActionDTO.getEwalletType()==null)? EwalletType.MAIN_WALLET: userWalletActionDTO.getEwalletType();

		UserWallet userWallet = userWalletRepository
				.findByUseridAndWalletType(userWalletActionDTO.getUserId(), ewalletType)
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND));

		saveUserWalletActivity(userWalletActionDTO.getUserId(), userWallet, EtransactionType.DEBIT,
				-userWalletActionDTO.getAmount(), userWallet.getBalanceInBaseCurrency(),
				userWalletActionDTO.getRemark(), userWalletActionDTO.getServiceType(),userWalletActionDTO.getTxnId());

		if (userWallet.getBalanceInBaseCurrency() < userWalletActionDTO.getAmount()) {

			throw new UserServiceException(UserExceptionCodeAndMassage.USER_WALLET_AMOUNT_INSUFFICIENT);
		}
		Double totalAmount = userWallet.getBalanceInBaseCurrency() - userWalletActionDTO.getAmount();
		userWallet.setBalanceInBaseCurrency(totalAmount);
		userWallet.setBalanceInConsumerCurrency(totalAmount);
		userWallet.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
		userWallet.setLastModifiedDate(LocalDateTime.now());
		userWalletRepository.save(userWallet);
	    smsService.sendWalletTransSms(userWalletActionDTO, totalAmount, "DEBIT");


	}

	private void saveUserWalletActivity(String userId, UserWallet userWallet,EtransactionType etransactionType,
			Double transactionAmount, Double userBal, String remark, String serviceType,String txnId) {
		UserWalletActivityMapper userWalletActivityMapper = new UserWalletActivityMapper();
		UserWalletActivity userWalletActivity = userWalletActivityMapper.mapUserWalletActivity(userId, etransactionType,
				transactionAmount, userBal + transactionAmount, remark,serviceType,txnId);
		userWalletActivity.setBusinessWallet(userWallet);
		userWalletActivityRepository.save(userWalletActivity);
	}
	
	
	
	public List<UserWalletTransactionVO> getServiceTypeTransaction(String serviceType ,Pageable pageable) {
		/// validation service type
		List<UserWalletActivity> userWalletList = userWalletActivityRepository
				.findByUseridAndServiceType(SecurityUtils.getCurrentUserLogin().get(),serviceType, pageable);

		return userWalletList.stream().map(mapper -> new UserWalletTransactionVO(mapper)).collect(Collectors.toList());
	}
	
	public void validateUserWallet(String userName, EwalletType ewalletType) {
		Optional<UserWallet> userWallet = userWalletRepository.findByUseridAndWalletType(userName, ewalletType);

		if (userWallet.isPresent()) {
			throw new UserServiceException(UserExceptionCodeAndMassage.USER_WALLET_VALIDATION);

		}
	}
	
	public Double getUserBalInWallet(String userName,EwalletType ewalletType) {
		Optional<UserWallet> userWallet = userWalletRepository.findByUseridAndWalletType(userName, ewalletType);
		if (!userWallet.isPresent()) {
			throw new UserServiceException(UserExceptionCodeAndMassage.USER_WALLET_NOT_FOUD);

		}else {
			return userWallet.get().getBalanceInBaseCurrency();
		}
		
	}
	
	

}
