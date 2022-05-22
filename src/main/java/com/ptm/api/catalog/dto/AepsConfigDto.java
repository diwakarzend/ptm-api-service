package com.ptm.api.catalog.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AepsConfigDto {

	private String userName;
	private List<CommissionRangeDto> commissionRange;

}
