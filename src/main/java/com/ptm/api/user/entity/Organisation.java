package com.ptm.api.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "organisation")
@Getter
@Setter
public class Organisation extends AbstractAuditingEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "company_address")
	private String companyAddress;
	
	@Column(name = "activeStatus")
	private String activeStatus;
	
	@Column(name = "brand_name")
	private String brandName;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "client_finance_email")
	private String clientFinanceEmail;
	
	@Column(name = "company_type")
	private String companyType;
	
	@Column(name = "city_id")
	private String cityId;
	
	@Column(name = "state_id")
	private String stateId;
	
	@Column(name = "pin_code")
	private String pinCode;
	
	@Column(name = "remarks")
	private String remarks;

	
}
