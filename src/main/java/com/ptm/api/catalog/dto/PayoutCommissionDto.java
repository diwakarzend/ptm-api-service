package com.ptm.api.catalog.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayoutCommissionDto {

	private String userName;

	private Map<String,List<PayoutCommissionRangeDto>> payoutCommissionList;

}
