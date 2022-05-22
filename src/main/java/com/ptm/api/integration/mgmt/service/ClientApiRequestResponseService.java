package com.ptm.api.integration.mgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptm.api.integration.mgmt.collectionrepo.ClientApiRequestResponseRepo;
import com.ptm.api.integration.mgmt.collections.ClientApiRequestResponseCollection;

@Service
public class ClientApiRequestResponseService {
	@Autowired
	private ClientApiRequestResponseRepo clientApiRequestResponseRepo;

	public void save(String rquest, String response, String serviceType) {
		ClientApiRequestResponseCollection object = new ClientApiRequestResponseCollection();
		object.setResponseJson(response);
		object.setRequestJson(rquest);
		object.setServiceType(serviceType);
		clientApiRequestResponseRepo.save(object);
	}

}
