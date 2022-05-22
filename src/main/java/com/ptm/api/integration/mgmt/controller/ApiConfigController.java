package com.ptm.api.integration.mgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptm.api.exception.code.SuccessCode;
import com.ptm.api.integration.mgmt.dto.ClientApiDTO;
import com.ptm.api.integration.mgmt.service.AepsService;
import com.ptm.api.user.controller.vo.CustomResponse;

@RestController
@RequestMapping("/merchant-api")
public class ApiConfigController {
	
	@Autowired
	private AepsService aepsService;
	
	@PostMapping("/save")
	public ResponseEntity<CustomResponse>  clientApiConfig(@RequestBody  ClientApiDTO aepsClientApiDTO) {
		aepsService.save(aepsClientApiDTO);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()),
				HttpStatus.OK);
	}

}
