package com.ptm.api.catalog.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommissionDto {
	private String id;
	private String userName;
	private String opCode;
	private String serviceType;
	private Map<String,Double> userComissionMap;

}
