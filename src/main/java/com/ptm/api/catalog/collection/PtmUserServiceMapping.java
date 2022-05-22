package com.ptm.api.catalog.collection;

import java.util.List;
import java.util.Map;

import com.ptm.api.catalog.dto.UserCommissonDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PtmUserServiceMapping {
	
	private String serviceType;
	private String clientId;
	private Boolean enabled; 
	/*
	 * map of operator and its commission
	 */
	private Map<String ,List<UserCommissonDto>> userHierarchyComission;
	
	//protected List<UserOperatorComissonDto> userHierarchyComission;


}
