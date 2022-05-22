package com.ptm.api.payout.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.payout.entity.PayOutEntity;

public interface PayOutRequestRepository extends JpaRepository<PayOutEntity, Long> , JpaSpecificationExecutor<PayOutEntity>  {

	List<PayOutEntity> findByUserIdAndAccountNumberAndStatus(String userId,String account, EtransactionStatus etransactionStatus);
	List<PayOutEntity> findByTxnIdAndStatus(String txnId, EtransactionStatus etransactionStatus);
	List<PayOutEntity> findByStatus(EtransactionStatus etransactionStatus);

	List<PayOutEntity> findByUserId(String userId);


}
