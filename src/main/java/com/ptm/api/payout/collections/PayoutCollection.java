package com.ptm.api.payout.collections;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayoutCollection extends CollectionAbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String userName;
	private String txnUuid;
	private Double amount;
	private Double chargeOnPayout;
	
}
