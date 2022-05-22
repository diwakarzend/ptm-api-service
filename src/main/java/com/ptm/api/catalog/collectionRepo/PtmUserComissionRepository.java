package com.ptm.api.catalog.collectionRepo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ptm.api.catalog.collection.PtmUserComissionEntity;

public interface PtmUserComissionRepository extends MongoRepository<PtmUserComissionEntity, String> {

	public PtmUserComissionEntity findAllByUserId(String userId);
}
