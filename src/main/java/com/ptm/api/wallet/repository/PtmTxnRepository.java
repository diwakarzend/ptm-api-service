package com.ptm.api.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.wallet.entity.PtmTxn;

public interface PtmTxnRepository extends JpaRepository<PtmTxn,String> {

	PtmTxn findOneByTxnUuid(String txnUuid);
}
