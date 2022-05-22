package com.ptm.api.catalog.collectionRepo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ptm.api.catalog.collection.AepsComissionCofigCollection;

public interface AepsComissionCofigRepository  extends MongoRepository<AepsComissionCofigCollection, String> {
	
	AepsComissionCofigCollection findByUserName(String userName);

}
