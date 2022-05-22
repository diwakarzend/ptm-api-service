package com.ptm.api.wallet.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ptm.api.config.constant.Eflags;
import com.ptm.api.config.constant.EtransactionStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ptm_request_for_fund")
public class RequestForFund extends WalletAbstractAuditingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "request_id")
	private Long requestid;

	@Column(name = "reqst_fund_uuid")
	private String reqstfunduuid;

	@Column(name = "from_reqst_user_id")
	private String fromReqstUserId;

	@Column(name = "payement_mode")
	private String payementMode;

	
	@Column(name = "from_bank")
	private String fromBank;

	@Column(name = "to_bank")
	private String toBank;

	@Column(name = "txn_amt")
	private Double txnAmt;

	@Column(name = "txn_currency")
	private String txnCurrency;

	@Column(name = "reqst_date")
	private LocalDateTime reqstDate = LocalDateTime.now();
	
	@Column(name = "proof_updaod_status")
	private Eflags proofUpdaodStatus;

	@Column(name = "requester_parent_id")
	private String requesterParentId;
	
    @Column(name = "transation_ref_no")
	private String transationRefNo;
	
    @Column(name = "approve_status")
	@Enumerated(EnumType.STRING)
    private EtransactionStatus approveStatus;
   
	
	/*
	 * @Column(name = "txn_fees") private Double txnFees;
	 */

}
