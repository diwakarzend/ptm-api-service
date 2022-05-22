package com.ptm.api.catalog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptm.api.catalog.collection.PayoutConfigCollection;
import com.ptm.api.catalog.entity.DefaultConfigMaster;
import com.ptm.api.catalog.repository.DefaultConfigMasterRepository;
import com.ptm.api.catalog.service.UserComissionService;

@Service
public class DefaultCondigureServiceImpl {

	@Autowired
	private DefaultConfigMasterRepository defaultConfigMasterRepository;
	
	@Autowired
	private UserComissionService userComissionService;

	public PayoutConfigCollection getDefaultPayoutConfigConfig(String seviceCode, String verdorUsername) {
		DefaultConfigMaster defaultConfigMaster = defaultConfigMasterRepository.findByServiceName(seviceCode);
		defaultConfigMaster.getConfigJson();
		ObjectMapper objectMapper = new ObjectMapper();
		PayoutConfigCollection payoutConfigCollection = null;
		try {
			payoutConfigCollection = objectMapper.readValue(
					defaultConfigMaster.getConfigJson().replace("verndorUserName", verdorUsername),
					PayoutConfigCollection.class);
			userComissionService.payOutDefaultCommission(payoutConfigCollection);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return payoutConfigCollection;
	}

}
