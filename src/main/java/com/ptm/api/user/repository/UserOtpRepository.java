package com.ptm.api.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.config.constant.EotpType;
import com.ptm.api.user.entity.UserOtp;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {
	
	public Optional<UserOtp> findByUserId(Long id);

	public Optional<UserOtp> findByUserUsername(String userName);
	
	public Optional<UserOtp> findByUserUsernameAndOtpAndOtpType(String phoneNumber,String otp,EotpType otpType);


}
