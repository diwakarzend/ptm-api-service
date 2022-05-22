package com.ptm.api.catalog.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayoutConfigDto {

	private String userName;
	private List<PayoutCommissionRangeDto> commissionRange;

}
