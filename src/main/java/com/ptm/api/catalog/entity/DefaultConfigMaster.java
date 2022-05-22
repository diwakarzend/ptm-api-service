package com.ptm.api.catalog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ptm_default_config_master")
public class DefaultConfigMaster extends CatalogueAbstractAuditingEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "service_name")
	private String serviceName;
	
	@Column(name = "config_json")
	private String configJson;

}
