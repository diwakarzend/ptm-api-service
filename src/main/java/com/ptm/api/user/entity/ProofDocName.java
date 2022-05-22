package com.ptm.api.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the proof_doc_name database table.
 * 
 */
@Entity
@Table(name="proof_doc_name")
@Getter
@Setter
public class ProofDocName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="proof_doc_name_id")
	private int proofDocNameId;

	@Column(name="acct_cat_id")
	private byte acctCatId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="created_user")
	private String createdUser;

	@Column(name="is_active")
	private String isActive;

	@Column(name="is_default")
	private String isDefault;

	@Column(name="is_extra")
	private String isExtra;


	@Column(name="proof_doc_name_en")
	private String proofDocNameEn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	@Column(name="updated_user")
	private String updatedUser;
	
	



}
