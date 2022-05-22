package com.ptm.api.wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ptm.api.config.constant.EtransactionStatus;
import com.ptm.api.config.constant.EtransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ptm_txn")
public class PtmTxn extends WalletAbstractAuditingEntity {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "txn_id")
	private int txnId;
	
	@Column(name = "txn_type")
	@Enumerated(EnumType.STRING)
	private EtransactionType txnType;
	
	@Column(name = "txn_status")
	@Enumerated(EnumType.STRING)
	private EtransactionStatus txnStatus;
	
	@Column(name = "txn_uuid")
	private String txnUuid;
			
	@Column(name = "user_id")
	private String userId;
	
	

}
