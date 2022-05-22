package com.ptm.api.payout.vo;

import java.util.List;
import java.util.Map.Entry;

import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.payout.entity.PayOutEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusTransactionReportVo {

	private EtransactionStatus status;

	private int count;

	private double totalTransaction;

	public StatusTransactionReportVo(Entry<EtransactionStatus, List<PayOutEntity>> mapper) {
		
		this.status=mapper.getKey();
		
		mapper.getValue().forEach(action->{
			
			this.count++;
			this.totalTransaction=this.totalTransaction+action.getRemittanceAmount();
		});
	}
	
	


}
