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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptm.api.exception.code.SuccessCode;
import com.ptm.api.user.controller.UserResourceController;
import com.ptm.api.user.controller.dto.UserDTO;
import com.ptm.api.user.controller.vo.CustomResponse;
import com.ptm.api.user.service.UserService;

@RestController
@RequestMapping("/api/ptp/")
public class PtpResourceController {
	
	private final Logger log = LoggerFactory.getLogger(UserResourceController.class);

	private final UserService userService;

	public PtpResourceController(UserService userService) {

		this.userService = userService;
	}

	/**
	 * POST /createptp : Creates a new ptp.
	 * <p>
	 * Creates a new ptp
	 * 
	 */
	@PostMapping("/create-ptp")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.VENDOR })
	public ResponseEntity<CustomResponse> createPtp(@Valid @RequestBody UserDTO userDTO) {
		log.info("REST request to save User : {}", userDTO);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PTP_CREATED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PTP_CREATED_SUCCESSFULLY.getSuccessMessage(), userService.createUser(userDTO)),
				HttpStatus.OK);

	}

	/**
	 * PUT /users : Updates an existing ptp.
	 *
	 * @param ptpDTO the ptp to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         user
	 *
	 */
	@PutMapping("/update-ptp")
	//@Secured(AuthoritiesConstants.ADMIN)
	public ResponseEntity<CustomResponse> updatePtp(@Valid @RequestBody UserDTO userDTO) {
		log.info("request to update User : {}", userDTO);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PTP_UPDATE_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PTP_UPDATE_SUCCESSFULLY.getSuccessMessage(), userService.updateUser(userDTO)),
				HttpStatus.OK);
	}

	/**
	 * GET /users : get all ptp.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all users
	 */
	@GetMapping("/ptp-details")
	public ResponseEntity<CustomResponse> getAllPtps(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());
		final Page<UserDTO> page = userService.getAllManagedUsers(pageable);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PTP_SERACH_RESULT_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PTP_SERACH_RESULT_SUCCESSFULLY.getSuccessMessage(), page.getContent()),
				HttpStatus.OK);
	}

	
}
