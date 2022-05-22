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

import com.ptm.api.config.constant.EwalletStatus;
import com.ptm.api.config.constant.EwalletType;

import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "ptm_user_wallet")
@Getter
@Setter
public class UserWallet extends WalletAbstractAuditingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wallet_id")
	private Long walletId;

	@Column(name = "user_id")
	private String userid;
	
	@Column(name = "balance_in_base_currency")
	private Double balanceInBaseCurrency;

	@Column(name = "balance_in_consumer_currency")
	private Double balanceInConsumerCurrency;


	@Column(name = "wallet_status")
	@Enumerated(EnumType.STRING)
	private EwalletStatus walletStatus;

	@Column(name = "currency_id")
	private String currency;
	
	@Column(name = "wallet_type")
	@Enumerated(EnumType.STRING)
	private EwalletType walletType;
	

	@Column(name = "blocked_date")
	private LocalDateTime blockedDate;
	
	@Column(name = "blocked_remark")
	private String blockedRemark;
	// wallet type---> travel, mobile , hotels, main wallet

	public UserWallet() {
		
	}
	

}