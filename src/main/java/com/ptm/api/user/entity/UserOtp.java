package com.ptm.api.user.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ptm.api.config.constant.EotpType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_otp")
@Getter
@Setter
public class UserOtp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Column(name = "otp")
	private String otp;
	
	@Column(name = "otp_type")
	@Enumerated(EnumType.STRING)
	private EotpType otpType;
	
	@Column(name = "otp_tries")
	private Integer otpTries;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "expire_at")
	private LocalDateTime expireAt;

}
