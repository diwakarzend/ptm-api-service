package com.ptm.api.catalog.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DMTCommissionRangeDto {
	
	private Double minAmount;
	private Double maxAmount;
	private String commissionType;
	private String route; //imps neft
	private String adminId;
	private List<UserCommissonDto> userHierarchyComission;

}
