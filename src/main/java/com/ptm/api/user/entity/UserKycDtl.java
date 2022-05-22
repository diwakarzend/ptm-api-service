package com.ptm.api.user.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_kyc_dtl")
@Getter
@Setter
public class UserKycDtl extends AbstractAuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "kyc_dtl_id")
	private int kycDtlId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "order_seq")
	private byte orderSeq;

	@Column(name = "doc_uuid")
	private byte docUuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proof_doc_name_id")
	private ProofDocName proofDocName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proof_doc_type_id")
	private ProofDocType proofDocType;

	@Temporal(TemporalType.DATE)
	@Column(name = "doc_expiry_date")
	private Date docExpiryDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doc_status_id")
	private ProofDocStatus proofDocStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "doc_upload_date")
	private Date docUploadDate;

	@Column(name = "is_active")
	private String isActive;
	
	@Column(name = "daleted_user")
	private String daletedUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_date")
	private Date deletedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "admin_action_date")
	private Date adminActionDate;

	@Column(name = "admin_action_user")
	private String adminActionUser;

}
