package com.ptm.api.user.collections;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.ptm.api.catalog.collection.CollectionAbstractEntity;
import com.ptm.api.config.constant.Estatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeneficiarylCollection  extends CollectionAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String bankName;
	
	private String accountNumber;

	private String ifscCode;

	private String mobile;

	private String firstName;

	private String lastName;
	
	private Estatus status;
	
	private String ptmUseruuid;



}
