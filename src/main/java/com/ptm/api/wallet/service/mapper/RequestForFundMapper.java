package com.ptm.api.wallet.service.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ptm.api.config.constant.EfundRequestType;
import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.user.entity.User;
import com.ptm.api.wallet.controller.dto.FundRequestDTO;
import com.ptm.api.wallet.entity.RequestForFund;


public class RequestForFundMapper {
	
	
	public RequestForFund map(FundRequestDTO fundRequestDTO,User user) {
		RequestForFund requestForFund=new RequestForFund();
		if(EfundRequestType.CASH.toString().equals(fundRequestDTO.getPayementMode())) {
			requestForFund.setPayementMode(EfundRequestType.CASH.toString());
			requestForFund.setFromBank("NA");
			requestForFund.setToBank("NA");
			requestForFund.setTransationRefNo("NA");
		}else {
			requestForFund.setPayementMode(EfundRequestType.NET_BANKING.toString());
			requestForFund.setFromBank(fundRequestDTO.getFromBank());
			requestForFund.setToBank(fundRequestDTO.getToBank());
			requestForFund.setTransationRefNo(fundRequestDTO.getTransationRefNo());
		}
		requestForFund.setApproveStatus(EtransactionStatus.INITIATED);
		requestForFund.setTxnAmt(fundRequestDTO.getRequestAmount());
		requestForFund.setRequesterParentId(user.getParentId());
		requestForFund.setFromReqstUserId(user.getUsername());
		requestForFund.setReqstfunduuid(UUID.randomUUID().toString());
		requestForFund.setCreatedBy(user.getUsername());
		requestForFund.setCreatedDate(LocalDateTime.now());
		return requestForFund;
	}

}
