package com.ptm.api.catalog.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommissionRangeDto {
	
	private Double minAepsAmount;
	private Double maxAepsAmount;
	private String commissionType;
	private List<UserCommissonDto> userHierarchyComission;

}
