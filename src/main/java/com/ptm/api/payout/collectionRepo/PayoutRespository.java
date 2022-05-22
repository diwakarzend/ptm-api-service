package com.ptm.api.payout.collectionRepo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ptm.api.payout.collections.PayoutCollection;

public interface PayoutRespository extends MongoRepository<PayoutCollection , String>{

}
