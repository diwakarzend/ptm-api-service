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
@Table(name = "ptm_api")
@Getter
@Setter
public class PtmApi extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "api_name")
	private String apiName;
	
	@Column(name = "api_code")
	private String apiCode;
	
	@Column(name = "api_url")
	private String apiUrl;
	
	@Column(name = "group_id")
	private Long groupId;
	
}
