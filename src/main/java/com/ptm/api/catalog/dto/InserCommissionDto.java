package com.ptm.api.catalog.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InserCommissionDto {

	private String id;
	private String userName;
	private String opCode;
	private String serviceType;
	private Map<String,Double> userComissionMap;
	private List<UserCommissonDto> commComissonDtos;
}
