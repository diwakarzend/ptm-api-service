package com.ptm.api.user.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptm.api.exception.code.SuccessCode;
import com.ptm.api.user.controller.dto.AddBeneficiaryDTO;
import com.ptm.api.user.controller.dto.UserDTO;
import com.ptm.api.user.controller.dto.UserIpDto;
import com.ptm.api.user.controller.vo.CustomResponse;
import com.ptm.api.user.service.ManageBeneficiaryService;
import com.ptm.api.user.service.UserService;


@RestController
@RequestMapping("/api")
public class UserResourceController {

	private final Logger log = LoggerFactory.getLogger(UserResourceController.class);

	private final UserService userService;
	
	@Autowired
	private ManageBeneficiaryService manageBeneficiaryService;  

	public UserResourceController(UserService userService) {

		this.userService = userService;
	}

	/**
	 * POST /users : Creates a new user.
	 * <p>
	 * Creates a new user if the login and email are not already used, and sends an
	 * mail with an activation link. The user needs to be activated on creation.
	 * 
	 */
	@PostMapping("/users")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.VENDOR })
	public ResponseEntity<CustomResponse> createUser(@Valid @RequestBody UserDTO userDTO) {
		log.info("REST request to save User : {}", userDTO);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.USER_CREATED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.USER_CREATED_SUCCESSFULLY.getSuccessMessage(), userService.createUser(userDTO)),
				HttpStatus.OK);

	}

	/**
	 * PUT /users : Updates an existing User.
	 *
	 * @param userDTO the user to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         user
	 *
	 */
	@PutMapping("/update-users")
	//@Secured(AuthoritiesConstants.ADMIN)
	public ResponseEntity<CustomResponse> updateUser(@Valid @RequestBody UserDTO userDTO) {
		log.info("request to update User : {}", userDTO);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PROFILE_UPDATED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PROFILE_UPDATED_SUCCESSFULLY.getSuccessMessage(), userService.updateUser(userDTO)),
				HttpStatus.OK);
	}

	/**
	 * GET /users : get all users.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all users
	 */
	@GetMapping("/users")
	//@Secured({AuthoritiesConstants.VENDOR,AuthoritiesConstants.ADMIN })
	public ResponseEntity<CustomResponse> getAllUsers(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());
		final Page<UserDTO> page = userService.getAllManagedUsers(pageable);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.PROFILE_UPDATED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.PROFILE_UPDATED_SUCCESSFULLY.getSuccessMessage(), page.getContent()),
				HttpStatus.OK);
	}

	
	/**
	 * GET /users/:username : get the "username" user.
	 *
	 * @param login the login of the user to find
	 * @return the ResponseEntity with status 200 (OK) and with body the "username"
	 *         user, or with status 404 (Not Found)
	 */
	@GetMapping("/users/search/{username}")
	public ResponseEntity<CustomResponse> getUser(@PathVariable String username) {
		log.info("REST request to get User : {}", username);

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.USER_SERACH_RESULT_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.USER_SERACH_RESULT_SUCCESSFULLY.getSuccessMessage(),
						userService.getUserByUserName(username)),
				HttpStatus.OK);
	}

	/**
	 * DELETE /users/:login : delete the "login" User.
	 *
	 * @param login the login of the user to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/users/{username}")
	//@Secured(AuthoritiesConstants.ADMIN)
	public ResponseEntity<CustomResponse> deleteUser(@PathVariable String login) {
		log.debug("REST request to delete User: {}", login);
		userService.deleteUser(login);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.USER_DELETED_SUCCESSFULLY.getSuccessCode(),
						SuccessCode.USER_DELETED_SUCCESSFULLY.getSuccessMessage()),
				HttpStatus.OK);
	}
	
	
	@PostMapping("/add-beneficiary")
	public ResponseEntity<CustomResponse> createBeneficiary(@Valid @RequestBody AddBeneficiaryDTO addBeneficiaryDTO) {
		manageBeneficiaryService.addBeneficiary(addBeneficiaryDTO);
		return new ResponseEntity<CustomResponse>(new CustomResponse(SuccessCode.CREATED_SUCCESSFULLY.getSuccessCode(),
				SuccessCode.CREATED_SUCCESSFULLY.getSuccessMessage()), HttpStatus.OK);
	}
	
	
	@PutMapping("/update-beneficiary")
	public ResponseEntity<CustomResponse> updateBeneficiary(@Valid @RequestBody AddBeneficiaryDTO addBeneficiaryDTO) {
		manageBeneficiaryService.updateBeneficiary(addBeneficiaryDTO);
		return new ResponseEntity<CustomResponse>(new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
				SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete-beneficiary")
	public ResponseEntity<CustomResponse> deleteBeneficiary(@RequestParam(name = "accountNumber") String accountNumber) {
		manageBeneficiaryService.deleteBeneficiary(accountNumber);
		return new ResponseEntity<CustomResponse>(new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
				SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()), HttpStatus.OK);
	}
	
	
	@GetMapping("/beneficiary")
	public ResponseEntity<CustomResponse> getBeneficiary(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());
		
		return new ResponseEntity<CustomResponse>(new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
				SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),manageBeneficiaryService.getBeneficiary(pageable)
				), HttpStatus.OK);
	}
	
	@PostMapping("/update-ip")
	public ResponseEntity<CustomResponse> updateBeneficiary(@RequestBody UserIpDto userIpDto) {
		userService.updateIp(userIpDto);
		return new ResponseEntity<CustomResponse>(new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
				SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()
				), HttpStatus.OK);
	}
	
	
	@GetMapping("/ip")
	public ResponseEntity<CustomResponse> getIps(@RequestParam(required = false,name = "username") String username,@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());

		return new ResponseEntity<CustomResponse>(new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
				SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage(),userService.getUserIps(username,pageable)
				), HttpStatus.OK);
	}
	


}
