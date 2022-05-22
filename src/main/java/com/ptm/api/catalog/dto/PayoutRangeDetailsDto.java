package com.ptm.api.catalog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayoutRangeDetailsDto {
	

	private Double minAmount;
	private Double maxAmount;
	private String commissionType;
	private  Double comission;
	private String merchantApiCode;
	
	public PayoutRangeDetailsDto(PayoutCommissionRangeDto mapper) {
		
		this.minAmount=mapper.getMinAmount();
		this.maxAmount=mapper.getMaxAmount();
		this.commissionType=mapper.getCommissionType();
		/*
		 * this.comission=mapper.getUserHierarchyComission()
		 * .stream().collect(Collectors.toMap(UserCommissonDto::getUuid,
		 * UserCommissonDto::getCommission));
		 */
		this.comission=mapper.getUserHierarchyComission().stream().mapToDouble(mp->mp.getCommission()).sum();
		this.merchantApiCode=mapper.getMerchantApiCode();
	}
 

}
