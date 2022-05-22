package com.ptm.api.payout.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ptm.api.catalog.dto.PayoutCommissionRangeDto;
import com.ptm.api.catalog.dto.UserCommissonDto;
import com.ptm.api.catalog.service.SmsService;
import com.ptm.api.catalog.service.UserComissionService;
import com.ptm.api.client.NotificationClient;
import com.ptm.api.client.merchant.service.MerchantService;
import com.ptm.api.client.response.ClientPayOutResponseModel;
import com.ptm.api.config.constant.Eflags;
import com.ptm.api.config.constant.EotpType;
import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.config.constant.EtransactionType;
import com.ptm.api.config.constant.EwalletType;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.payout.dto.PayoutDto;
import com.ptm.api.payout.entity.PayOutEntity;
import com.ptm.api.payout.repository.PayOutRequestRepository;
import com.ptm.api.payout.service.mapper.PayOutMapper;
import com.ptm.api.payout.vo.PayoutVo;
import com.ptm.api.wallet.controller.dto.UserWalletActionDTO;
import com.ptm.api.wallet.entity.PtmTxn;
import com.ptm.api.wallet.service.TransactionService;
import com.ptm.api.wallet.service.UserWalletService;

@Service
public class PayOutservice {

	@Autowired
	private UserWalletService userWalletService;

	@Autowired
	private NotificationClient notificationClient;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	UserComissionService userComissionService;

	@Autowired
	private PayOutRequestRepository payOutRequestRepository;

	@Autowired
	private SmsService smsService;

	@Autowired
	private Gson gson;

	@Autowired
	private MerchantService merchantService;

	public PayoutVo payoutInit(PayoutDto payoutDto) {
		//validation benifciary 
		validateUserBalance(Double.parseDouble(payoutDto.getRemittanceAmount()));
		PtmTxn ptmTxn = transactionService.initiateTransaction("payout ", EtransactionType.DEBIT,
				SecurityUtils.getCurrentUserLogin().get());

		List<PayOutEntity> payOutInitList = payOutRequestRepository.findByUserIdAndAccountNumberAndStatus(
				SecurityUtils.getCurrentUserUuid().get(),payoutDto.getAccountNumber(),EtransactionStatus.INITIATED);
		// api key check
		// generate hash

		if (payOutInitList.size() > 0) {
			throw new UserServiceException(UserExceptionCodeAndMassage.PAYOUT_INITIATED);
		}

		PayOutEntity payOutRequestEntity = new PayOutMapper().map(payoutDto, ptmTxn.getTxnUuid());

		if (Double.parseDouble(payoutDto.getRemittanceAmount()) >= 25000) {
			payOutRequestEntity.setApprovalRequired(Eflags.Y);
		} else {
			payOutRequestEntity.setApprovalRequired(Eflags.N);

		}
		payOutRequestEntity.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
		payOutRequestEntity.setCreatedDate(LocalDateTime.now());
		payOutRequestRepository.save(payOutRequestEntity);
		smsService.sendOtp(EotpType.PAYOUT.toString());
		
		PayoutVo payoutVo=new PayoutVo();
		payoutVo.setTnxId(ptmTxn.getTxnUuid());
		
		return payoutVo;
	}

	public void validatePayoutByOtp(String otp,String txnId) {
		
		smsService.verifyOtp(otp, EotpType.PAYOUT.toString());

		PayOutEntity payOut = payOutRequestRepository.findByTxnIdAndStatus(txnId,EtransactionStatus.INITIATED)
				.stream()
				.findFirst()
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.PAYOUT_NOT_FOUND));

		if (payOut.getApprovalRequired().equals(Eflags.Y)) {

			throw new UserServiceException(UserExceptionCodeAndMassage.PAYOUT_APPROVAL_REQUIRED);
		}

		if (LocalDateTime.now().isAfter(payOut.getExpireAt())) {

			payOut.setRemark("transaction expired");
			payOut.setStatus(EtransactionStatus.REJECTED);
			payOut.setLastModifiedDate(LocalDateTime.now());
			payOut.setPayoutChanrge(0D);
			payOut.setProfitAmount(0D);
			payOut.setPayoutChanrge(0D);
			payOutRequestRepository.save(payOut);

			throw new UserServiceException(UserExceptionCodeAndMassage.PAYOUT_REQUEST_EXPIRE);
		}
		
		String currentUserLogin = SecurityUtils.getCurrentUserLogin().get();

		List<PayoutCommissionRangeDto> commissionRangeDto = userComissionService.getUserPayoutCommission(currentUserLogin,
				payOut.getRemittanceAmount(), payOut.getRoute());
		Map<Double, PayoutCommissionRangeDto> commisionMap=commissionMerchantMap(commissionRangeDto,payOut.getRemittanceAmount());
		Double totalCommission = commisionMap.entrySet().stream().map(mapper->mapper.getKey()).findFirst().get();
		PayoutCommissionRangeDto payoutCommissionRangeDto = commisionMap.entrySet().stream().map(mapper->mapper.getValue()).findFirst().get();
		double openingBalance=validateUserBalance(payOut.getRemittanceAmount());

		PtmTxn ptmTxn = transactionService.findPtmTxnById(payOut.getTxnId());

		

		UserWalletActionDTO userWalletActionDTO = UserWalletActionDTO.builder()
				.amount(payOut.getRemittanceAmount() + totalCommission)
				.userId(SecurityUtils.getCurrentUserLogin().get()).remark("payout").serviceType("PAY_OUT")
				.ewalletType(EwalletType.MAIN_WALLET).txnId(ptmTxn.getTxnUuid()).build();
		// service type should be api code

		userWalletService.userWalletDebit(userWalletActionDTO);
		ClientPayOutResponseModel response = payOutonMerchant(payOut, ptmTxn,userWalletActionDTO, payoutCommissionRangeDto.getMerchantApiCode());

		commissionDistribution(payoutCommissionRangeDto.getCommissionType(), payoutCommissionRangeDto.getUserHierarchyComission(),
				payOut.getRemittanceAmount());

		payOut.setMerchantCode(payoutCommissionRangeDto.getMerchantApiCode());
		updatePayout(payOut, totalCommission,openingBalance, response,payoutCommissionRangeDto.getMinAmount(),payoutCommissionRangeDto.getMaxAmount());

		transactionService.doneTransaction(ptmTxn);
	}
	
	private Map<Double, PayoutCommissionRangeDto> commissionMerchantMap(List<PayoutCommissionRangeDto> commissionRangeDto,
			Double amount) {

		Map<Double, PayoutCommissionRangeDto> map = new HashMap<>();
		double maxCommission = 0;
		PayoutCommissionRangeDto payoutCommissionRangeDto = new PayoutCommissionRangeDto();
		for (PayoutCommissionRangeDto obj : commissionRangeDto) {
			double commissionSum = 0;
			if ("PERCENTAGE".equals(obj.getCommissionType())) {
				for (UserCommissonDto innerObj : obj.getUserHierarchyComission()) {
					commissionSum = commissionSum + (amount * innerObj.getCommission()) / 100;
				}

			} else {
				for (UserCommissonDto innerObj : obj.getUserHierarchyComission()) {
					commissionSum = commissionSum + innerObj.getCommission();
				}
			}

			if (maxCommission < commissionSum) {
				maxCommission = commissionSum;
				payoutCommissionRangeDto = obj;
			}

		}

		map.put(maxCommission, payoutCommissionRangeDto);

		return map;
	}
	

	
	

	private void updatePayout(PayOutEntity payOut, Double totalCommission,Double openingBalance,
			ClientPayOutResponseModel response,double minRange,double maxRange) {
		Double merchantCommision=merchantService.getPayOutConfigAmount(minRange,maxRange);
		payOut.setClientPayoutChanrge(merchantCommision);
		payOut.setPayoutChanrge(totalCommission);
		payOut.setProfitAmount(totalCommission - merchantCommision);
		payOut.setMerchantTxnId(response.getRrn());
		payOut.setPayoutChanrge(totalCommission);
		payOut.setLastModifiedDate(LocalDateTime.now());
		payOut.setOpeningBalance(openingBalance);
		payOut.setClosingBalance(openingBalance-totalCommission-payOut.getRemittanceAmount());
		payOut.setStatus(EtransactionStatus.DONE);
		payOutRequestRepository.save(payOut);

	}

	private ClientPayOutResponseModel payOutonMerchant(PayOutEntity payOut, PtmTxn ptmTxn,UserWalletActionDTO userWalletActionDTO,String merchantApicode) {
		PayoutDto payout =preparePayoutDto(payOut);
		payout.setMerchantCode(merchantApicode);
		System.out.println(payout);
		payout.setRemittanceAmount("11");
		//String payOutServerResponse = notificationClient.payout(payout);
		//ClientPayOutResponseModel response = gson.fromJson(payOutServerResponse, ClientPayOutResponseModel.class);
		ClientPayOutResponseModel response =new ClientPayOutResponseModel();
		response.setRrn(UUID.randomUUID().toString());
		response.setStatus("SUCCESS");
		if (response.getStatus().equals(EtransactionStatus.PENDING.toString())) {
			payOut.setStatus(EtransactionStatus.PENDING);
			transactionService.pendingTransaction(ptmTxn);
			throw new UserServiceException(UserExceptionCodeAndMassage.CLIENT_PAYOUT_PENDING, response.getMessage());

		} else if (response.getStatus().equals(EtransactionStatus.FAILED.toString())) {
			transactionService.failTransaction(ptmTxn);
			payOut.setStatus(EtransactionStatus.FAIL);
			payOut.setRemark("refund on payout");
			payOutRequestRepository.save(payOut);
			userWalletActionDTO.setRemark("refund on payout");
			userWalletService.userWalletDeposit(userWalletActionDTO);
			throw new UserServiceException(UserExceptionCodeAndMassage.CLIENT_PAYOUT_FAIL, response.getMessage());

		}
		return response;
	}

	private PayoutDto preparePayoutDto(PayOutEntity payOut) {
		return PayoutDto.builder().accountNumber(payOut.getAccountNumber()).beneficiaryName(payOut.getBeneficiaryName())
				.clientId(payOut.getTxnId()).ifscCode(payOut.getIfscCode()).mobileNumber(payOut.getMobileNumber())
				.remittanceAmount(payOut.getRemittanceAmount().toString()).route(payOut.getRoute())
				.type(payOut.getType()).build();
	}

	private double validateUserBalance(Double requestedAmount) {
		Double balance = userWalletService.getUserBalInWallet(SecurityUtils.getCurrentUserLogin().get(),
				EwalletType.MAIN_WALLET);
		if (balance < 500 ) {
			throw new UserServiceException(UserExceptionCodeAndMassage.PAYOUT_FAIL);
		}else if(balance<requestedAmount) {
			throw new UserServiceException(UserExceptionCodeAndMassage.PAYOUT_REQUEST_ERROR);

		}
		return balance;
	}

	private void commissionDistribution(String commissionType,  List<UserCommissonDto> userCommissonDto, Double amount) {

		for (UserCommissonDto comissonDto : userCommissonDto) {
			double aepsommission = calculateCommsision(commissionType, amount, comissonDto.getCommission());
			userComissiondeposit(aepsommission, "Commission on PayOut", "PAY_OUT", comissonDto.getUuid());
		}
		
	}

	private void userComissiondeposit(Double commission, String remark, String serviceType, String userId) {
		PtmTxn ptmTxn = transactionService.initiateTransaction(remark, EtransactionType.CREDIT, userId);
		UserWalletActionDTO userWalletActionDTO = new UserWalletActionDTO();
		userWalletActionDTO.setAmount(commission);
		userWalletActionDTO.setRemark(remark);
		userWalletActionDTO.setUserId(userId);
		userWalletActionDTO.setServiceType(serviceType);
		userWalletActionDTO.setTxnId(ptmTxn.getTxnUuid());
		userWalletActionDTO.setEwalletType(EwalletType.MAIN_WALLET);
		userWalletService.userWalletDeposit(userWalletActionDTO);
		transactionService.doneTransaction(ptmTxn);

	}

	private double calculateCommsision(String commissionType, Double amount, Double comisson) {
		if (commissionType.equals("PERCENTAGE")) {
			return (amount * comisson) / 100;
		} else {
			return comisson;
		}
	}

	public void rejectPayOutRequest(String txnId) {
		PayOutEntity payOut =payOutRequestRepository.findByTxnIdAndStatus(txnId,EtransactionStatus.INITIATED)
				.stream()
				.findFirst()
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.PAYOUT_NOT_FOUND));

		payOut.setStatus(EtransactionStatus.REJECTED);
		payOut.setRemark("rejected by requested user");
		payOut.setLastModifiedDate(LocalDateTime.now());
		payOut.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
		payOut.setPayoutChanrge(0D);
		payOut.setProfitAmount(0D);
		payOut.setPayoutChanrge(0D);
		payOutRequestRepository.save(payOut);

	}
	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void rejectScheduling() {
		List<PayOutEntity> payOutList = payOutRequestRepository.findByStatus(EtransactionStatus.INITIATED);

		for (PayOutEntity payOut : payOutList) {
			if (LocalDateTime.now().isAfter(payOut.getExpireAt())) {
				payOut.setStatus(EtransactionStatus.REJECTED);
				payOut.setRemark("rejected by requested user");
				payOut.setLastModifiedDate(LocalDateTime.now());
				payOut.setLastModifiedBy("SYSTEM");
				payOut.setPayoutChanrge(0D);
				payOut.setProfitAmount(0D);
				payOut.setPayoutChanrge(0D);
				payOutRequestRepository.save(payOut);
			}
		}

	}
	
	
	public void payoutResendOtp(String txnId) {
		payOutRequestRepository.findByTxnIdAndStatus(txnId,EtransactionStatus.INITIATED)
				.stream()
				.findFirst()
				.orElseThrow(() -> new UserServiceException(UserExceptionCodeAndMassage.PAYOUT_NOT_FOUND));
		smsService.sendOtp(EotpType.PAYOUT.toString());

	}

}
