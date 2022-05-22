package com.ptm.api.catalog.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DMTConfigDto {

	private String userName;
	private List<DMTCommissionRangeDto> commissionRange;

}
