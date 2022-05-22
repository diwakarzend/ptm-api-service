package com.ptm.api.user.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ptm.api.exception.code.SuccessCode;
import com.ptm.api.user.controller.dto.PasswordChangeDTO;
import com.ptm.api.user.controller.dto.UserDTO;
import com.ptm.api.user.controller.vo.CustomResponse;
import com.ptm.api.user.controller.vo.KeyAndPasswordVM;
import com.ptm.api.user.service.impl.UserServiceImpl;


/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api/user/")
public class AccountResourceController {

	private final UserServiceImpl userService;

	public AccountResourceController(UserServiceImpl userService) {
		this.userService = userService;
	}

	/**
	 * POST /register : register the user.
	 *
	 * @param registerUserDTO the managed user View Model
	 * 
	 */
	/*
	 * @PostMapping("/register")
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) public ResponseEntity<CustomResponse>
	 * registerAccount(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
	 * userService.registerUser(registerUserDTO);
	 * 
	 * return new ResponseEntity<CustomResponse>( new
	 * CustomResponse(SuccessCode.REGISTERED_SUCCESSFULLY.getSuccessCode(),
	 * SuccessCode.REGISTERED_SUCCESSFULLY.getSuccessMessage()), HttpStatus.OK); }
	 */

	/**
	 * GET /activate : activate the registered user.
	 *
	 * @param key the activation key
	 * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be
	 *                          activated
	 */
	/*
	 * @GetMapping("/activate") public ResponseEntity<CustomResponse>
	 * activateAccount(@RequestParam(value = "key") String key) {
	 * userService.activateRegistration(key); return new
	 * ResponseEntity<CustomResponse>( new
	 * CustomResponse(SuccessCode.ACTIVATED_SUCCESSFULLY.getSuccessCode(),
	 * SuccessCode.ACTIVATED_SUCCESSFULLY.getSuccessMessage()), HttpStatus.OK); }
	 */

	/**
	 * GET /account : get the current user.
	 *
	 * @return the current user
	 * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be
	 *                          returned
	 */
	@GetMapping("/account")
	public ResponseEntity<CustomResponse> getAccount() {
		UserDTO userDTO = userService.getUserWithAuthorities();
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PROFILE_UPDATED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PROFILE_UPDATED_SUCCESSFULLY.getSuccessMessage(), userDTO),
				HttpStatus.OK);
	}

	/**
	 * POST /account : update the current user information.
	 *
	 * @param userDTO the current user information
	 * @return
	 * 
	 */
	@PutMapping("/account")
	public ResponseEntity<CustomResponse> saveAccount(@Valid @RequestBody UserDTO userDTO) {

		userService.updateUser(userDTO);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PROFILE_UPDATED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PROFILE_UPDATED_SUCCESSFULLY.getSuccessMessage()),
				HttpStatus.OK);
	}
	
	
	@PutMapping("/user-status")
	public ResponseEntity<CustomResponse> updateUserStatus(@RequestParam(name = "userId") String userId,
			@RequestParam(name = "status") String status) {

		userService.updateUserStatus(userId, status);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()),
				HttpStatus.OK);
	}

	/**
	 * POST /account/change-password : changes the current user's password
	 * 
	 * @return
	 *
	 * 
	 */
	@PostMapping(path = "/account/change-password")
	public ResponseEntity<CustomResponse> changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {

		userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PASSWORD_CHANGED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PASSWORD_CHANGED_SUCCESSFULLY.getSuccessMessage()),
				HttpStatus.OK);
	}

	/**
	 * POST /account/reset-password/init : Send an email to reset the password of
	 * the user
	 * 
	 * @return
	 *
	 * 
	 */
	@GetMapping(path = "/account/reset-password/init")
	public ResponseEntity<CustomResponse> requestPasswordReset(@RequestParam(name = "phoneNumber") String phoneNumber) {

		userService.requestPasswordReset(phoneNumber);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.RESET_PASSWORD_CODE_SEND_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.RESET_PASSWORD_CODE_SEND_SUCCESSFULLY.getSuccessMessage()),
				HttpStatus.OK);
	}

	/**
	 * POST /account/reset-password/finish : Finish to reset the password of the
	 * user
	 *
	 * @param keyAndPassword the generated key and the new password
	 * @return
	 */
	@PostMapping(path = "/account/reset-password/finish")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CustomResponse> finishPasswordReset(@RequestBody @Valid KeyAndPasswordVM keyAndPassword) {

		userService.completePasswordReset(keyAndPassword);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PASSWORD_RESET_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PASSWORD_RESET_SUCCESSFULLY.getSuccessMessage()),
				HttpStatus.OK);

	}

	
	@GetMapping("/financial-details")
	public ResponseEntity<CustomResponse> getFinancialDetails() {

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),
						userService.getUserFinecialDetails()),
				HttpStatus.OK);
	}

	
	@GetMapping("/save-financial-details")
	public ResponseEntity<CustomResponse> saveUpdateFinecialDetails(@RequestParam(name = "brandName", required = true) String brandName,
			@RequestParam(name = "registerCompany", required = true) String registerCompany,@RequestParam(name = "registerAddress", required = true) String registerAddress, 
			@RequestParam(name = "gstNo", required = true) String gstNo, @RequestParam(name = "website", required = true) String website) {
		userService.saveUpdateFinecialDetails(brandName, registerCompany, registerAddress, gstNo, website);
		
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()),
				HttpStatus.OK);
	}

}
