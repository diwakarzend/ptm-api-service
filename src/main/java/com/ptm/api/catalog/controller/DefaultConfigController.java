package com.ptm.api.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptm.api.catalog.entity.DefaultConfigMaster;
import com.ptm.api.catalog.repository.DefaultConfigMasterRepository;
import com.ptm.api.exception.code.SuccessCode;
import com.ptm.api.user.controller.vo.CustomResponse;

@RestController
@RequestMapping("/api/default/config")
public class DefaultConfigController {
	

	@Autowired
	private DefaultConfigMasterRepository defaultConfigMasterRepository;

	@PostMapping("/create")
	public ResponseEntity<CustomResponse> create(@RequestBody DefaultConfigMaster defaultConfigMaster) {
		defaultConfigMasterRepository.save(defaultConfigMaster);
		return new ResponseEntity<CustomResponse>(
				new CustomResponse(SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessCode(),
						SuccessCode.GENERAL_SUCCESSFULLY_STATUS.getSuccessMessage()),
				HttpStatus.OK);

	}
}
