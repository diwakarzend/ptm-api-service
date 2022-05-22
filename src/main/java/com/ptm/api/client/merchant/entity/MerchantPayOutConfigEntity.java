package com.ptm.api.client.merchant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "merchant_pay_out_config")
@Builder
public class MerchantPayOutConfigEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "min_range")
	private double minRange;
	
	@Column(name = "max_range")
	private double maxRange;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "merchant_name")
	private String merchantName;
	
	@Column(name = "merchange_code")
	private String merchangeCode;
	
	
	
}
