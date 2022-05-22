package com.ptm.api.catalog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayoutUpdateDto {

	private String userId;

	private String route;

	private Double commission;

	private String commissionType;

	private String merchantCode;

	private Double minAmount;

	private Double maxAmount;

}
