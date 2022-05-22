package com.ptm.api.catalog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayoutRouteDto {
	
	private Double minAmount;
	
	private Double maxAmount;
	
	private String routeCode;
	
	private String commissionType;
	
	private  Double commission;
	
	private String userId;

}
