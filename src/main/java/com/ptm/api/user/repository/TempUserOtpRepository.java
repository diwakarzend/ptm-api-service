package com.ptm.api.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptm.api.user.entity.TempUserOtp;

public interface TempUserOtpRepository extends JpaRepository<TempUserOtp, Long>{
	
	Optional<TempUserOtp> findByPhoneNumberAndOtp(String phoneNumber,String otp);
	Optional<TempUserOtp> findByPhoneNumber(String phoneNumber);

}
