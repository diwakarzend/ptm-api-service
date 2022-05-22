package com.ptm.api.catalog.collectionRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ptm.api.catalog.collection.PayoutConfigCollection;

public interface PayoutConfigRepository extends MongoRepository<PayoutConfigCollection, String> {
	PayoutConfigCollection findByUserName(String userName);

	Page<PayoutConfigCollection> findByUserName(Pageable pageable, String userName);

}
