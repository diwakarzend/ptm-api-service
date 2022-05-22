package com.ptm.api.wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ptm.api.config.constant.EtransactionType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "ptm_user_wallet_activity")
public class UserWalletActivity  extends WalletAbstractAuditingEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id")
	private String userid;
	
	
	@Column(name = "currency_code")
	private String currencyCode;
	
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "current_amount")
	private Double currentAmount;
	

	@Column(name = "txn_type")
	@Enumerated(EnumType.STRING)
	private EtransactionType txnType;
	
	@ManyToOne
    @JoinColumn(name = "wallet_id")
    private UserWallet businessWallet;
	
	@Column(name = "service_type")
	private String serviceType;
	
	
	@Column(name = "txn_id")
	private String txnId;
	
	

	
}
