package com.ptm.api.catalog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ptm.api.user.entity.AbstractAuditingEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ptm_sms_master")
public class PtmSmsMaster extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "service_name")
	private String serviceName;

	@Column(name = "sms_txt")
	private String smsTxt;
	
	@Column(name = "template_id")
	private String templateId;
}
