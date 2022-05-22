package com.ptm.api.payout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptm.api.exception.code.SuccessCode;
import com.ptm.api.payout.dto.ReportFilterDto;
import com.ptm.api.payout.service.PayOutReportservice;
import com.ptm.api.user.controller.vo.CustomResponse;

@RestController
@RequestMapping("/api/payout")
public class PayoutReportController {
	
	@Autowired
	PayOutReportservice payOutReportservice ;
	
	@PostMapping("/transaction-report")
	public ResponseEntity<CustomResponse> transaction(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize,@RequestBody ReportFilterDto reportFilterDto) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());
		
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),payOutReportservice.fetchReport(reportFilterDto,pageable)),
				HttpStatus.OK);
	}
	
	
	@GetMapping("/dashboard/status-report")
	public ResponseEntity<CustomResponse> transactionStatus() {
		
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),payOutReportservice.transactionStatus()),
				HttpStatus.OK);
	}
	
	
	@GetMapping("/dashboard/monthly-report")
	public ResponseEntity<CustomResponse> transactionMonthly() {
		
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),payOutReportservice.monthlyTransaction()),
				HttpStatus.OK);
	}
	
	
	@GetMapping("/dashboard/status-transaction-report")
	//@Secured("PTM_STATUS_REPORT")
	public ResponseEntity<CustomResponse> statusAndTranAmountReport() {
		
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),payOutReportservice.statusAndTranAmountReport()),
				HttpStatus.OK);
	}

}
