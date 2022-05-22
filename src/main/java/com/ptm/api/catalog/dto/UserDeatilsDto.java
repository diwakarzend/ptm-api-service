package com.ptm.api.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDeatilsDto {
	
	protected String uuid;
	protected String userType;
	protected boolean isStatus;
	protected int order;

}
