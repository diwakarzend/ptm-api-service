package com.ptm.api.wallet.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.config.constant.EtransactionType;
import com.ptm.api.config.jwt.SecurityUtils;
import com.ptm.api.wallet.entity.PtmTxn;
import com.ptm.api.wallet.repository.PtmTxnRepository;

@Service
public class TransactionService {

	@Autowired
	private PtmTxnRepository ptmTxnRepository;

	public PtmTxn initiateTransaction(String remark, EtransactionType eTransactionType,String userId) {
		PtmTxn ptmTxn = new PtmTxn();
		ptmTxn.setTxnStatus(EtransactionStatus.INITIATED);
		ptmTxn.setTxnType(eTransactionType);
		ptmTxn.setTxnUuid(UUID.randomUUID().toString());
		ptmTxn.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
		ptmTxn.setUserId(userId);
		return ptmTxnRepository.save(ptmTxn);
	}

	public PtmTxn doneTransaction(PtmTxn ptmTxn) {
		ptmTxn.setTxnStatus(EtransactionStatus.DONE);
		ptmTxn.setLastModifiedDate(LocalDateTime.now());
		ptmTxn.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());

		return ptmTxnRepository.save(ptmTxn);
	}
	
	
	public PtmTxn pendingTransaction(PtmTxn ptmTxn) {
		ptmTxn.setTxnStatus(EtransactionStatus.PENDING);
		ptmTxn.setLastModifiedDate(LocalDateTime.now());
		ptmTxn.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());

		return ptmTxnRepository.save(ptmTxn);
	}
	
	
	public PtmTxn failTransaction(PtmTxn ptmTxn) {
		ptmTxn.setTxnStatus(EtransactionStatus.FAIL);
		ptmTxn.setLastModifiedDate(LocalDateTime.now());
		ptmTxn.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());

		return ptmTxnRepository.save(ptmTxn);
	}
	
	public PtmTxn inProcessTransaction(PtmTxn ptmTxn) {
		ptmTxn.setTxnStatus(EtransactionStatus.INPROCESS);
		ptmTxn.setLastModifiedDate(LocalDateTime.now());
		ptmTxn.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
		return ptmTxnRepository.save(ptmTxn);
	}
	
	
	public  PtmTxn findPtmTxnById(String txnId) {
		return ptmTxnRepository.findOneByTxnUuid(txnId);
	}

}
