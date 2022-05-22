package com.ptm.api.payout.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptm.api.exception.code.SuccessCode;
import com.ptm.api.payout.dto.PayoutDto;
import com.ptm.api.payout.service.PayOutservice;
import com.ptm.api.user.controller.vo.CustomResponse;

@RestController
@RequestMapping("/api/user")
public class PayOutController {

	@Autowired
	private PayOutservice payOutservice;

	@PostMapping("/payout")
	public ResponseEntity<CustomResponse> payout(@RequestBody @Valid PayoutDto payoutDto) {
		
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PAYOUT_INIT_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PAYOUT_INIT_SUCCESSFULLY.getSuccessMessage(),payOutservice.payoutInit(payoutDto)),
				HttpStatus.OK);
	}

	@PutMapping("/payout")
	public ResponseEntity<CustomResponse> payout(@RequestParam(name = "payOutOtp",required = true) String payoutOtp,
			@RequestParam(name = "txnId",required = true) String txnId) {
		payOutservice.validatePayoutByOtp(payoutOtp,txnId);
		return new ResponseEntity<CustomResponse>(new CustomResponse(SuccessCode.PAYOUT_SUCCESSFULLY.getSuccessCode(),
				SuccessCode.PAYOUT_SUCCESSFULLY.getSuccessMessage()), HttpStatus.OK);
	}

	@GetMapping("/reject-payout")
	public ResponseEntity<CustomResponse> rejectPayout(@RequestParam(name = "txnId",required = true) String txnId) {
		payOutservice.rejectPayOutRequest(txnId);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()),
				HttpStatus.OK);
	}
	
	
	@GetMapping("/resendOtp")
	public ResponseEntity<CustomResponse> resendOtp(@RequestParam(name = "txnId",required = true) String txnId) {
		payOutservice.payoutResendOtp(txnId);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()),
				HttpStatus.OK);
	}
}
