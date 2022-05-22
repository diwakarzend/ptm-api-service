package com.ptm.api.catalog.dto;

import java.util.List;

import com.ptm.api.catalog.collection.PtmUserServiceMapping;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserHierarchyComissionDto{

	protected String userId;
	
	protected String operatorId;
	
	protected List<PtmUserServiceMapping> userServiceMapping;

	//protected List<UserOperatorComissonDto> userHierarchyComission;
}