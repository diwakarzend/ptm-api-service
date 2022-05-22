package com.ptm.api.catalog.collectionRepo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ptm.api.catalog.collection.MoneyTransferConfigCollection;

public interface MoneyTransferConfigRespository extends MongoRepository<MoneyTransferConfigCollection, String> {
	MoneyTransferConfigCollection findByUserName(String userName);

}
