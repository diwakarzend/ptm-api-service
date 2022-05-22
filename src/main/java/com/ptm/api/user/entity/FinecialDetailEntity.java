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
@Getter
@Setter
@Table(name = "ptm_user_fin_detail")
public class FinecialDetailEntity extends AbstractAuditingEntity implements Serializable{
	
	/**
	 * Long
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_uuid")
	private String userUuid;
	
	@Column(name = "brand_name")
	private String brnadName;
	
	@Column(name = "register_company")
	private String registerCompany;
	
	@Column(name = "register_address")
	private String registerAddress;
	
	@Column(name = "gst_no")
	private String gstNo;
	
	@Column(name = "website")
	private String website;

}
