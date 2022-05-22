package com.ptm.api.user.collectionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ptm.api.user.collections.BeneficiarylCollection;

public interface BeneficiaryCollectionRepository extends MongoRepository<BeneficiarylCollection, String> {
	
	Optional<BeneficiarylCollection> findByAccountNumber(String accountNumber);
	
	Optional<BeneficiarylCollection> findByAccountNumberAndStatus(String accountNumber,String status);
	
	List<BeneficiarylCollection> findByPtmUseruuidAndStatus(Pageable pageable,String ptmUserUuid,String status);
	
	Optional<BeneficiarylCollection> findByPtmUseruuidAndAccountNumberAndStatus(String userUuid,String accountNumber,String status);



}
