package com.ptm.api.integration.mgmt.collectionrepo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ptm.api.integration.mgmt.collections.ClientApiCollection;

public interface AepsCollectionRepo extends MongoRepository<ClientApiCollection, String> {

	ClientApiCollection findByApiName(String apitype);
	ClientApiCollection findByApiNameAndMerchantCode(String apitype,String merchantCode);


}
