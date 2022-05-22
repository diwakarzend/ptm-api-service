package com.ptm.api.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCommissonDto extends UserDeatilsDto{
	
	private Double commission;


	
}
