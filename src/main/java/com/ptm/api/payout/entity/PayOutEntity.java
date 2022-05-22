package com.ptm.api.payout.entity;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ptm_pay_out")
public class PayOutEntity extends PayOutAbstractAuditingEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "remittance_amount")
	private Double remittanceAmount;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "beneficiary_name")
	private String beneficiaryName;

	@Column(name = "route")
	private String route;

	// indexing
	@Column(name = "user_id")
	private String userId;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "ifsc_code")
	private String ifscCode;

	// unique
	@Column(name = "client_id")
	private String clientId;

	@Column(name = "type")
	private String type;

	// unique
	@Column(name = "txn_id")
	private String txnId;

	@Column(name = "merchant_txn_id") // rrn number
	private String merchantTxnId;

	@Column(name = "merchant_pay_out_charge") // rbl or paytm charge
	private Double clientPayoutChanrge;

	@Column(name = "pay_out_charge") // ptm pay out charge
	private Double payoutChanrge;

	@Column(name = "profit_amount")
	private Double profitAmount;

	@Column(name = "refund_txn_id")
	private Double refundTxnId;

	@Column(name = "refund_amount")
	private Double refundAmount;

	@Column(name = "refund_reason")
	private Double refundReson;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private EtransactionStatus status;

	@Column(name = "expire_at")
	private LocalDateTime expireAt;

	@Column(name = "remark")
	private String remark;

	@Column(name = "approval_required")
	@Enumerated(EnumType.STRING)
	private Eflags approvalRequired;
	
	@Column(name = "opening_balance")
	private Double openingBalance;
	
	@Column(name = "closing_balance")
	private Double closingBalance;
	
	@Column(name = "merhant_code") 
	private String merchantCode;

}
