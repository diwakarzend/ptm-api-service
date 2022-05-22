package com.ptm.api.user.entity;

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

@Entity
@Table(name = "proof_doc_status")
@Getter
@Setter
public class ProofDocStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "proof_doc_status_id")
	private byte proofDocStatusId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_ts")
	private Date createdTs;

	@Column(name = "created_user")
	private String createdUser;

	@Column(name = "is_active")
	private String isActive;

	@Column(name = "proof_doc_status_code")
	private String proofDocStatusCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_ts")
	private Date updatedTs;

	@Column(name = "updated_user")
	private String updatedUser;

}
