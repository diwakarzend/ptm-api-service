package com.ptm.api.ptp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
import com.ptm.api.ptp.dto.PtpDTO;
import com.ptm.api.ptp.service.PtpService;
import com.ptm.api.user.controller.vo.CustomResponse;

/**
 * @author Aman Garg
 *
 */
@RestController
@RequestMapping("/api/ptp/")
public class PtpResourceController {

	private final Logger log = LoggerFactory.getLogger(PtpResourceController.class);

	private final PtpService ptpService;

	public PtpResourceController(PtpService ptpService) {
		this.ptpService = ptpService;
	}

	/**
	 * POST /createptp : Creates a new ptp.
	 * <p>
	 * Creates a new ptp
	 * 
	 * @param ptpDTO the ptp to update
	 * @return the ResponseEntity with status 200 (OK) and with body
	 * 
	 */
	@PostMapping("/create-ptp")
	public ResponseEntity<CustomResponse> createPtp(@Valid @RequestBody PtpDTO ptpDTO) {
		log.info("REST request to save ptp detail : {}", ptpDTO);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PTP_CREATED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PTP_CREATED_SUCCESSFULLY.getSuccessMessage(),
						ptpService.createPTP(ptpDTO)),
				HttpStatus.OK);

	}

	/**
	 * PUT /update-ptp : Updates an existing ptp.
	 *
	 * @param ptpDTO the ptp to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated ptp
	 *
	 */

	/*
	 * @PutMapping("/update-ptp") public ResponseEntity<CustomResponse>
	 * updatePtp(@Valid @RequestBody PtpDTO ptpDTO) {
	 * log.info("request to update ptp : {}", ptpDTO);
	 * 
	 * return new ResponseEntity<CustomResponse>( new
	 * CustomResponse(SuccessCode.PTP_UPDATE_SUCCESSFULLY.getSuccessCode(),
	 * SuccessCode.PTP_UPDATE_SUCCESSFULLY.getSuccessMessage(),
	 * ptpService.updatePtp(ptpDTO)), HttpStatus.OK); }
	 */

	/**
	 * GET /ptp : get all ptp.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all ptp
	 */
	@GetMapping("/ptp-details")
	public ResponseEntity<CustomResponse> getAllPtps(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());
		final Page<PtpDTO> page = ptpService.getAllManagedPtp(pageable);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PTP_SERACH_RESULT_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PTP_SERACH_RESULT_SUCCESSFULLY.getSuccessMessage(), page.getContent()),
				HttpStatus.OK);
	}

}
