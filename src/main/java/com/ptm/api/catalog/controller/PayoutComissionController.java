package com.ptm.api.catalog.controller;

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

import com.ptm.api.catalog.dto.PayoutConfigDto;
import com.ptm.api.catalog.dto.PayoutUpdateDto;
import com.ptm.api.catalog.service.UserComissionService;
import com.ptm.api.exception.code.SuccessCode;
import com.ptm.api.user.controller.vo.CustomResponse;

@RestController
@RequestMapping("/api/user/payout")
public class PayoutComissionController {

	@Autowired
	UserComissionService userComissionService;

	@PostMapping("/create")
	public ResponseEntity<CustomResponse> createPayOutConfig(@RequestBody PayoutConfigDto payoutConfigDto) {
		userComissionService.savepayoutCommission(payoutConfigDto);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()),
				HttpStatus.OK);

	}

	@PostMapping("/update-commission")
	public ResponseEntity<CustomResponse> updatePayOutConfig(@RequestBody PayoutUpdateDto payoutUpdateDto) {
		userComissionService.updatePayOut(payoutUpdateDto);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()),
				HttpStatus.OK);

	}

	/*
	 * @PostMapping("/update-route") public ResponseEntity<CustomResponse>
	 * updateRouteConfig(@RequestBody PayoutRouteDto payoutRouteDto) {
	 * userComissionService.updateRoute(payoutRouteDto); return new
	 * ResponseEntity<CustomResponse>( new
	 * CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
	 * SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()), HttpStatus.OK);
	 * 
	 * }
	 */

	@GetMapping("/commission-range")
	public ResponseEntity<CustomResponse> getPayOutRange(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize,
			@RequestParam(required = false) String username) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),
						userComissionService.getPayOutRange(pageable, username)),
				HttpStatus.OK);

	}

}
