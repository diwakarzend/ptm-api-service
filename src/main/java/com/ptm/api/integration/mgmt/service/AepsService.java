 package com.ptm.api.integration.mgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptm.api.integration.mgmt.collectionrepo.AepsCollectionRepo;
import com.ptm.api.integration.mgmt.collections.ClientApiCollection;
import com.ptm.api.integration.mgmt.dto.ClientApiDTO;

@Service
public class AepsService {
	
	@Autowired
	private AepsCollectionRepo aepsCollectionRepo;
	
	public void save(ClientApiDTO aepsClientApiDTO) {
		ClientApiCollection aepsCollection=ClientApiCollection.builder()
				.apiName(aepsClientApiDTO.getApiName())
				.baseUrl(aepsClientApiDTO.getBaseUrl())
				.cleintId(aepsClientApiDTO.getCleintId())
				.requestType(aepsClientApiDTO.getRequestType())
				.parameters(aepsClientApiDTO.getParameters())
				.merchantCode(aepsClientApiDTO.getMerchantCode())
				.build();
		aepsCollectionRepo.save(aepsCollection);
	}

}
