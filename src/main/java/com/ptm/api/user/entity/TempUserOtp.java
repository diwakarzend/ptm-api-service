package com.ptm.api.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ptm.api.config.constant.EotpType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "temp_user_otp")
@Getter
@Setter
public class TempUserOtp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "phone_number")
	private String phoneNumber;
	
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
