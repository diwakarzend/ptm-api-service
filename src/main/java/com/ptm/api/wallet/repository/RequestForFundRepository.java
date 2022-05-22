package com.ptm.api.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.wallet.entity.RequestForFund;

public interface RequestForFundRepository extends JpaRepository<RequestForFund,String> {
	
	public Optional<RequestForFund> findByReqstfunduuidAndRequesterParentId(String uuid,String requesterParentId);
	
	public List<RequestForFund> findByRequesterParentId(Pageable pageable,String requesterParentId);
	
	public List<RequestForFund> findByFromReqstUserIdAndApproveStatus(String userId,EtransactionStatus approveStatus);
	

}
