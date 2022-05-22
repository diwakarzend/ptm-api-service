package com.ptm.api.integration.mgmt.collectionrepo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ptm.api.integration.mgmt.collections.ClientApiRequestResponseCollection;

public interface ClientApiRequestResponseRepo  extends MongoRepository<ClientApiRequestResponseCollection, String>{

}
