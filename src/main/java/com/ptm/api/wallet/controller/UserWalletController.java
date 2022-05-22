package com.ptm.api.wallet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptm.api.config.AuthoritiesConstants;
import com.ptm.api.config.constant.EtransactionType;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.exception.code.SuccessCode;
import com.ptm.api.user.controller.vo.CustomResponse;
import com.ptm.api.wallet.controller.dto.FundRequestDTO;
import com.ptm.api.wallet.controller.dto.UserWalletActionDTO;
import com.ptm.api.wallet.service.FundRequestService;
import com.ptm.api.wallet.service.UserWalletService;
import com.ptm.api.wallet.service.WalletTransferService;

@RestController
@RequestMapping("/api/wallet")
public class UserWalletController {

	@Autowired
	private UserWalletService userWalletService;
	
	@Autowired
	private WalletTransferService walletTransferService;
	
	@Autowired
	private FundRequestService fundRequestService;

	@GetMapping
	public ResponseEntity<CustomResponse> getUserWallet() {

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.WALLET_FETCHED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.WALLET_FETCHED_SUCCESSFULLY.getSuccessMessage(), userWalletService.getUserWallet()),
				HttpStatus.OK);
	}

	@GetMapping("/transactions")
	public ResponseEntity<CustomResponse> getUserWalletTransaction(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize,  @RequestParam(name = "walletType") String walletType) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.WALLET_FETCHED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.WALLET_FETCHED_SUCCESSFULLY.getSuccessMessage(),
						userWalletService.walletTransaction(walletType,pageable)),
				HttpStatus.OK);
	}
	
	
	@GetMapping("/transactions/serviceType/{serviceType}")
	public ResponseEntity<CustomResponse> getUserServiceTypeTransaction(@PathVariable(name = "serviceType") String serviceType, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.SERVICE_TYPE_TRANSACTIONS_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.SERVICE_TYPE_TRANSACTIONS_SUCCESSFULLY.getSuccessMessage(),
						userWalletService.getServiceTypeTransaction(serviceType,pageable)),
				HttpStatus.OK);
	}

	@GetMapping("/transactions/{userId}")
	@Secured({ AuthoritiesConstants.ADMIN })
	public ResponseEntity<CustomResponse> getWalletTransaction(@PathVariable(name = "userId") String userId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.WALLET_FETCHED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.WALLET_FETCHED_SUCCESSFULLY.getSuccessMessage(),
						userWalletService.userWalletTransaction(userId, pageable)),
				HttpStatus.OK);
	}
	
	
	
	@PostMapping("/deposit")
	@Secured({ AuthoritiesConstants.ADMIN })
	public ResponseEntity<CustomResponse> depositAmount(@RequestBody UserWalletActionDTO userWalletDepositDTO) {

		walletTransferService.transferWalletAmount(userWalletDepositDTO,SecurityUtils.getCurrentUserLogin().get(),
				EtransactionType.DEBIT,EtransactionType.CREDIT);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.DEPOSIT_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.DEPOSIT_SUCCESSFULLY.getSuccessMessage()),
				HttpStatus.OK);
	}
	
	
	@PostMapping("/debit")
	@Secured({ AuthoritiesConstants.ADMIN })
	public ResponseEntity<CustomResponse> debitAmount(@RequestBody @Valid UserWalletActionDTO userWalletActionDTO) {

		walletTransferService.transferWalletAmount(userWalletActionDTO,SecurityUtils.getCurrentUserLogin().get(),
				EtransactionType.CREDIT,EtransactionType.DEBIT);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.DEBIT_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.DEBIT_SUCCESSFULLY.getSuccessMessage()),
				HttpStatus.OK);
	}
	
	
	@PostMapping("/fund-request")
	public ResponseEntity<CustomResponse> fundRequest(@RequestBody @Valid FundRequestDTO fundRequestDTO) {

		fundRequestService.saveFundRequest(fundRequestDTO);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.FUND_REQUEST_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.FUND_REQUEST_SUCCESSFULLY.getSuccessMessage()),
				HttpStatus.OK);
	}

	
	@GetMapping("/fund-approve")
	public ResponseEntity<CustomResponse> fundApprove(@RequestParam(name = "reqstfunduuid") @Valid String reqstfunduuid) {

		fundRequestService.approveFundRequest(reqstfunduuid);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.FUND_APPROVED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.FUND_APPROVED_SUCCESSFULLY.getSuccessMessage()),
				HttpStatus.OK);
	}
	
	@GetMapping("/reject-fund-request")
	public ResponseEntity<CustomResponse> rejectFundRequest(@RequestParam(name = "reqstfunduuid") @Valid String reqstfunduuid) {

		fundRequestService.rejectFundRequest(reqstfunduuid);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()),
				HttpStatus.OK);
	}
	
	
	@GetMapping("/fund-request")
	public ResponseEntity<CustomResponse> fundRequestStatus(@RequestParam(name = "status") @Valid String status) {

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),fundRequestService.userFundRequest(status)),
				HttpStatus.OK);
	}
	
	
	@GetMapping("/fund-request-list")
	public ResponseEntity<CustomResponse> fundRequestList(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),fundRequestService.getFundRequestUserList(pageable)),
				HttpStatus.OK);
	}

}
